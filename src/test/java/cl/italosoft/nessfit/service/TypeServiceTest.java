package cl.italosoft.nessfit.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import cl.italosoft.nessfit.model.Type;
import cl.italosoft.nessfit.repository.TypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class TypeServiceTest {

	   @Autowired
	    private TypeService typeservice;

	    @MockBean
	    private TypeRepository typerepository;

	    private Type type;
	    private Type type2;
	    private Type type3;
	    private Type type4;
	    private Type type5;
	    private List<Type> types = new ArrayList<>();

	    @BeforeEach
	    public void setup()
	    {
            type = new Type();
            type.setId(1);
            type.setName("cancha");
            
            type2 = new Type();
            type2.setId(2);
            type2.setName("gimnasio");
            
            type3 = new Type();
            type3.setId(3);
            type3.setName("piscina");
            
            type4 = new Type();
            type4.setId(4);
            type4.setName("quincho");
            
            type5 = new Type();
            type5.setId(5);
            type5.setName("estadio");
            
            types.add(type);
            types.add(type2);
            types.add(type3);
            types.add(type4);
            types.add(type5);
            
            
	    }
	    
	    @Test
	    void findAll()
	    {
	        Mockito.when(typerepository.findAll()).thenReturn(types);
 
	        List<Type> foundALL  = typeservice.list();
	        
	        assertNotNull(foundALL);
	        assertEquals(5, foundALL.size());
	    }

}
