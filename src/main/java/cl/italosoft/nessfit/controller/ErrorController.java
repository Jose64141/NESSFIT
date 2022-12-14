package cl.italosoft.nessfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/***
 * Controller for error handling pages
 */
@Controller
public class ErrorController
{
    /**
     * Error page
     * @return page template
     */
    @GetMapping("/name")
    public String error()
    {
        return "error";
    }
}
