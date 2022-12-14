package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import cl.italosoft.nessfit.util.RutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import static cl.italosoft.nessfit.util.Util.capitelizeEachWord;

/***
 * Controller for Administrador role pages
 */
@Controller
public class AdministradorController
{
    @Autowired
    private UserService userService;

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
     * Manage Users page
     * @param request HTTP request
     * @param rut new User rut
     * @param model view template model
     * @return page template
     */
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
                    boolean isEnabled = user.isEnabled();
                    model.addAttribute("RUT",rut);
                    model.addAttribute("name",capitelizeEachWord(user.getName()));
                    model.addAttribute("firstLastName",capitelizeEachWord(user.getFirstLastName()));
                    model.addAttribute("secondLastName",capitelizeEachWord(user.getSecondLastName()));
                    model.addAttribute("isEnabled",isEnabled ? "Habilitado" : "Deshabilitado");
                    model.addAttribute("actionName",isEnabled ? "Deshabilitar" : "Habilitar");
                    model.addAttribute("tooltip",isEnabled ? "Deshabilitar un usuario implica que ya no podr?? iniciar sesi??n"
                            : "Habilitar un usuario implica que ya podr?? iniciar sesi??n");}
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
            model.addAttribute("nullMessage","No ha seleccionado ning??n usuario");
        }
        return "administrador/manage-clients";
    }

    /**
     * Manage Users page
     * @param rut rut of user to manage
     * @param attr view redirect attributes
     * @return page template
     */
    @PostMapping("/administrador/manage-clients")
    public String manageUsers(@RequestParam String rut, RedirectAttributes attr)
    {
        User user = this.userService.find(rut.toLowerCase());
        user.setEnabled(!user.isEnabled());
        this.userService.saveAndFlush(user);
        attr.addFlashAttribute("isEnabledChanged","Se ha cambiado el estado con ??xito");
        return "redirect:/administrador/manage-clients?rut="+rut;
    }

    /**
     * Add administrative page
     * @param model view template model
     * @param newUser new User to add
     * @return page template
     */
    @GetMapping("/administrador/add-administrative")
    public String addAdministrative(Model model, User newUser)
    {
        if(newUser == null)
            newUser = new User();
        model.addAttribute("user", newUser);
        return "administrador/add-administrative";
    }

    /**
     * Add administrative page
     * @param model view template model
     * @param newUser new User to add
     * @param result validation errors on newInfo
     * @param attr view redirect attributes
     * @return page template
     */
    @PostMapping("/administrador/add-administrative")
    public String addAdministrative(Model model, @Valid User newUser, BindingResult result, RedirectAttributes attr)
    {
        newUser.setRut(newUser.getRut().strip().toLowerCase());
        newUser.setEmail(newUser.getEmail().strip().toLowerCase());

        if(this.userService.findByEmail(newUser.getEmail()) != null)
        {
            result.rejectValue("email",null,"El correo electr??nico ya existe " +
                    "en el sistema. Intente iniciar sesi??n.");
        }
        if(this.userService.find(newUser.getRut()) != null)
        {
            result.rejectValue("rut",null,"El RUT ya existe " +
                    "en el sistema. Intente iniciar sesi??n.");
        }

        if(result.hasErrors())
        {
            return "administrador/add-administrative";
        }

        newUser.setEnabled(true);
        Role role = new Role(2);
        newUser.setRole(role);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPasswordHash = passwordEncoder.encode(newUser.getRut());
        newUser.setPassword(newPasswordHash);
        newUser.setName(newUser.getName().strip().toLowerCase());
        newUser.setFirstLastName(newUser.getFirstLastName().strip().toLowerCase());
        newUser.setSecondLastName(newUser.getSecondLastName().strip().toLowerCase());
        userService.saveAndFlush(newUser);

        attr.addFlashAttribute("successMsg","El administrativo se a??adi?? con ??xito. ");
        return "redirect:/administrador/add-administrative";
    }

    /**
     * Manage administrative page
     * @param model view template model
     * @param page Page with the results ordered in tuples
     * @param rut rut to search
     * @return page template
     */
    @GetMapping("/administrador/manage-administrative")
    public String manageAdministrative(Model model, @PageableDefault(value = 5) Pageable page, @RequestParam(required = false) String rut)
    {
        model.addAttribute("users", userService.findByRutWithRole(rut, page, 2));
        return "administrador/manage-administrative";
    }

    /**
     * edit Administrative page
     * @param model view template model
     * @param rut rut of the user to edit
     * @return page template
     */
    @GetMapping("/administrador/edit-administrative")
    public String editAdministrative(Model model, @RequestParam String rut)
    {
        User user = this.userService.find(rut);
        if(user == null || (user.getRole().getId() != 2))
        {
            return "redirect:/administrador/manage-administrative";
        }
        user.setName(capitelizeEachWord(user.getName()));
        user.setFirstLastName(capitelizeEachWord(user.getFirstLastName()));
        user.setSecondLastName(capitelizeEachWord(user.getSecondLastName()));
        model.addAttribute("user",user);
        return "administrador/edit-administrative";
    }

    /**
     * Edit Administrative page
     * @param model view template model
     * @param user User to edit
     * @param result validation errors on newInfo
     * @param attr view redirect attributes
     * @return page template
     */
    @PostMapping("/administrador/edit-administrative")
    public String editAdministrative(Model model, @Valid User user, BindingResult result, RedirectAttributes attr)
    {
        if(result.hasErrors())
        {
            return "administrador/edit-administrative";
        }
        String rut = user.getRut();
        User completeUser = userService.find(rut);
        User emailUser = this.userService.findByEmail(user.getEmail());
        if(emailUser != null && !emailUser.getRut().equalsIgnoreCase(rut))
        {
            result.rejectValue("email",null,"El correo electr??nico ya est?? en uso.");
            return "administrador/edit-administrative";
        }

        completeUser.setName(user.getName().strip().toLowerCase());
        completeUser.setFirstLastName(user.getFirstLastName().strip().toLowerCase());
        completeUser.setSecondLastName(user.getSecondLastName().strip().toLowerCase());
        completeUser.setPhoneNumber(user.getPhoneNumber());
        completeUser.setEmail(user.getEmail().strip().toLowerCase());
        userService.saveAndFlush(completeUser);
        attr.addFlashAttribute("infoSuccessMsg","Los cambios se han realizado con ??xito.");

        return "redirect:/administrador/edit-administrative?rut="+rut;
    }
}
