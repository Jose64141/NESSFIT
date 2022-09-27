package cl.italosoft.nessfit.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class RutValidator implements ConstraintValidator<Rut, String>
{
    /**
     * Verifies if a RUT is valid.
     * @param rut must have the check digit on it last position.
     * @param context is the validator context.
     * @return Whether is valid or not.
     */
    @Override
    public boolean isValid(String rut, ConstraintValidatorContext context) {
        int checkDigit = 0;
        char originalCheckDigitChar = rut.charAt(rut.length()-1);
        int[] serie = {2,3,4,5,6,7};
        int seriePositionCounter = 0;
        for(int i = rut.length()-1 ; i>=0; i--)
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
        return checkDigitChar == originalCheckDigitChar;
    }
}

