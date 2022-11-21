package cl.italosoft.nessfit.util;


import javax.validation.constraints.NotNull;

public class Util
{
    /**
     * Capitalize a String
     * @param word String to capitalize
     * @return String capitalized
     */
    public static String capitalizer(@NotNull String word)
    {
        if (word == null) throw new IllegalArgumentException("String to convert cannot be null");
        if (word.isEmpty()) return word;
        return word.substring(0,1).toUpperCase().concat(word.substring(1).toLowerCase());
    }
}
