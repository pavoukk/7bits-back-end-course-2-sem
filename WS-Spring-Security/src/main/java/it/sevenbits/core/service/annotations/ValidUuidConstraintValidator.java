package it.sevenbits.core.service.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * The uuid validator. It checks if the ID is valid or not.
 */
public class ValidUuidConstraintValidator implements ConstraintValidator<ValidUuid, String> {
    private Pattern pattern;

    /**
     * The initializator.
     *
     * @param constraintAnnotation an annotation.
     */
    @Override
    public void initialize(final ValidUuid constraintAnnotation) {
        pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");
    }

    /**
     * The method checks if the parameter if valid or not.
     *
     * @param s                          the parameter.
     * @param constraintValidatorContext the context.
     * @return true if the parameter is valid and false otherwise.
     */
    @Override
    public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(s).matches();
    }
}
