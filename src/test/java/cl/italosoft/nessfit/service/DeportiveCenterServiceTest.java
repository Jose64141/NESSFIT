package cl.italosoft.nessfit.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.model.Type;
import cl.italosoft.nessfit.repository.DeportiveCenterRepository;
@SpringBootTest
class DeportiveCenterServiceTest {

	@Autowired
	private DeportiveCenterService deportiveService = new DeportiveCenterServiceImpl();
	
	private DeportiveCenter deportiveCenter;
	
	@Autowired
	private DeportiveCenterRepository deportiveCenterRepository;

	@Test
	void save() 
	{
		Type type = new Type(0,"CANCHA");
		Type type1 = new Type(1,"GIMNASIO");
		Type type2 = new Type(2,"PISCINA");
		Type type3 = new Type(3,"QUINCHO");
		Type type4 = new Type(4,"ESTADIO");
		
		deportiveCenter = new DeportiveCenter("RINGFIT","AVENIDA ANGAMOS 001",type,2000,true);
		assertNotNull(deportiveCenterRepository.save(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("SOKOL","AVENIDA ANGAMOS 002",type1,6000,false);
		assertNotNull(deportiveCenterRepository.save(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("GREEN CROSS","AVENIDA ANGAMOS 003", type2,4000,false);
		assertNotNull(deportiveCenterRepository.save(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("MATCH","AVENIDA ANGAMOS 004", type3,5000,true);
		assertNotNull(deportiveCenterRepository.save(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("ESTADIO","AVENIDA ANGAMOS 003", type4,8000,true);
		assertNotNull(deportiveCenterRepository.save(deportiveCenter));
	}
	
	@Test
	void saveAndFlush() 
	{
		Type type = new Type(0,"CANCHA");
		Type type1 = new Type(1,"GIMNASIO");
		Type type2 = new Type(2,"PISCINA");
		Type type3 = new Type(3,"QUINCHO");
		Type type4 = new Type(4,"ESTADIO");
		
		deportiveCenter = new DeportiveCenter("RINGFIT","AVENIDA ANGAMOS 001",type,2000,true);
		assertNotNull(deportiveCenterRepository.saveAndFlush(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("SOKOL","AVENIDA ANGAMOS 002",type1,6000,false);
		assertNotNull(deportiveCenterRepository.saveAndFlush(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("GREEN CROSS","AVENIDA ANGAMOS 003", type2,4000,false);
		assertNotNull(deportiveCenterRepository.saveAndFlush(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("MATCH","AVENIDA ANGAMOS 004", type3,5000,true);
		assertNotNull(deportiveCenterRepository.saveAndFlush(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("ESTADIO","AVENIDA ANGAMOS 003", type4,8000,true);
		assertNotNull(deportiveCenterRepository.saveAndFlush(deportiveCenter));
	}
	
	@Test
	void list() 
	{
		List<DeportiveCenter> list = deportiveService.list();
		assertNotNull(list);
	}
	
}
