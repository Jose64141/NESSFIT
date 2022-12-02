package cl.italosoft.nessfit.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UtilTest
{
    String capitalizeResult = "Capitalize";
    String capitalizeSpacedResult = "Capitalize Each Word";

    //region CAPITALIZE

    @Test
    public void invertedCapitalizeToCapitalize()
    {
        String word = "cAPITALIZE";
        assertEquals(Util.capitalize(word), capitalizeResult);
    }

    @Test
    public void nullArgumentToCapitalize()
    {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Util.capitalize(null),"IllegalArgumentException was expected");

        Assertions.assertEquals("String to convert cannot be null", thrown.getMessage());
    }

    @Test
    public void emptyStringToCapitalize()
    {
        String word = "";
        assertEquals(Util.capitalize(word),"");
    }

    @Test
    public void upperCaseToCapitalize()
    {
        String word = "CAPITALIZE";
        assertEquals(Util.capitalize(word), capitalizeResult);
    }

    @Test
    public void lowerCaseToCapialize()
    {
        String word = "capitalize";
        assertEquals(Util.capitalize(word), capitalizeResult);
    }

    @Test
    public void studlyCapsToCapitalize()
    {
        String word = "cApiTaLIzE";
        assertEquals(Util.capitalize(word), capitalizeResult);
    }

    @Test
    public void oneLetterWord()
    {
        String word = "a";
        assertEquals(Util.capitalize(word), "A");
    }

    //endregion

    //region CAPITALIZE EACH WORDS

    @Test
    public void invertedCapitalizePhraseToCapitalizePhrase()
    {
        String word = "cAPITALIZE eACH wORD";
        assertEquals(Util.capitelizeEachWord(word), capitalizeSpacedResult);
    }

    @Test
    public void nullArgumentToCapitalizePhrase()
    {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Util.capitelizeEachWord(null),"IllegalArgumentException was expected");

        Assertions.assertEquals("String to convert cannot be null", thrown.getMessage());
    }

    @Test
    public void emptyStringToCapitalizePhrase()
    {
        String word = "";
        assertEquals(Util.capitelizeEachWord(word),"");
    }

    @Test
    public void upperCasePhraseToCapitalizePhrase()
    {
        String word = "CAPITALIZE EACH WORD";
        assertEquals(Util.capitelizeEachWord(word), capitalizeSpacedResult);
    }

    @Test
    public void lowerCaseToCapializePhrase()
    {
        String word = "capitalize each word";
        assertEquals(Util.capitelizeEachWord(word), capitalizeSpacedResult);
    }

    @Test
    public void studlyCapsToCapitalizePhrase()
    {
        String word = "cApiTaLIzE eACh WoRd";
        assertEquals(Util.capitelizeEachWord(word), capitalizeSpacedResult);
    }

    @Test
    public void oneLetterWordPhrase()
    {
        String word = "a";
        assertEquals(Util.capitelizeEachWord(word), "A");
    }

    //endregion

}