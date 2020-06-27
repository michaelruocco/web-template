package uk.co.mruoc.exception;

public class CustomerNotFoundException extends CustomerException {

    private static final String MESSAGE = "customer id %s does not exist";

    public CustomerNotFoundException(String id) {
        super(String.format(MESSAGE, id));
    }

}