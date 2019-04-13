package it.sevenbits.core.service.validators;

public class PageSizeValidator implements IValidator<Integer> {
    private final int MIN = 10;
    private final int MAX = 50;
    @Override
    public boolean check(final Integer value) {
        return value >= MIN && value <= MAX;
    }
}
