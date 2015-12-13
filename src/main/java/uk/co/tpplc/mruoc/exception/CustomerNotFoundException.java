package uk.co.tpplc.mruoc.exception;

public class CustomerNotFoundException extends RuntimeException {

    private static final String MESSAGE = "customer id %s does not exist";

    public CustomerNotFoundException(String id) {
        super(String.format(MESSAGE, id));
    }

}