package cl.italosoft.nessfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController
{
    @GetMapping("/home")
    public String home(HttpServletRequest request)
    {
        if (request.isUserInRole("ADMINISTRADOR")) return "home";
        else if (request.isUserInRole("ADMINISTRATIVO")) return "home";
        else if (request.isUserInRole("CLIENTE")) return "cliente/home";
        else return "login";
    }

}
