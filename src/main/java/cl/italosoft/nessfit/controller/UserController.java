package cl.italosoft.nessfit.controller;


import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;
    @GetMapping("/saveTestUser")
    public String saveTestUser()
    {
        /*
        Role role = new Role(3);
        String passwordEncrypted = "$2a$10$iER9Ma0CbfVYoQz6RBfrMec6mYxiz.dFdU/QBELxrs5fQq15jjAkq";
        User user = new User("103661455","Mauricio",
                "Araya",passwordEncrypted,56210646
                ,"mdaraya@gmail.com",false,role);
        this.userService.save(user);
        */
        return "a";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login?logout";
    }

    @GetMapping("/account-settings")
    public String config(HttpServletRequest request, Model model)
    {
        User user = this.userService.find(request.getRemoteUser());
        model.addAttribute("rut", user.getRut().strip());
        model.addAttribute("name", user.getName().strip());
        model.addAttribute("lastName", user.getLastName().strip());
        model.addAttribute("email",user.getEmail().strip());
        model.addAttribute("phoneNumber",user.getPhoneNumber());
        return "account-settings";
    }

    @PostMapping("/account-settings/{operation}")
    public String configChange(HttpServletRequest request, Model model, @PathVariable String operation, @RequestBody MultiValueMap<String, String> formBody, RedirectAttributes attr) //https://stackoverflow.com/a/55338584
    {
        // do this, do that or just give an error
        User user = this.userService.find(request.getRemoteUser());
        switch (operation)
        {
            case "change-password":
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                if(!passwordEncoder.matches(formBody.getFirst("actualPassword"), user.getPassword()))
                {
                    // send error // change active tab
                    attr.addFlashAttribute("errorMsg","La contraseña actual es incorrecta.");
                    break;
                }
                String newPassword = formBody.getFirst("newPassword");
                String confirmNewPassword = formBody.getFirst("confirmNewPassword");
                if(!newPassword.equals(confirmNewPassword)) // change value on html
                {
                    // send error
                    attr.addFlashAttribute("errorMsg","Las contraseñas no coinciden.");
                    break;
                }
                if (newPassword.length() < 10 || 15 < newPassword.length())
                {
                    // send error
                    attr.addFlashAttribute("errorMsg","El largo de la contraseña debe estar entre 10 y 15 caracteres.");
                    break;
                }

                String newPasswordHash = passwordEncoder.encode(formBody.getFirst("newPassword"));
                user.setPassword(newPasswordHash);
                userService.saveAndFlush(user);
                // logout, redirect, and notify passwd change
                break;
            case "change-data":
                // validate email
            default:
                // check how to send error a
                break;
        }
        return config(request, model);
    }

}
