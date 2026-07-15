package korobkin.nikita.error_free_text.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import korobkin.nikita.error_free_text.validation.annotation.ValidText;

public class ValidTextValidator implements ConstraintValidator<ValidText, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return value.chars()
                .mapToObj(c -> (char) c)
                .anyMatch(Character::isLetter);
    }
}