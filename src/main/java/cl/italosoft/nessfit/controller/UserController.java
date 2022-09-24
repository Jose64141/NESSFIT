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
    @GetMapping("/saveTestUser")
    public String saveTestUser()
    {
        Role role = new Role(1);
        String passwordEncrypted = "$2a$10$AlDCdNSTduYSr7GlE4uMyegCrhdDZAHhUqXeRzckAq5LyeoqELmkG";
        User user = new User("17977139K","Antonio",
                "Barraza",passwordEncrypted,913121312
                ,"antonio.barraza.guzman@gmail.com",true,role);
        this.userService.save(user);
        return "a";
    }





}
