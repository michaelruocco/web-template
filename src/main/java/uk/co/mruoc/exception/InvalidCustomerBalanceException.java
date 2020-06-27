package uk.co.mruoc.exception;

public class InvalidCustomerBalanceException extends InvalidCustomerException {

    private static final String MESSAGE = "customer balance %s is not valid, it must be a numeric value";

    public InvalidCustomerBalanceException(String id, Throwable cause) {
        super(String.format(MESSAGE, id), cause);
    }

}