package cl.italosoft.nessfit.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for login pages
 */
@Controller
public class LoginController
{
    /**
     * Login page
     * @return page template
     */
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    /**
     * Credits page
     * @return credits page template
     */
    @GetMapping("/credits")
    public String credits()
    {
        return "credits";
    }


}
