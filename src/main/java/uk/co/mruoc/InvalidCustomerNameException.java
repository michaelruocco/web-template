package uk.co.mruoc;

public class InvalidCustomerNameException extends InvalidCustomerException {

    private static final String MESSAGE = "customer name %s is not valid, customer must have a first name or surname";

    public InvalidCustomerNameException(String name) {
        super(String.format(MESSAGE, name));
    }

}