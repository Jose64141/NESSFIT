package cl.italosoft.nessfit.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Definition for RUT validator.
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = RutValidator.class)
public @interface Rut {
    String message() default "{Rut.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
