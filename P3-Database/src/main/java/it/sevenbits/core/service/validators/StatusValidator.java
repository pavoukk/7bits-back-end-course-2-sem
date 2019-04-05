package it.sevenbits.core.service.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatusValidator implements IValidator {
    private List<String> statusList;

    public StatusValidator() {
        statusList = Collections.synchronizedList(new ArrayList<>());
        statusList.add("inbox");
        statusList.add("done");
    }

    @Override
    public boolean check(String status) {
        return statusList.contains(status);
    }
}
