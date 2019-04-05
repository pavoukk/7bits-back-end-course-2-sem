package it.sevenbits.core.service.validators;

import java.util.regex.Pattern;

public class IdValidator implements IValidator {
    private Pattern pattern;

    public IdValidator(){
        pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");
    }

    @Override
    public boolean check(String id) {
        return pattern.matcher(id).matches();
    }
}
