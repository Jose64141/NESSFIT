package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/administrativo/manage-clients")
    public String manageUsers(HttpServletRequest request, @RequestParam(required = false) String rut, Model model)
    {
        if(rut != null)
        {
            User user = this.userService.find(rut,3);
            if(user != null)
            {
                System.out.println(user.getName());
                model.addAttribute("RUT",rut);
                model.addAttribute("name",user.getName());
                model.addAttribute("firstLastName",user.getLastName());
                model.addAttribute("secondLastName",user.getLastName());
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
        return "/administrativo/manage-clients";
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


}
