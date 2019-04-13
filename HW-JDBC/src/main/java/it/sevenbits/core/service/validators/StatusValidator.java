package it.sevenbits.core.service.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class is needed to check the correctness of a status. It has a list of correct values.
 */
public class StatusValidator implements IValidator <String> {
    private List<String> statusList;

    /**
     * A constructor with no parameters.
     */
    public StatusValidator() {
        statusList = Collections.synchronizedList(new ArrayList<>());
        statusList.add("inbox");
        statusList.add("done");
    }

    @Override
    public boolean check(final String status) {
        return status != null && !status.isEmpty() && statusList.contains(status);
    }
}
