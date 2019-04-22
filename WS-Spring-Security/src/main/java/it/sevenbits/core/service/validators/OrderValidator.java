package it.sevenbits.core.service.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A validator of order value.
 */
public class OrderValidator implements IValidator<String> {
    private List<String> allowed;

    /**
     * The constructor. Creates a collection containing allowed values.
     */
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
