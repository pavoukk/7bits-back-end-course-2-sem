package it.sevenbits.core.service.validators;

/**
 * It is an interface that describes a class that checks if some argument is correct or not.
 *
 * @param <T> is a type of a value to check.
 */
public interface IValidator<T> {
    /**
     * The method checks the correctness of a string
     *
     * @param value the value to be tested
     * @return true if the string is correct and false otherwise
     */
    boolean check(T value);
}
