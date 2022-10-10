package cl.italosoft.nessfit.controller;


import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import cl.italosoft.nessfit.util.RutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RutValidator rutValidator;

    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(rutValidator);
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
        model.addAttribute("lastName", user.getFirstLastName().strip());
        model.addAttribute("email",user.getEmail().strip());
        model.addAttribute("phoneNumber",user.getPhoneNumber());
        model.addAttribute("user",user);
        return "account-settings";
    }

    @PostMapping("/account-settings/{operation}")
    public String configChange(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String operation, @RequestBody MultiValueMap<String, String> formBody, RedirectAttributes attr, @Valid User newInfo, BindingResult result) //https://stackoverflow.com/a/55338584
    {
        // do this, do that or just give an error
        User user = this.userService.find(request.getRemoteUser());
        switch (operation)
        {
            case "change-password":
                attr.addFlashAttribute("showPassword", true);
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String actualPassword = formBody.getFirst("actualPassword");

                if(!passwordEncoder.matches(actualPassword, user.getPassword()))
                {
                    // send error // change active tab
                    attr.addFlashAttribute("passwordErrorMsg","La contraseña actual es incorrecta.");
                    break;
                }
                String newPassword = formBody.getFirst("newPassword");
                String confirmNewPassword = formBody.getFirst("confirmNewPassword");
                if(newPassword.equals(actualPassword))
                {
                    attr.addFlashAttribute("passwordErrorMsg","La contraseña nueva debe ser distinta a la actual.");
                    break;
                }
                if(!newPassword.equals(confirmNewPassword))
                {
                    attr.addFlashAttribute("passwordErrorMsg","Las contraseñas no coinciden.");
                    break;
                }
                if (newPassword.length() < 10 || 15 < newPassword.length())
                {
                    attr.addFlashAttribute("passwordErrorMsg","El largo de la contraseña debe estar entre 10 y 15 caracteres.");
                    break;
                }
                String newPasswordHash = passwordEncoder.encode(formBody.getFirst("newPassword"));
                user.setPassword(newPasswordHash);
                userService.saveAndFlush(user);

                attr.addFlashAttribute("passwordChanged","Su contraseña se ha cambiado exitosamente.");

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if( authentication != null)
                    new SecurityContextLogoutHandler().logout(request, response, authentication);

                return "redirect:/login";
            case "change-data":
                attr.addFlashAttribute("showPassword", false);
                if(result.hasErrors())
                {
                    StringBuffer errorMsg = new StringBuffer();
                    Boolean[] msgInserted = {false, false, false};
                    for (FieldError error: result.getFieldErrors())
                    {
                        switch (error.getField())
                        {
                            case "name", "firstLastName", "secondLastName":
                                if(!msgInserted[0])
                                {
                                    errorMsg.append("Los nombres o apellidos deben tener más de 2 caracteres. ");
                                    msgInserted[0] = true;
                                }
                                break;
                            case "phoneNumber":
                                if(!msgInserted[1])
                                {
                                    errorMsg.append("El teléfono móvil ingresado no es válido. ");
                                    msgInserted[1] = true;
                                }
                                break;
                            case "email":
                                if(!msgInserted[2])
                                {
                                    errorMsg.append("Su correo electrónico no es válido. ");
                                    msgInserted[2] = true;
                                }
                                break;
                        }
                    }
                    attr.addFlashAttribute("infoErrorMsg",errorMsg);
                    break;
                }
                user.setName(newInfo.getName().strip());
                user.setFirstLastName(newInfo.getFirstLastName().strip());
                user.setSecondLastName(newInfo.getSecondLastName().strip());
                user.setPhoneNumber(newInfo.getPhoneNumber());
                user.setEmail(newInfo.getEmail().strip());
                userService.saveAndFlush(user);
                attr.addFlashAttribute("infoSuccessMsg","Los cambios se han realizado con éxito.");
            default:
                break;
        }
        return "redirect:/account-settings";
    }

}
