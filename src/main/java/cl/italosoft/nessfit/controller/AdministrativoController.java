package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.model.RentRequest;
import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.Type;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.DeportiveCenterService;
import cl.italosoft.nessfit.service.RentRequestService;
import cl.italosoft.nessfit.service.TypeService;
import cl.italosoft.nessfit.service.UserService;
import cl.italosoft.nessfit.util.RutValidator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import static cl.italosoft.nessfit.util.Util.capitelizeEachWord;

/***
 * Controller for Administrativo role pages
 */
@Controller
public class AdministrativoController 
{
	@Autowired
    private UserService userService;
	
	@Autowired
	private DeportiveCenterService deportiveCenterService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private RentRequestService rentRequestService;
    
    @Autowired
    private RutValidator rutValidator;

    /**
     * Adds RUT validator to the validation on BindingResult
     * @param binder binder Data binder object
     */
    @InitBinder("user")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(rutValidator);
    }

    /**
     * Manage client page
     * @param request HTTP request
     * @param rut new client rut
     * @param model view template model
     * @return page template
     */
    @GetMapping("/administrativo/manage-clients")
    public String manageUsers(HttpServletRequest request, @RequestParam(required = false) String rut, Model model)
    {
        if(rut != null)
        {
            User user = this.userService.find(rut,3);
            if(user != null)
            {
                boolean isEnabled = user.isEnabled();
                model.addAttribute("RUT",rut);
                model.addAttribute("name",capitelizeEachWord(user.getName()));
                model.addAttribute("firstLastName",capitelizeEachWord(user.getFirstLastName()));
                model.addAttribute("secondLastName",capitelizeEachWord(user.getSecondLastName()));
                model.addAttribute("isEnabled",isEnabled ? "Habilitado" : "Deshabilitado");
                model.addAttribute("actionName",isEnabled ? "Deshabilitar" : "Habilitar");
                model.addAttribute("tooltip",isEnabled ? "Deshabilitar un usuario implica que ya no podrá iniciar sesión"
                        : "Habilitar un usuario implica que ya podrá iniciar sesión");
            }
            else
            {
                model.addAttribute("nullMessage","No se han encontrado coincidencias");
            }
        }
        else
        {
            model.addAttribute("nullMessage","No ha seleccionado ningún usuario");
        }
        return "administrativo/manage-clients";
    }

    /**
     * Manage clients page
     * @param rut rut of user to manage
     * @param attr view redirect attributes
     * @return page template
     */
    @PostMapping("/administrativo/manage-clients")
    public String manageUsers(@RequestParam String rut, RedirectAttributes attr)
    {
        User user = this.userService.find(rut);
        user.setEnabled(!user.isEnabled());
        this.userService.saveAndFlush(user);
        attr.addFlashAttribute("isEnabledChanged","Se ha cambiado el estado con éxito");
        return "redirect:/administrativo/manage-clients?rut="+rut;
    }

    /**
     * Add client page
     * @param model view template model
     * @param newUser new User to add
     * @return page template
     */
    @GetMapping("/administrativo/add-client")
    public String addClient(Model model, User newUser)
    {
        if(newUser == null)
            newUser = new User();
        model.addAttribute("user", newUser);
    	return "administrativo/add-client";
    }

    /**
     * Add client page
     * @param model view template model
     * @param newUser new User to add
     * @param result validation errors on newInfo
     * @param attr view redirect attributes
     * @return page template
     */
    @PostMapping("/administrativo/add-client")
    public String addClient(Model model, @Valid User newUser, BindingResult result, RedirectAttributes attr)
    {
        newUser.setRut(newUser.getRut().strip().toLowerCase());
        newUser.setEmail(newUser.getEmail().strip().toLowerCase());

        if(this.userService.findByEmail(newUser.getEmail()) != null)
        {
            result.rejectValue("email",null,"El correo electrónico ya existe " +
                    "en el sistema. Intente iniciar sesión.");
        }
        if(this.userService.find(newUser.getRut()) != null)
        {
            result.rejectValue("rut",null,"El RUT ya existe " +
                    "en el sistema. Intente iniciar sesión.");
        }
        if(result.hasErrors())
        {
            return "administrativo/add-client";
        }

    	newUser.setEnabled(true);
    	Role role = new Role(3);
    	newUser.setRole(role);
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPasswordHash = passwordEncoder.encode(newUser.getRut());
        newUser.setPassword(newPasswordHash);
        newUser.setName(newUser.getName().strip().toLowerCase());
        newUser.setFirstLastName(newUser.getFirstLastName().strip().toLowerCase());
        newUser.setSecondLastName(newUser.getSecondLastName().strip().toLowerCase());
        userService.saveAndFlush(newUser);

        attr.addFlashAttribute("successMsg","El cliente se añadió con éxito. ");
        return "redirect:/administrativo/add-client";
    }

    /**
     * Add deportive center page
     * @param model view template model
     * @param newDeportiveCenter new deportive center to add
     * @return page template
     */
    @GetMapping("/administrativo/add-deportive-center")
    public String addDeportiveCenter(Model model, DeportiveCenter newDeportiveCenter) 
    {
    	if(newDeportiveCenter == null)
        {
            newDeportiveCenter = new DeportiveCenter();
            newDeportiveCenter.setType(new Type());
        }

    	model.addAttribute("deportiveCenter",newDeportiveCenter);
        model.addAttribute("types", typeService.list());
     	return "administrativo/add-deportive-center";
    }

    /**
     * Add deportive center page
     * @param model view template model
     * @param newDeportiveCenter new deportive center to add
     * @param result validation errors on newInfo
     * @param attr view redirect attrbutes
     * @return page template
     */
    @PostMapping("/administrativo/add-deportive-center")
    public String addDeportiveCenter(Model model,@Valid DeportiveCenter newDeportiveCenter, BindingResult result, RedirectAttributes attr)
    {	
    	newDeportiveCenter.setName(newDeportiveCenter.getName().toUpperCase().strip());
        newDeportiveCenter.setAddress(newDeportiveCenter.getAddress().toUpperCase().strip());
    	
    	DeportiveCenter deportiveCenter =  this.deportiveCenterService.find(newDeportiveCenter.getName());
    	if(deportiveCenter != null) 
    	{
            result.rejectValue("name", null, "El nombre ingresado ya existe en el sistema.");
    	}    	
    	if(result.hasErrors()) 
    	{
            model.addAttribute("types", typeService.list());
            return "administrativo/add-deportive-center";
    	}

        newDeportiveCenter.setName(newDeportiveCenter.getName().toLowerCase().strip());
        newDeportiveCenter.setAddress(newDeportiveCenter.getAddress().toLowerCase().strip());
        deportiveCenterService.saveAndFlush(newDeportiveCenter);
    	attr.addFlashAttribute("successMsg", "El recinto se añadió con éxito.");
    	return "redirect:/administrativo/add-deportive-center";
    }

    /**
     * Manage deportive centers page
     * @param model view template model
     * @param page Page with the results ordered in tuples
     * @param name name to search
     * @return page template
     */
    @GetMapping("/administrativo/manage-deportive-centers")
    public String manageDeportiveCenter(Model model, @PageableDefault(value = 5) Pageable page, @RequestParam(required = false) String name)
    {
        model.addAttribute("centers", deportiveCenterService.findByName(name, page));
        return "administrativo/manage-deportive-centers";
    }

    /**
     * Edit deportive center page
     * @param model view template model
     * @param name name of the deportive center to edit
     * @return page template
     */
    @GetMapping("/administrativo/edit-deportive-center")
    public String editDeportiveCenter(Model model, @RequestParam String name)
    {
        DeportiveCenter deportiveCenter = this.deportiveCenterService.find(name);
        model.addAttribute("types", typeService.list());
        if(deportiveCenter == null)
        {
            return "redirect:/administrativo/manage-deportive-centers";
        }
        deportiveCenter.setName(deportiveCenter.getName().toUpperCase());
        deportiveCenter.setAddress(deportiveCenter.getAddress().toUpperCase());
        model.addAttribute("deportiveCenter",deportiveCenter);
        return "administrativo/edit-deportive-center";
    }

    /**
     * Edit deportive center page
     * @param model view template model
     * @param deportiveCenter deportive center to edit
     * @param result validation errors on newInfo
     * @param attr view redirect attributes
     * @return page template
     */
    @PostMapping("/administrativo/edit-deportive-center")
    public String editDeportiveCenter(Model model, @Valid DeportiveCenter deportiveCenter, BindingResult result, RedirectAttributes attr)
    {
        model.addAttribute("types", typeService.list());

        if(result.hasErrors())
        {
            return "administrativo/edit-deportive-center";
        }
        String name = deportiveCenter.getName();
        DeportiveCenter completeCenter = deportiveCenterService.find(name);
        if(completeCenter == null)
        {
            attr.addFlashAttribute("errorMsg","Ha habido un problema. Intente nuevamente.");
            return "redirect:/administrativo/manage-deportive-centers";
        }
        completeCenter.setAddress(deportiveCenter.getAddress().toLowerCase().strip());
        completeCenter.setType(deportiveCenter.getType());
        completeCenter.setIsEnabled(deportiveCenter.getIsEnabled());
        completeCenter.setCostPerDay(deportiveCenter.getCostPerDay());
        deportiveCenterService.saveAndFlush(completeCenter);
        attr.addFlashAttribute("successMsg","Los cambios se han realizado con éxito.");

        return "redirect:/administrativo/edit-deportive-center?name="+name+"&successMsg="+"Los cambios se han realizado con %C3%A9xito.";
    }

    @GetMapping("administrativo/manage-rent-requests")
    public String manageRentRequests(Model model, @PageableDefault(value = 5) Pageable page, @RequestParam(required = false) String rut)
    {
        if(rut != null)
            model.addAttribute("requests",rentRequestService.findRentRequestByUser(rut,"pendiente",page));
    	else
            model.addAttribute("requests",rentRequestService.findByStatus("pendiente",page));
        return "administrativo/manage-rent-requests";
    }

    /**
     * Review rent request page
     * @param model view template model
     * @param id id of the request to review
     * @return page template
     */
    @GetMapping("administrativo/review-rent-request")
    public String reviewRentRequest(Model model, @RequestParam int id)
    {
        RentRequest request = rentRequestService.find(id);
        if (request == null)
            return "redirect:/administrativo/manage-rent-requests";
        model.addAttribute("request",request);
        return "administrativo/review-rent-request";
    }

    /**
     * Review rent request page
     * @param rentRequest form object with new status
     * @param attr redirect attributes
     * @return page template
     */
    @PostMapping("administrativo/review-rent-request")
    public String reviewRentRequest(RentRequest rentRequest, RedirectAttributes attr)
    {
        String action = rentRequest.getStatus();
        if(!(action.equals("aprobada") || action.equals("rechazada")))
        {
            attr.addFlashAttribute("errorMsg","Ha ocurrido un error.");
            return "redirect:/administrativo/manage-rent-requests";
        }

        int id = rentRequest.getId();
        rentRequest = rentRequestService.find(id);
        if (rentRequest == null)
        {
            attr.addFlashAttribute("errorMsg","Ha ocurrido un error.");
            return "redirect:/administrativo/manage-rent-requests";
        }

        StringBuilder response = new StringBuilder("La solicitud fue ");
        response.append(action);
        response.append(" con éxito.");
        rentRequest.setStatus(action);
        rentRequestService.saveAndFlush(rentRequest);
        attr.addFlashAttribute("successMsg",response);
        return "redirect:/administrativo/manage-rent-requests";
    }

    @GetMapping("/administrativo/statistics")
    public String viewStatistics(Model model, @RequestParam(name = "start", required = false, defaultValue = "") String start, @RequestParam(name = "end", required = false, defaultValue = "") String end)throws ParseException
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate;
        if (end.isBlank())
        {
            endDate = new Date(cal.getTimeInMillis());
            end = formatter.format(endDate);
        }
        else
            endDate = new Date((formatter.parse(end)).getTime());
        Date startDate;
        if (start.isBlank())
        {
            cal.add(Calendar.DAY_OF_MONTH,-7);
            startDate = new Date(cal.getTimeInMillis());
            start = formatter.format(startDate);
        }
        else
            startDate = new Date((formatter.parse(start)).getTime());
        if (endDate.before(startDate))
        {
            end = start;
            endDate = startDate;
        }

    	int canchaCounter = 0;
    	int gimnasioCounter = 0;
    	int piscinaCounter = 0;
    	int quinchoCounter = 0;
    	int estadioCounter = 0;

    	List<RentRequest> requests = rentRequestService.findByDateBetween(startDate, endDate);

    	for (RentRequest rentRequest : requests) {
    		switch ( rentRequest.getDeportiveCenter().getType().getName() ) {
    		case "cancha":
    			canchaCounter++;
    			break;
    		case "gimnasio":
    			gimnasioCounter++;
    			break;
    		case "piscina":
    			piscinaCounter++;
    			break;
    		case "quincho":
    			quinchoCounter++;
    			break;
    		case "estadio":
    			estadioCounter++;
    			break;
    		default:
    			break;
			}
    	}

    	model.addAttribute("solicitudes", requests);
    	model.addAttribute("canchaQty", canchaCounter);
    	model.addAttribute("gimnasioQty", gimnasioCounter);
    	model.addAttribute("piscinaQty", piscinaCounter);
    	model.addAttribute("quinchoQty", quinchoCounter);
    	model.addAttribute("estadioQty", estadioCounter);

    	model.addAttribute("start", start);
    	model.addAttribute("end", end);

    	return "administrativo/statistics";
    }
}
