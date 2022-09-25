package cl.italosoft.nessfit.controller;


import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;
    @GetMapping("/saveTestUser")
    public String saveTestUser()
    {
        /*
        Role role = new Role(3);
        String passwordEncrypted = "$2a$10$iER9Ma0CbfVYoQz6RBfrMec6mYxiz.dFdU/QBELxrs5fQq15jjAkq";
        User user = new User("103661455","Mauricio",
                "Araya",passwordEncrypted,56210646
                ,"mdaraya@gmail.com",false,role);
        this.userService.save(user);
        */
        return "a";
    }

    @GetMapping("/config")
    public String config(HttpServletRequest request, Model model)
    {
        User user = this.userService.find(request.getRemoteUser());
        model.addAttribute("rut", user.getRut().strip());
        model.addAttribute("name", user.getName().strip());
        model.addAttribute("lastName", user.getLastName().strip());
        return "config";
    }



}
