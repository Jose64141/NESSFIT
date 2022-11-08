package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import cl.italosoft.nessfit.util.RutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AdministradorController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RutValidator rutValidator;

    @InitBinder("user")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(rutValidator);
    }

    @GetMapping("/administrador/manage-clients")
    public String manageUsers(HttpServletRequest request, @RequestParam(required = false) String rut, Model model)
    {
        if(rut != null)
        {
            User user = this.userService.find(rut);
            if(user != null)
            {
                if(user.getRole().getId() != 1)
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
                model.addAttribute("nullMessage","No se han encontrado coincidencias");
            }
        }
        else
        {
            model.addAttribute("nullMessage","No ha seleccionado ningún usuario");
        }
        return "administrador/manage-clients";
    }

    @PostMapping("/administrador/manage-clients")
    public String manageUsers(@RequestParam String rut, RedirectAttributes attr)
    {
        User user = this.userService.find(rut);
        user.setEnabled(!user.isEnabled());
        this.userService.saveAndFlush(user);
        attr.addFlashAttribute("isEnabledChanged","Se ha cambiado el estado con éxito");
        return "redirect:/administrador/manage-clients?rut="+rut;
    }

    @GetMapping("/administrador/add-administrative")
    public String addAdministrative(Model model, User newUser)
    {
        if(newUser == null)
            newUser = new User();
        model.addAttribute("user", newUser);
        return "administrador/add-administrative";
    }

    @PostMapping("/administrador/add-administrative")
    public String addAdministrative(Model model, @Valid User newUser, BindingResult result, RedirectAttributes attr)
    {
        newUser.setRut(newUser.getRut().toUpperCase());

        User user = this.userService.findByRutOrEmail(newUser.getRut(), newUser.getEmail());
        if(user != null)
        {
            result.rejectValue("rut",null,"El RUT y/o correo electrónico ya existen " +
                    "en el sistema. Intente iniciar sesión.");
        }
        if(result.hasErrors())
        {
            return "administrador/add-administrative";
        }

        newUser.setEnabled(true);
        Role role = new Role(3);
        newUser.setRole(role);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPasswordHash = passwordEncoder.encode(newUser.getRut());
        newUser.setPassword(newPasswordHash);
        userService.saveAndFlush(newUser);

        attr.addFlashAttribute("successMsg","El administrativo se añadió con éxito. ");
        return "redirect:/administrador/add-administrative";
    }

    @GetMapping("/administrador/manage-administrative")
    public String manageAdministrative()
    {
        return "administrador/manage-administrative";
    }

}
