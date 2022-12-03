package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.model.RentRequest;
import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.Type;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.DeportiveCenterService;
import cl.italosoft.nessfit.service.RentRequestService;
import cl.italosoft.nessfit.service.TypeService;
import cl.italosoft.nessfit.service.UserService;
import cl.italosoft.nessfit.util.RutValidator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import static cl.italosoft.nessfit.util.Util.capitelizeEachWord;

/***
 * Controller for Administrativo role pages
 */
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
    private RentRequestService rentRequestService;
    
    @Autowired
    private RutValidator rutValidator;

    /**
     * Adds RUT validator to the validation on BindingResult
     * @param binder binder Data binder object
     */
    @InitBinder("user")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(rutValidator);
    }

    /**
     * Manage client page
     * @param request HTTP request
     * @param rut new client rut
     * @param model view template model
     * @return page template
     */
    @GetMapping("/administrativo/manage-clients")
    public String manageUsers(HttpServletRequest request, @RequestParam(required = false) String rut, Model model)
    {
        if(rut != null)
        {
            User user = this.userService.find(rut,3);
            if(user != null)
            {
                model.addAttribute("RUT",rut);
                model.addAttribute("name",capitelizeEachWord(user.getName()));
                model.addAttribute("firstLastName",capitelizeEachWord(user.getFirstLastName()));
                model.addAttribute("secondLastName",capitelizeEachWord(user.getSecondLastName()));
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

    /**
     * Manage clients page
     * @param rut rut of user to manage
     * @param attr view redirect attributes
     * @return page template
     */
    @PostMapping("/administrativo/manage-clients")
    public String manageUsers(@RequestParam String rut, RedirectAttributes attr)
    {
        User user = this.userService.find(rut);
        user.setEnabled(!user.isEnabled());
        this.userService.saveAndFlush(user);
        attr.addFlashAttribute("isEnabledChanged","Se ha cambiado el estado con éxito");
        return "redirect:/administrativo/manage-clients?rut="+rut;
    }

    /**
     * Add client page
     * @param model view template model
     * @param newUser new User to add
     * @return page template
     */
    @GetMapping("/administrativo/add-client")
    public String addClient(Model model, User newUser)
    {
        if(newUser == null)
            newUser = new User();
        model.addAttribute("user", newUser);
    	return "administrativo/add-client";
    }

    /**
     * Add client page
     * @param model view template model
     * @param newUser new User to add
     * @param result validation errors on newInfo
     * @param attr view redirect attributes
     * @return page template
     */
    @PostMapping("/administrativo/add-client")
    public String addClient(Model model, @Valid User newUser, BindingResult result, RedirectAttributes attr)
    {
        newUser.setRut(newUser.getRut().strip().toLowerCase());
        newUser.setEmail(newUser.getEmail().strip().toLowerCase());

        if(this.userService.findByEmail(newUser.getEmail()) != null)
        {
            result.rejectValue("email",null,"El correo electrónico ya existe " +
                    "en el sistema. Intente iniciar sesión.");
        }
        if(this.userService.find(newUser.getRut()) != null)
        {
            result.rejectValue("rut",null,"El RUT ya existe " +
                    "en el sistema. Intente iniciar sesión.");
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
        newUser.setName(newUser.getName().strip().toLowerCase());
        newUser.setFirstLastName(newUser.getFirstLastName().strip().toLowerCase());
        newUser.setSecondLastName(newUser.getSecondLastName().strip().toLowerCase());
        userService.saveAndFlush(newUser);

        attr.addFlashAttribute("successMsg","El cliente se añadió con éxito. ");
        return "redirect:/administrativo/add-client";
    }

    /**
     * Add deportive center page
     * @param model view template model
     * @param newDeportiveCenter new deportive center to add
     * @return page template
     */
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

    /**
     * Add deportive center page
     * @param model view template model
     * @param newDeportiveCenter new deportive center to add
     * @param result validation errors on newInfo
     * @param attr view redirect attrbutes
     * @return page template
     */
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

        newDeportiveCenter.setName(newDeportiveCenter.getName().toLowerCase().strip());
        newDeportiveCenter.setAddress(newDeportiveCenter.getAddress().toLowerCase().strip());
        deportiveCenterService.saveAndFlush(newDeportiveCenter);
    	attr.addFlashAttribute("successMsg", "El recinto se añadió con éxito.");
    	return "redirect:/administrativo/add-deportive-center";
    }

    /**
     * Manage deportive centers page
     * @param model view template model
     * @param page Page with the results ordered in tuples
     * @param name name to search
     * @return page template
     */
    @GetMapping("/administrativo/manage-deportive-centers")
    public String manageDeportiveCenter(Model model, @PageableDefault(value = 5) Pageable page, @RequestParam(required = false) String name)
    {
        model.addAttribute("centers", deportiveCenterService.findByName(name, page));
        return "administrativo/manage-deportive-centers";
    }

    /**
     * Edit deportive center page
     * @param model view template model
     * @param name name of the deportive center to edit
     * @return page template
     */
    @GetMapping("/administrativo/edit-deportive-center")
    public String editDeportiveCenter(Model model, @RequestParam String name)
    {
        DeportiveCenter deportiveCenter = this.deportiveCenterService.find(name);
        model.addAttribute("types", typeService.list());
        if(deportiveCenter == null)
        {
            return "redirect:/administrativo/manage-deportive-centers";
        }
        deportiveCenter.setName(deportiveCenter.getName().toUpperCase());
        deportiveCenter.setAddress(deportiveCenter.getAddress().toUpperCase());
        model.addAttribute("deportiveCenter",deportiveCenter);
        return "administrativo/edit-deportive-center";
    }

    /**
     * Edit deportive center page
     * @param model view template model
     * @param deportiveCenter deportive center to edit
     * @param result validation errors on newInfo
     * @param attr view redirect attributes
     * @return page template
     */
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
            attr.addFlashAttribute("errorMsg","Ha habido un problema. Intente nuevamente.");
            return "redirect:/administrativo/manage-deportive-centers";
        }
        completeCenter.setAddress(deportiveCenter.getAddress().toLowerCase().strip());
        completeCenter.setType(deportiveCenter.getType());
        completeCenter.setIsEnabled(deportiveCenter.getIsEnabled());
        completeCenter.setCostPerDay(deportiveCenter.getCostPerDay());
        deportiveCenterService.saveAndFlush(completeCenter);
        attr.addFlashAttribute("successMsg","Los cambios se han realizado con éxito.");

        return "redirect:/administrativo/edit-deportive-center?name="+name;
    }

   
    @GetMapping("/administrativo/statistics")
    public String viewStatistics(Model model, @RequestParam(name = "inicio", required = false, defaultValue = "1900-01-01") String beginning, @RequestParam(name = "fin", required = false, defaultValue = "2999-01-01") String end)throws ParseException {
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	Date beginningdate = new Date((formatter.parse(beginning)).getTime());
    	Date enddate = new Date((formatter.parse(end)).getTime());
    	int contCancha = 0;
    	int contGimnasio = 0;
    	int contPiscina = 0;
    	int contQuincho = 0;
    	int contEstadio = 0;
    	
    	List<RentRequest> requests = rentRequestService.findByDateBetween(beginningdate, enddate);
    	
    	for (RentRequest rentrequest : requests) {
    		switch ( rentrequest.getDeportiveCenter().getType().getName() ) {			
    		case "cancha": 
    			contCancha++;
    			break;
    		case "gimnasio": 
    			contGimnasio++;
    			break;
    		case "piscina": 
    			contPiscina++;
    			break;
    		case "quincho": 
    			contQuincho++;
    			break;
    		case "estadio": 
    			contEstadio++;
    			break;
    		default: 
    			break;
			}
    	}
    	
    	model.addAttribute("solicitudes", requests);
    	model.addAttribute("CantidadCancha", contCancha);
    	model.addAttribute("CantidadGimnasio", contGimnasio);
    	model.addAttribute("CantidadPiscina", contPiscina);
    	model.addAttribute("CantidadQuincho", contQuincho);
    	model.addAttribute("CantidadEstadio", contEstadio);
    	
    	model.addAttribute("inicio", beginning);
    	model.addAttribute("fin", end);
    	
    	return "administrativo/statistics";
    }
}