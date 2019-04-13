package it.sevenbits.core.service.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderValidator implements IValidator <String> {
    private List<String> allowed;

    public OrderValidator() {
        allowed = Collections.synchronizedList(new ArrayList<>());
        allowed.add("asc");
        allowed.add("desc");
    }

    @Override
    public boolean check(final String order) {
        return order != null && !order.isEmpty() && allowed.contains(order.toLowerCase());
    }
}
