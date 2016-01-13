package uk.co.mruoc.exception;

public class CustomerIdAlreadyUsedException extends RuntimeException {

    private static final String MESSAGE = "customer id %s already in use";

    public CustomerIdAlreadyUsedException(String id) {
        super(String.format(MESSAGE, id));
    }

}