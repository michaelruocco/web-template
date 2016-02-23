package uk.co.mruoc;

public class InvalidCustomerIdException extends InvalidCustomerException {

    private static final String MESSAGE = "customer id %s is not valid, it must be 6 digits";

    public InvalidCustomerIdException(String id) {
        super(String.format(MESSAGE, id));
    }

}