package cl.italosoft.nessfit.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RutValidatorTest {

    private RutValidator validator = new RutValidator();
    @Test
    void supports() {
    }

    @Test
    void validate() {
    }

    @Test
    void checkFormat() {

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
    void getNumbers() {
        String rut = "209674327";
        assertEquals("20967432", validator.getNumbers(rut,9));
    }

    @Test
    void getCheckDigit() {
            String rut = "20967432";
            assertEquals('7', validator.getCheckDigit(rut));
            rut = "17977139";
            assertEquals('K', validator.getCheckDigit(rut));
            rut = "9378000";
            assertEquals('0', validator.getCheckDigit(rut));
    }
}