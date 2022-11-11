package cl.italosoft.nessfit.util;

import cl.italosoft.nessfit.model.Role;
import cl.italosoft.nessfit.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RutValidatorTest {

    private RutValidator validator = new RutValidator();
    @Test
    void supports()
    {
        User user = new User();
        Role notUser = new Role();
        assertTrue(validator.supports(user.getClass()));
        assertFalse(validator.supports(notUser.getClass()));
    }

    @Test
    void validate()
    {
        User user = new User();
        Errors errors = new DirectFieldBindingResult(user, "user");
        validator.validate(user, errors);
        assertEquals(1,errors.getErrorCount());

        errors = new DirectFieldBindingResult(user, "user");
        user.setRut("209674327");
        validator.validate(user, errors);
        assertEquals(0,errors.getErrorCount());

        errors = new DirectFieldBindingResult(user, "user");
        user.setRut("20967432k");
        validator.validate(user, errors);
        assertEquals(1,errors.getErrorCount());

        errors = new DirectFieldBindingResult(user, "user");
        user.setRut("20967432-k");
        validator.validate(user, errors);
        assertEquals(1,errors.getErrorCount());
    }

    @Test
    void checkFormat()
    {

        String rut = "209674327";
        assertTrue(validator.checkFormat(rut));
        rut = "17977139K";
        assertTrue(validator.checkFormat(rut));
        rut = "17977139k";
        assertTrue(validator.checkFormat(rut));
        rut = "93780000";
        assertTrue(validator.checkFormat(rut));
        rut = "9378000";
        assertFalse(validator.checkFormat(rut));
    }

    @Test
    void getNumbers()
    {
        String rut = "209674327";
        assertEquals("20967432", validator.getNumbers(rut,9));
    }

    @Test
    void getCheckDigit()
    {
            String rut = "20967432";
            assertEquals('7', validator.getCheckDigit(rut));
            rut = "17977139";
            assertEquals('K', validator.getCheckDigit(rut));
            rut = "9378000";
            assertEquals('0', validator.getCheckDigit(rut));
    }
}