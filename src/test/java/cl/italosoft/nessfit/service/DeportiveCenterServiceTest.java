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
	private DeportiveCenterService deportiveService;
	
	private DeportiveCenter deportiveCenter;
	
	Type type = new Type(0,"CANCHA");
	Type type1 = new Type(1,"GIMNASIO");
	Type type2 = new Type(2,"PISCINA");
	Type type3 = new Type(3,"QUINCHO");
	Type type4 = new Type(4,"ESTADIO");
	
	@Test
	void save() 
	{
		
		deportiveCenter = new DeportiveCenter("RINGFIT","AVENIDA ANGAMOS 001",type,2000,true);
		assertNotNull(deportiveService.save(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("SOKOL","AVENIDA ANGAMOS 002",type1,6000,false);
		assertNotNull(deportiveService.save(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("GREEN CROSS","AVENIDA ANGAMOS 003", type2,4000,false);
		assertNotNull(deportiveService.save(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("MATCH","AVENIDA ANGAMOS 004", type3,5000,true);
		assertNotNull(deportiveService.save(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("ESTADIO","AVENIDA ANGAMOS 003", type4,8000,true);
		assertNotNull(deportiveService.save(deportiveCenter));
	}
	
	@Test
	void saveAndFlush() 
	{
		deportiveCenter = new DeportiveCenter("RINGFIT","AVENIDA ANGAMOS 001",type,2000,true);
		assertNotNull(deportiveService.saveAndFlush(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("SOKOL","AVENIDA ANGAMOS 002",type1,6000,false);
		assertNotNull(deportiveService.saveAndFlush(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("GREEN CROSS","AVENIDA ANGAMOS 003", type2,4000,false);
		assertNotNull(deportiveService.saveAndFlush(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("MATCH","AVENIDA ANGAMOS 004", type3,5000,true);
		assertNotNull(deportiveService.saveAndFlush(deportiveCenter));
		
		deportiveCenter = new DeportiveCenter("ESTADIO","AVENIDA ANGAMOS 003", type4,8000,true);
		assertNotNull(deportiveService.saveAndFlush(deportiveCenter));
	}
	
	@Test
	void list() 
	{
		List<DeportiveCenter> list = deportiveService.list();
		assertNotNull(list);
	}
	
	@Test
	void find() 
	{
		deportiveCenter = new DeportiveCenter("GREEN CROSS","AVENIDA ANGAMOS 003",type2,4000,true);
		DeportiveCenter newDeportiveCenter = deportiveService.find("GREEN CROSS");
		assertSame(deportiveCenter.getName(),newDeportiveCenter.getName());
		
		newDeportiveCenter = deportiveService.find("GREEN CROSS", 2);
		assertSame(deportiveCenter.getType().getId(),newDeportiveCenter.getType().getId());
		
	}
	
	void findByNameOrAddress() 
	{
		deportiveCenter = new DeportiveCenter("MATCH","AVENIDA ANGAMOS 004",type3, 2000,false);
		DeportiveCenter newDeportiveCenter = deportiveService.findByNameOrAddress("MATCH", "AVENIDA ANGAMOS 007");
		assertSame(deportiveCenter.getName(), newDeportiveCenter.getName());
		assertSame(deportiveCenter.getAddress(),newDeportiveCenter.getAddress());
		
		newDeportiveCenter = deportiveService.findByNameOrAddress("GREEN CROSS", "AVENIDA ANGAMOS 003");
		assertSame(deportiveCenter.getName(), newDeportiveCenter.getName());
		assertSame(deportiveCenter.getAddress(),newDeportiveCenter.getAddress());
	}
	
}
