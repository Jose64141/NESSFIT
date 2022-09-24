package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController
{
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model)
    {
        model.addAttribute("name",
                this.userService.find(request.getRemoteUser()).getName().strip());
        if (request.isUserInRole("ADMINISTRADOR")) return "home";
        else if (request.isUserInRole("ADMINISTRATIVO")) return "home";
        else if (request.isUserInRole("CLIENTE")) return "cliente/home";
        else return "login";
    }

}
