package uk.co.mruoc.exception;

public class InvalidCustomerException extends CustomerException {

    public InvalidCustomerException(String message) {
        super(message);
    }

    public InvalidCustomerException(String message, Throwable cause) {
        super(message, cause);
    }

}
