package cl.italosoft.nessfit.util;

import cl.italosoft.nessfit.model.User;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

@Component
public class RutValidator implements Validator
{
    @Override
    public boolean supports(Class<?> clazz)
    {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        User user = (User) target;
        String rut = user.getRut();
        if(rut == null || !checkFormat(rut))
        {
            errors.rejectValue("rut", null, "El RUT es inválido.");
            return;
        }
        rut = rut.toUpperCase();
        int rutLenght = rut.length();
        char originalCheckDigitChar = rut.charAt(rutLenght-1);
        char checkDigitChar = 0;

        checkDigitChar = getCheckDigit(getNumbers(rut,rutLenght));

        if (checkDigitChar == originalCheckDigitChar)
            return;
        errors.rejectValue("rut", null, "El RUT es inválido.");
    }

    public boolean checkFormat(String rut)
    {
        Pattern rutPattern = Pattern.compile("\\d{1,2}\\d{3}\\d{3}[Kk0-9]");
        return rutPattern.matcher(rut).matches();
    }
    public String getNumbers(String rut, int lenght)
    {
        return rut.substring(0,lenght-1);
    }
    public char getCheckDigit(String rutNumbers) {
        int checkDigit = 0;
        int[] serie = {2,3,4,5,6,7};
        int seriePositionCounter = 0;
        for(int i = rutNumbers.length()-1 ; i>=0; i--)
        {
            int digit = rutNumbers.charAt(i);
            if (seriePositionCounter == 6) seriePositionCounter = 0;
            digit -= '0';
            digit *= serie[seriePositionCounter];
            seriePositionCounter++;
            checkDigit += digit;
        }
        checkDigit = checkDigit%11;
        checkDigit = 11 - checkDigit;
        char checkDigitChar = switch (checkDigit)
                {
                    case 11 -> '0';
                    case 10 -> 'K';
                    default -> (char) (checkDigit + '0');
                };
        return checkDigitChar;
    }
}

