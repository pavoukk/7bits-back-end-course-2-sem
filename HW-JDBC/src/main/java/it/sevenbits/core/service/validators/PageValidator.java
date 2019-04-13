package it.sevenbits.core.service.validators;

public class PageValidator implements IValidator<Integer> {
    @Override
    public boolean check(final Integer value) {
        return value > 0;
    }
}
