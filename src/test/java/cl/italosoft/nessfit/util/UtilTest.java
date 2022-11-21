package cl.italosoft.nessfit.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UtilTest
{
    String capitalizeResult = "Capitalize";

    @Test
    public void invertedCapitalizeToCapitalize()
    {
        String word = "cAPITALIZE";
        assertEquals(Util.capitalizer(word), capitalizeResult);
    }

    @Test
    public void nullArgumentToCapitalize()
    {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Util.capitalizer(null),"IllegalArgumentException was expected");

        Assertions.assertEquals("String to convert cannot be null", thrown.getMessage());
    }

    @Test
    public void emptyStringToCapitalize()
    {
        String word = "";
        assertEquals(Util.capitalizer(word),"");
    }

    @Test
    public void upperCaseToCapitalize()
    {
        String word = "CAPITALIZE";
        assertEquals(Util.capitalizer(word), capitalizeResult);
    }

    @Test
    public void lowerCaseToCapialize()
    {
        String word = "capitalize";
        assertEquals(Util.capitalizer(word), capitalizeResult);
    }

    @Test
    public void studlyCapsToCapitalize()
    {
        String word = "cApiTaLIzE";
        assertEquals(Util.capitalizer(word), capitalizeResult);
    }

    @Test
    public void oneLetterWord()
    {
        String word = "a";
        assertEquals(Util.capitalizer(word), "A");
    }

}