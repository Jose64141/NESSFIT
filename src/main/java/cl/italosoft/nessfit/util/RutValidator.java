package cl.italosoft.nessfit.util;

import cl.italosoft.nessfit.model.User;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

        if(rut == null)
        {
            errors.rejectValue("rut", null, "Invalid RUT");
            return;
        }

        int checkDigit = 0;
        char originalCheckDigitChar = rut.charAt(rut.length()-1);
        int[] serie = {2,3,4,5,6,7};
        int seriePositionCounter = 0;
        for(int i = rut.length()-2 ; i>=0; i--)
        {
            if (seriePositionCounter == 6) seriePositionCounter = 0;
            int number = rut.charAt(i) - '0';
            number *= serie[seriePositionCounter];
            seriePositionCounter++;
            checkDigit += number;
        }
        checkDigit = checkDigit%11;
        checkDigit = 11 - checkDigit;
        char checkDigitChar = switch (checkDigit)
                {
                    case 11 -> '0';
                    case 10 -> 'K';
                    default -> (char) (checkDigit + '0');
                };
        if (checkDigitChar == originalCheckDigitChar)
            return;
        errors.rejectValue("rut", null, "Invalid RUT");
    }
}

