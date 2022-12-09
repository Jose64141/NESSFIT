package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.model.RentRequest;
import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.service.DeportiveCenterService;
import cl.italosoft.nessfit.service.RentRequestService;
import cl.italosoft.nessfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/***
 * Controller for Cliente role pages
 */
@Controller
public class ClienteController
{

    @Autowired
    private UserService userService;
    @Autowired
    private DeportiveCenterService deportiveCenterService;
    @Autowired
    private RentRequestService rentRequestService;

    /**
     * Rent page
     * @param model view template model
     * @param id selected center name
     * @return page template
     */
    @GetMapping("/cliente/rent")
    public String rent(Model model, @RequestParam(required = false) String id)
    {

        model.addAttribute("centers", deportiveCenterService.listEnabled());
        DeportiveCenter selection = null;
        if (id != null)
        {
            selection = deportiveCenterService.find(id);
            if(!selection.getIsEnabled())
            {
                model.addAttribute("selection", null);
                return "cliente/rent";
            }
            selection.setName(selection.getName().toUpperCase());
            selection.setAddress(selection.getAddress().toUpperCase());
            List<Date> takenDates = rentRequestService.listDeportiveCenterDates(id);
            List<java.util.Date> dates = new ArrayList<>(8);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            for(int i = 0; i <= 7; i++)
            {
                java.util.Date date = cal.getTime();
                dates.add(date);
                cal.add(Calendar.DAY_OF_MONTH,1);
            }
            model.addAttribute("dates", dates);

            List<Boolean> availability = new ArrayList<>(8);
            for(int i = 0; i <= 7; i++)
            {
                if(takenDates.contains(new Date(dates.get(i).getTime())))
                    availability.add(false);
                else
                    availability.add(true);
            }
            model.addAttribute("avaliable", availability);
        }
        model.addAttribute("selection", selection);

        return "cliente/rent";
    }

    /**
     * Rent page
     * @param request HTTP request
     * @param model view template model
     * @param formBody form fields values
     * @param attr view redirect attributes
     * @return redirected page
     */
    @PostMapping("/cliente/rent")
    public String rent(HttpServletRequest request, Model model, @RequestBody MultiValueMap<String, String> formBody, RedirectAttributes attr)
    {
        User user = this.userService.find(request.getRemoteUser());
        DeportiveCenter deportiveCenter = this.deportiveCenterService.find(formBody.getFirst("center").toLowerCase());

        if(user == null || deportiveCenter == null)
        {
            attr.addFlashAttribute("errorMsg","Ha habido un error al procesar la solicitud.");
            return "redirect:/cliente/rent";
        }
        String[] datesStr = formBody.getFirst("dates").split(",");
        List<Date> dates = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        for (String date : datesStr)
        {
            try
            {
                dates.add(new Date(format.parse(date).getTime()));
            }
            catch (Exception e) {}
        }
        int total = 0;
        try
        {
            total = Integer.parseInt(formBody.getFirst("total"));
        }
        catch (Exception e)
        {
            attr.addFlashAttribute("errorMsg","Ha habido un error al procesar la solicitud.");
            return "redirect:/cliente/rent";
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = new Date(cal.getTimeInMillis());
        // save actual date
        RentRequest rentRequest = new RentRequest(user, deportiveCenter, "pendiente", total, dates, date);
        this.rentRequestService.saveAndFlush(rentRequest);

        attr.addFlashAttribute("successMsg","La solicitud se ha procesado con éxito.");
        return "redirect:/cliente/rent";
    }

    @GetMapping("/cliente/visualize-rent-requests")
    public String visualizeRentRequests(Model model,@PageableDefault(value = 5) Pageable page, @RequestParam(required = false) String deportivename)
    {
        model.addAttribute("requests", rentRequestService.findRentRequestByUser(SecurityContextHolder.getContext().getAuthentication().getName(), page));
        return "/cliente/visualize-rent-requests";
    }
    
    @GetMapping("/cliente/visualize-single-request")
    public String visualizeSingleRequest(Model model, @RequestParam int id)
    {
    	RentRequest request = rentRequestService.find(id);
    	if(request == null)
    		return "redirect:/cliente/visualize-rent-requests";
    	model.addAttribute("request", request);
        return "/cliente/visualize-single-request";
    }

    @GetMapping("/cliente/visualize-rent-requests/pdf")
    public String exportToPdf(HttpServletResponse response, Model model, Pageable page) throws DocumentException, IOException
    { 
    	List<RentRequest> request = rentRequestService.listByUser(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(request == null) {
    		return "/cliente/visualize-rent-requests";
    	}  	
        model.addAttribute("requests", rentRequestService.listByUser(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "/cliente/visualize-rent-requests/pdf";
    }


}


