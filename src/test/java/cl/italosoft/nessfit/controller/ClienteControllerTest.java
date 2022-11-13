package cl.italosoft.nessfit.controller;

import cl.italosoft.nessfit.NessfitApplication;
import cl.italosoft.nessfit.model.*;
import cl.italosoft.nessfit.service.DeportiveCenterService;
import cl.italosoft.nessfit.service.RentRequestService;
import cl.italosoft.nessfit.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private DeportiveCenterService deportiveCenterService;
    @MockBean
    private RentRequestService rentRequestService;

    private List<DeportiveCenter> deportiveCenters;
    private List<RentRequest> rentRequests;
    private User user;


    @BeforeEach
    void setUp()
    {
        user = new User("207676918","","","","",null,"",true,new Role(3));
        when(userService.find("207676918")).thenReturn(user);
        user = new User("209674327","","","","",null,"",true,new Role(2));
        when(userService.find("209674327")).thenReturn(user);

        this.deportiveCenters = new ArrayList<>();
        deportiveCenters.add(new DeportiveCenter("TATIO","ORELLA 1040", new Type(2,"Piscina"), 45000,true));
        deportiveCenters.add(new DeportiveCenter("SPLIT","VLADIMIR ZAAVEDRA 355", new Type(0,"Cancha"), 30000,true));
        when(deportiveCenterService.listEnabled()).thenReturn(this.deportiveCenters);
        when(deportiveCenterService.find("TATIO")).thenReturn(deportiveCenters.get(1));
    }

    @Test
    void rent() throws Exception
    {
        this.mockMvc.perform(get("/cliente/rent").with(user("207676918").roles("CLIENTE")) )
                .andExpect(status().isOk())
                .andExpect(model().attribute("centers",deportiveCenters))
                .andExpect(model().attribute("selection", nullValue()))
                .andExpect(model().attributeDoesNotExist("dates"))
                .andExpect(model().attributeDoesNotExist("available"))
                .andExpect(view().name("cliente/rent"));
    }
    @Test
    void rentDisabled() throws Exception
    {
        when(deportiveCenterService.find("SOKOL")).thenReturn(new DeportiveCenter("SOKOL","ESMERALDA", new Type(2,"Piscina"), 1500,false));
        this.mockMvc.perform(get("/cliente/rent?id={id}", "SOKOL")
                        .with(user("207676918").roles("CLIENTE")) )
                .andExpect(status().isOk())
                .andExpect(model().attribute("centers",deportiveCenters))
                .andExpect(model().attribute("selection", nullValue()))
                .andExpect(model().attributeDoesNotExist("dates"))
                .andExpect(model().attributeDoesNotExist("available"))
                .andExpect(view().name("cliente/rent"));
    }
    @Test
    void rentOne() throws Exception
    {
        List<Date> rentDates = new ArrayList<Date>();
        rentDates.add(new Date(122,10,14));
        rentDates.add(new Date(122,10,16));

        when(rentRequestService.listDeportiveCenterDates("TATIO")).thenReturn(rentDates);

        RentRequest rentRequest = new RentRequest(user, deportiveCenters.get(1), "PENDIENTE",90000, rentDates, new Date(2022,11,12)  );

        List<java.util.Date> dates = new ArrayList<>(8);
        dates.add(new java.util.Date(122,10,12));
        dates.add(new java.util.Date(122,10,13));
        dates.add(new java.util.Date(122,10,14));
        dates.add(new java.util.Date(122,10,15));
        dates.add(new java.util.Date(122,10,16));
        dates.add(new java.util.Date(122,10,17));
        dates.add(new java.util.Date(122,10,18));
        dates.add(new java.util.Date(122,10,19));


        List<Boolean> availability = new ArrayList<>(8);
        availability.add(true);
        availability.add(true);
        availability.add(false);
        availability.add(true);
        availability.add(false);
        availability.add(true);
        availability.add(true);
        availability.add(true);

        this.mockMvc.perform(get("/cliente/rent?id={id}", "TATIO").with(user("207676918").roles("CLIENTE")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("centers",deportiveCenters))
                .andExpect(model().attribute("selection",deportiveCenters.get(1)))
                .andExpect(model().attribute("dates", dates))
                .andExpect(model().attribute("avaliable", availability))
                .andExpect(view().name("cliente/rent"));
    }

    @Test
    void testRent() throws Exception {
        List<Date> rentDates = new ArrayList<Date>();
        rentDates.add(new Date(122,10,14));
        rentDates.add(new Date(122,10,16));
        RentRequest rentRequest = new RentRequest(user, deportiveCenters.get(1), "PENDIENTE",90000, rentDates, new Date(2022,11,12)  );
        when(rentRequestService.saveAndFlush(any(RentRequest.class))).thenReturn(rentRequest);

        this.mockMvc.perform(post("/cliente/rent?id={id}", "TATIO")
                        .with(user("207676918").roles("CLIENTE"))
                        .param("center","TATIO")
                        .param("dates","18/11/2022,19/11/2022")
                        .param("total", "90000")
                )
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<RentRequest> formObjectArgument = ArgumentCaptor.forClass(RentRequest.class);
        //assertFalse(rentRequestService.listByDeportiveCenter("TATIO").isEmpty());
        verify(rentRequestService).saveAndFlush(formObjectArgument.capture());
    }

    @Test
    void testRentInvalidUser() throws Exception
    {
        this.mockMvc.perform(post("/cliente/rent?id={id}", "TATIO")
                        .with(user("20767691k").roles("CLIENTE"))
                        .param("center","TATIO")
                        .param("dates","18/11/2022,19/11/2022")
                        .param("total", "90000")
                )
                .andExpect(status().is3xxRedirection());
        verify(rentRequestService, never()).saveAndFlush(any(RentRequest.class));
    }

    @Test
    void testRentInvalidCenter() throws Exception
    {
        this.mockMvc.perform(post("/cliente/rent?id={id}", "TATIO")
                        .with(user("207676918").roles("CLIENTE"))
                        .param("center","")
                        .param("dates","18/11/2022,19/11/2022")
                        .param("total", "90000")
                )
                .andExpect(status().is3xxRedirection());
        verify(rentRequestService, never()).saveAndFlush(any(RentRequest.class));
    }

    @Test
    void testRentInvalidDate() throws Exception
    {
        this.mockMvc.perform(post("/cliente/rent?id={id}", "TATIO")
                        .with(user("207676918").roles("CLIENTE"))
                        .param("center","TATIO")
                        .param("dates","18/11/2022,19/11/2022")
                        .param("total", "$90000")
                )
                .andExpect(status().is3xxRedirection());
        verify(rentRequestService, never()).saveAndFlush(any(RentRequest.class));
    }
}