package korobkin.nikita.error_free_text.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import korobkin.nikita.error_free_text.validation.validator.ValidTextValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTextValidator.class)
@Documented
public @interface ValidText {

    String message() default "Text must contain at least one letter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
