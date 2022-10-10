package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdministradorController
{
    @Autowired
    private UserService userService;

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
}
