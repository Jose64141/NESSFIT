package cl.italosoft.nessfit.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController
{
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/credits")
    public String credits()
    {
        return "credits";
    }


}
