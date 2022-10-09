package cl.italosoft.nessfit.controller;


import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.UserService;
import cl.italosoft.nessfit.util.RutValidator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdministrativoController 
{
	@Autowired
    private UserService userService;

    @Autowired
    private RutValidator rutValidator;
    
    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(rutValidator);
    }
	
    @GetMapping("/administrativo/add-client")
    public String addClient()
    {
    	return "administrativo/add-client";
    }
    
    @PostMapping("/administrativo/add-client")
    public String addClient(@Valid User newUser, RedirectAttributes attr, BindingResult result)
    {
    	if(result.hasErrors())
        {
            StringBuffer errorMsg = new StringBuffer();
            Boolean[] msgInserted = {false, false, false, false};
            for (FieldError error: result.getFieldErrors())
            {
                switch (error.getField())
                {
                    case "name", "firstLastName", "secondLastName":
                        if(!msgInserted[0])
                        {
                            errorMsg.append("Los nombres o apellidos deben tener más de 2 caracteres. ");
                            msgInserted[0] = true;
                        }
                        break;
                    case "phoneNumber":
                        if(!msgInserted[1])
                        {
                            errorMsg.append("El teléfono móvil ingresado no es válido. ");
                            msgInserted[1] = true;
                        }
                        break;
                    case "email":
                        if(!msgInserted[2])
                        {
                            errorMsg.append("Su correo electrónico no es válido. ");
                            msgInserted[2] = true;
                        }
                        break;
                    case "rut":
                        if(!msgInserted[3])
                        {
                            errorMsg.append("RUT inválido. ");
                            msgInserted[3] = true;
                        }
                        break;
                }
            }
            attr.addFlashAttribute("infoErrorMsg",errorMsg);
            return "administrativo/add-client";
        }
    	newUser.setRut(newUser.getRut().toUpperCase());
    	newUser.setEnabled(true);
    	Role role = new Role(3);
    	newUser.setRole(role);
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String newPasswordHash = passwordEncoder.encode(newUser.getRut());
        newUser.setPassword(newPasswordHash);
        userService.saveAndFlush(newUser);
        attr.addFlashAttribute("infoSuccessMsg","El cliente se añadio con éxito. ");
    	return "administrativo/add-client";
    	
    }
    
}
