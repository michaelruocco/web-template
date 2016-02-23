package uk.co.mruoc;

public class InvalidCustomerException extends CustomerException {

    public InvalidCustomerException(String message) {
        super(message);
    }

    public InvalidCustomerException(String message, Throwable cause) {
        super(message, cause);
    }

}
