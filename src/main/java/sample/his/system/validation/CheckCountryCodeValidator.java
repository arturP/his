package sample.his.system.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Locale;

/**
 *
 */
public class CheckCountryCodeValidator implements ConstraintValidator<CheckCountryCode, String> {
    @Override
    public void initialize(CheckCountryCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return Arrays.asList(Locale.getISOCountries()).contains(value);
    }
}
