package cl.italosoft.nessfit.controller;


import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController
{

    @Autowired
    private UserService userService;
    @GetMapping("/name")
    public String save()
    {
        Role role = new Role(1);
        User user = new User("rutTest","nameTest",
                "lastNameTest","123",123,"a@a.com",true,role);
        this.userService.save(user);
        return "save";
    } 



}
