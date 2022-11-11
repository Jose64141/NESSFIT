package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.Type;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.DeportiveCenterService;
import cl.italosoft.nessfit.service.TypeService;
import cl.italosoft.nessfit.service.UserService;
import cl.italosoft.nessfit.util.RutValidator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdministrativoController 
{
	@Autowired
    private UserService userService;
	
	@Autowired
	private DeportiveCenterService deportiveCenterService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private RutValidator rutValidator;
    
    @InitBinder("user")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(rutValidator);
    }
    
    @GetMapping("/administrativo/manage-clients")
    public String manageUsers(HttpServletRequest request, @RequestParam(required = false) String rut, Model model)
    {
        if(rut != null)
        {
            User user = this.userService.find(rut,3);
            if(user != null)
            {
                model.addAttribute("RUT",rut);
                model.addAttribute("name",user.getName());
                model.addAttribute("firstLastName",user.getFirstLastName());
                model.addAttribute("secondLastName",user.getSecondLastName());
                model.addAttribute("isEnabled",user.isEnabled() ? "Habilitado" : "Deshabilitado");
                model.addAttribute("actionName",user.isEnabled() ? "Deshabilitar" : "Habilitar");
            }
            else
            {
                model.addAttribute("nullMessage","No se han encontrado coincidencias");
            }
        }
        else
        {
            model.addAttribute("nullMessage","No ha seleccionado ningún usuario");
        }
        return "administrativo/manage-clients";
    }

    @PostMapping("/administrativo/manage-clients")
    public String manageUsers(@RequestParam String rut, RedirectAttributes attr)
    {
        User user = this.userService.find(rut);
        user.setEnabled(!user.isEnabled());
        this.userService.saveAndFlush(user);
        attr.addFlashAttribute("isEnabledChanged","Se ha cambiado el estado con éxito");
        return "redirect:/administrativo/manage-clients?rut="+rut;
    }
	
    @GetMapping("/administrativo/add-client")
    public String addClient(Model model, User newUser)
    {
        if(newUser == null)
            newUser = new User();
        model.addAttribute("user", newUser);
    	return "administrativo/add-client";
    }
    
    @PostMapping("/administrativo/add-client")
    public String addClient(Model model, @Valid User newUser, BindingResult result, RedirectAttributes attr)
    {
    	newUser.setRut(newUser.getRut().toUpperCase());

        User user = this.userService.findByRutOrEmail(newUser.getRut(), newUser.getEmail());
        if(user != null)
        {
            result.rejectValue("rut",null,"El RUT y/o correo electrónico ya existen en el sistema. Intente iniciar sesión.");
        }
        if(result.hasErrors())
        {
            return "administrativo/add-client";
        }

    	newUser.setEnabled(true);
    	Role role = new Role(3);
    	newUser.setRole(role);
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String newPasswordHash = passwordEncoder.encode(newUser.getRut());
      newUser.setPassword(newPasswordHash);
      userService.saveAndFlush(newUser);

      attr.addFlashAttribute("successMsg","El cliente se añadió con éxito. ");
      return "redirect:/administrativo/add-client";
    }

    @GetMapping("/administrativo/add-deportive-center")
    public String addDeportiveCenter(Model model, DeportiveCenter newDeportiveCenter) 
    {
    	if(newDeportiveCenter == null)
        {
            newDeportiveCenter = new DeportiveCenter();
            newDeportiveCenter.setType(new Type());
        }

    	model.addAttribute("deportiveCenter",newDeportiveCenter);
        model.addAttribute("types", typeService.list());
     	return "administrativo/add-deportive-center";
    }
    
    @PostMapping("/administrativo/add-deportive-center")
    public String addDeportiveCenter(Model model,@Valid DeportiveCenter newDeportiveCenter, BindingResult result, RedirectAttributes attr)
    {	
    	newDeportiveCenter.setName(newDeportiveCenter.getName().toUpperCase().strip());
        newDeportiveCenter.setAddress(newDeportiveCenter.getAddress().toUpperCase().strip());
    	
    	DeportiveCenter deportiveCenter =  this.deportiveCenterService.find(newDeportiveCenter.getName());
    	if(deportiveCenter != null) 
    	{
            result.rejectValue("name", null, "El nombre ingresado ya existe en el sistema.");
    	}    	
    	if(result.hasErrors()) 
    	{
            model.addAttribute("types", typeService.list());
            return "administrativo/add-deportive-center";
    	}

        deportiveCenterService.saveAndFlush(newDeportiveCenter);
    	attr.addFlashAttribute("successMsg", "El recinto se añadió con éxito.");
    	return "redirect:/administrativo/add-deportive-center";
    }

    @GetMapping("/administrativo/manage-deportive-centers")
    public String manageDeportiveCenter(Model model, @PageableDefault(value = 5) Pageable page, @RequestParam(required = false) String rut)
    {
        model.addAttribute("centers", deportiveCenterService.list(page));
        return "administrativo/manage-deportive-centers";
    }

    @GetMapping("/administrativo/edit-deportive-center")
    public String editDeportiveCenter(Model model, @RequestParam String name)
    {
        DeportiveCenter deportiveCenter = this.deportiveCenterService.find(name);
        model.addAttribute("types", typeService.list());
        if(deportiveCenter == null)
        {
            return "redirect:/administrativo/manage-deportive-centers";
        }
        model.addAttribute("deportiveCenter",deportiveCenter);
        return "administrativo/edit-deportive-center";
    }

    @PostMapping("/administrativo/edit-deportive-center")
    public String editDeportiveCenter(Model model, @Valid DeportiveCenter deportiveCenter, BindingResult result, RedirectAttributes attr)
    {
        model.addAttribute("types", typeService.list());

        if(result.hasErrors())
        {
            return "administrativo/edit-deportive-center";
        }
        String name = deportiveCenter.getName();
        DeportiveCenter completeCenter = deportiveCenterService.find(name);
        if(completeCenter == null)
        {
            attr.addFlashAttribute("errorMsg","Ha habido un problema.");
            return "redirect:/administrativo/manage-deportive-centers";
        }
        completeCenter.setAddress(deportiveCenter.getAddress().toUpperCase().strip());
        completeCenter.setType(deportiveCenter.getType());
        completeCenter.setIsEnabled(deportiveCenter.getIsEnabled());
        completeCenter.setCostPerDay(deportiveCenter.getCostPerDay());
        deportiveCenterService.saveAndFlush(completeCenter);
        attr.addFlashAttribute("successMsg","Los cambios se han realizado con éxito.");

        return "redirect:/administrativo/edit-deportive-center?name="+name;
    }
}
