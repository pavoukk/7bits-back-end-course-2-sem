package it.sevenbits.core.service.validators;

/**
 * It is an interface that describes a class that checks if some argument is correct or not.
 */
public interface IValidator {
    /**
     * The method checks the correctness of a string
     *
     * @param string the string to be tested
     * @return true if the string is correct and false otherwise
     */
    boolean check(String string);
}
