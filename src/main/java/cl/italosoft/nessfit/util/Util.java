package cl.italosoft.nessfit.util;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Dizk
 */
public class Util
{
    /**
     * Capitalize a String
     * @param word String to capitalize
     * @return String capitalized
     */
    public static String capitalize(@NotNull String word)
    {
        if (word == null) throw new IllegalArgumentException("String to convert cannot be null");
        if (word.isEmpty()) return word;
        return word.substring(0,1).toUpperCase().concat(word.substring(1).toLowerCase());
    }

    public static String capitelizeEachWord(@NotNull String word)
    {
        if (word == null) throw new IllegalArgumentException("String to convert cannot be null");
        if (word.isEmpty()) return word;
        String[] splitString = word.split(" ");
        StringBuilder capitalizedWord = new StringBuilder();
        for (String element: splitString)
        {
            capitalizedWord.append(capitalize(element+" "));
        }
        return capitalizedWord.toString().strip();
    }
}
