package it.sevenbits.core.service.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

/**
 * An annotation that defines a valid uuid.
 */
@Documented
@Constraint(validatedBy = ValidUuidConstraintValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUuid {
    /**
     * @return a message
     */
    String message() default "{invalid.uuid}";

    /**
     * @return .
     */
    Class<?>[] groups() default {};

    /**
     * @return .
     */
    Class<? extends Payload>[] payload() default {};
}
