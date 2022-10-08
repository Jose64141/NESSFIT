package cl.italosoft.nessfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdministrativoController
{
    @GetMapping("/administrativo/manage-users")
    public String manageUsers(HttpServletRequest request)
    {
        return "/administrativo/manage-clients";
    }
}
