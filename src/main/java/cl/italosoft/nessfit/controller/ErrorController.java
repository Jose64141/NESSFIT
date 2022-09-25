package cl.italosoft.nessfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController
{
    @GetMapping("/name")
    public String error()
    {
        return "error";
    }
}