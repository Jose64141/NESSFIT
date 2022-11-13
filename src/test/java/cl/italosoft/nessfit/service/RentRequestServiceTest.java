package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.*;
import cl.italosoft.nessfit.repository.RentRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RentRequestServiceTest {

    @Autowired
    private RentRequestService service;

    @MockBean
    RentRequestRepository rentRequestRepository;

    private User testUser;
    private DeportiveCenter testDeportiveCenter;
    private RentRequest testRentRequest;

    @BeforeEach
    void setUp()
    {
        testUser = new User("207676918","","","","",null,
                "",true,new Role(3));
        testDeportiveCenter = new DeportiveCenter("TATIO","ORELLA 1040", new Type(2,"Piscina"),
                45000,true);

        List<Date> rentDates = new ArrayList<Date>();
        rentDates.add(new Date(122,10,14));
        rentDates.add(new Date(122,10,16));
        testRentRequest = new RentRequest(testUser, testDeportiveCenter, "PENDIENTE",90000, rentDates,
                new Date(2022,11,12)  );
    }

    @Test
    void find()
    {
        when(rentRequestRepository.findById(testRentRequest.getId())).thenReturn(Optional.of(testRentRequest));
        RentRequest found = service.find(testRentRequest.getId());
        assertEquals(testRentRequest,found);
    }

    @Test
    void save()
    {
        when(rentRequestRepository.save(testRentRequest)).thenReturn(testRentRequest);
        RentRequest saved = service.save(testRentRequest);
        assertEquals(testRentRequest,saved);
    }

    @Test
    void saveAndFlush()
    {
        when(rentRequestRepository.saveAndFlush(testRentRequest)).thenReturn(testRentRequest);
        RentRequest saved = service.saveAndFlush(testRentRequest);
        assertEquals(testRentRequest,saved);
    }

    @Test
    void delete()
    {
        service.delete(testRentRequest.getId());
        verify(rentRequestRepository).deleteById(testRentRequest.getId());
    }

    @Test
    void listByUser()
    {
        List<Date> rentDates = new ArrayList<Date>();
        rentDates.add(new Date(122,10,13));
        RentRequest testRentRequest2 = new RentRequest(testUser, testDeportiveCenter,
                "PENDIENTE",45000, rentDates, new Date(2022,11,12));
        when(rentRequestRepository.findByUser_Rut(testUser.getRut()))
                .thenReturn(List.of(testRentRequest,testRentRequest2));
        List<RentRequest> found = service.listByUser(testUser.getRut());

        assertEquals(2,found.size());
    }

    @Test
    void listByDeportiveCenter()
    {
        when(rentRequestRepository.findByDeportiveCenter(testDeportiveCenter.getName()))
                .thenReturn(List.of(testRentRequest));
        List<RentRequest> found = service.listByDeportiveCenter(testDeportiveCenter.getName());
        assertEquals(1,found.size());
    }

    @Test
    void listDeportiveCenterDates()
    {
        when(rentRequestRepository.findDeportiveCenterDates("TATIO"))
                .thenReturn(testRentRequest.getDates());
        List<Date> found = service.listDeportiveCenterDates("TATIO");
        assertEquals(testRentRequest.getDates(),found);
    }

    @Test
    void flush()
    {
        service.flush();
        verify(rentRequestRepository).flush();
    }
}