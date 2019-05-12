package it.sevenbits.core.service.validators;

import java.util.regex.Pattern;

/**
 * The class is used to check if ID suits the UUID style or not.
 */
public class IdValidator  implements IValidator <String> {
    private Pattern pattern;

    /**
     * The constructor with no parameters. It creates a UUID pattern.
     */
    public IdValidator() {
        pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");
    }

    @Override
    public boolean check(final String id) {
        return pattern.matcher(id).matches();
    }
}
