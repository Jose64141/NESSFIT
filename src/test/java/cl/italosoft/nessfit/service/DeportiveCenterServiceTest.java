package cl.italosoft.nessfit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.model.Type;
import cl.italosoft.nessfit.repository.DeportiveCenterRepository;
@SpringBootTest
class DeportiveCenterServiceTest {

	@Mock
	private DeportiveCenterRepository deportiveCenterRepository;

	@InjectMocks
	private DeportiveCenterService deportiveCenterService = new DeportiveCenterServiceImpl();

	private DeportiveCenter deportiveCenter;

	Type type = new Type(0,"CANCHA");
	Type type1 = new Type(1,"GIMNASIO");
	Type type2 = new Type(2,"PISCINA");
	Type type3 = new Type(3,"QUINCHO");
	Type type4 = new Type(4,"ESTADIO");

	@BeforeEach
	void setup(){
		MockitoAnnotations.initMocks(this);

		deportiveCenter = new DeportiveCenter("GREEN CROSS","AVENIDA ANGAMOS 001",type1,2000,true);
	}

    @Test
    void testFind() {
		when(deportiveCenterRepository.findById(deportiveCenter.getName())).thenReturn(Optional.of(deportiveCenter));
        assertNotNull(deportiveCenterService.find(deportiveCenter.getName()));
    }

    @Test
    void testFind2() {
        when(deportiveCenterRepository.findByNameAndType_id(deportiveCenter.getName(), deportiveCenter.getType().getId())).thenReturn(deportiveCenter);
        assertNotNull(deportiveCenterService.find(deportiveCenter.getName(),deportiveCenter.getType().getId()));
    }

    @Test
    void testFindByNameOrAddress() {
        when(deportiveCenterRepository.findByNameOrAddress(deportiveCenter.getName(), deportiveCenter.getAddress())).thenReturn(deportiveCenter);
        assertNotNull(deportiveCenterService.findByNameOrAddress(deportiveCenter.getName(), deportiveCenter.getAddress()));
    }

    @Test
    void testFlush() {
    	deportiveCenterService.flush();
    	verify(deportiveCenterRepository, atLeastOnce()).flush();
    }

    @Test
    void testList() {
    	DeportiveCenter deportiveCenter1 = new DeportiveCenter("SOKOL","AVENIDA ANGAMOS 003",type4,6000,true);
    	
    	when(deportiveCenterRepository.findAll()).thenReturn(List.of(deportiveCenter,deportiveCenter1));
        
    	List<DeportiveCenter> deportiveCenterList = deportiveCenterService.list();
    	
    	assertNotNull(deportiveCenterList);
    	assertEquals(2,deportiveCenterList.size());
    }

    @Test
    void testSave() {
    	when(deportiveCenterRepository.findById(deportiveCenter.getName())).thenReturn(Optional.empty());
    	
    	when(deportiveCenterRepository.save(deportiveCenter)).thenReturn(deportiveCenter);
    	
    	System.out.println(deportiveCenterRepository);
    	System.out.println(deportiveCenterService);
    	
    	DeportiveCenter newDeportiveCenter = deportiveCenterService.save(deportiveCenter);
    	
    	System.out.println(newDeportiveCenter);
    	
    	assertNotNull(newDeportiveCenter);
    }

    @Test
    void testSaveAndFlush() {
when(deportiveCenterRepository.findById(deportiveCenter.getName())).thenReturn(Optional.empty());
    	
    	when(deportiveCenterRepository.saveAndFlush(deportiveCenter)).thenReturn(deportiveCenter);
    	
    	System.out.println(deportiveCenterRepository);
    	System.out.println(deportiveCenterService);
    	
    	DeportiveCenter newDeportiveCenter = deportiveCenterService.saveAndFlush(deportiveCenter);
    	
    	System.out.println(newDeportiveCenter);
    	
    	assertNotNull(newDeportiveCenter);
    	verify(deportiveCenterRepository, atLeastOnce()).saveAndFlush(deportiveCenter);
        
    }
	
}
