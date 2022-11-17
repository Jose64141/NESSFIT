package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/***
 * Controller for home page
 */
@Controller
public class HomeController
{
    @Autowired
    private UserService userService;

    /**
     * Home page
     * @param request HTTP request
     * @param model view template model
     * @return page template
     */
    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model)
    {
        User user = this.userService.find(request.getRemoteUser());
        model.addAttribute("name", user.getName().strip());
        model.addAttribute("firstLastName", user.getFirstLastName().strip());
        model.addAttribute("secondLastName", user.getSecondLastName().strip());
        return "home";
    }

    /**
     * Home page redirection
     * @return redirected page
     */
    @GetMapping("/")
    public String rootPage()
    {
        return "redirect:/home";
    }
}
