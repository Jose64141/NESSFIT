package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.model.RentRequest;
import cl.italosoft.nessfit.service.DeportiveCenterService;
import cl.italosoft.nessfit.service.RentRequestService;
import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ClienteController
{

    @Autowired
    private UserService userService;
    @Autowired
    private DeportiveCenterService deportiveCenterService;
    @Autowired
    private RentRequestService rentRequestService;

    @GetMapping("/cliente/rent/{id}")
    public String rent( Model model, String id)
    {

        DeportiveCenter selection = null;
        if (!id.isBlank())
            selection = deportiveCenterService.find(id);
        model.addAttribute("selection", selection);
        return "cliente/rent";
    }

    @PostMapping("/cliente/rent")
    public String rent(HttpServletRequest request, Model model)
    {
        String userRut = request.getRemoteUser();

        return "cliente/rent";
    }
}
