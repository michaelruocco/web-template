package uk.co.mruoc.service;

import org.apache.commons.lang3.StringUtils;
import uk.co.mruoc.exception.InvalidCustomerIdException;
import uk.co.mruoc.exception.InvalidCustomerNameException;
import uk.co.mruoc.model.Customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerValidator {

    private static final Pattern PATTERN = Pattern.compile("(\\d{6})");

    public void validate(Customer customer) {
        validateId(customer);
        validateName(customer);
    }

    private void validateId(Customer customer) {
        if (!hasValidId(customer)) {
            throw new InvalidCustomerIdException(customer.getId());
        }
    }

    private void validateName(Customer customer) {
        if (!hasName(customer)) {
            throw new InvalidCustomerNameException(customer.getFullName());
        }
    }

    private boolean hasValidId(Customer customer) {
        Matcher matcher = PATTERN.matcher(customer.getId());
        return matcher.matches();
    }

    private boolean hasName(Customer customer) {
        if (StringUtils.isNotEmpty(customer.getFirstName().trim())) {
            return true;
        }
        return StringUtils.isNotEmpty(customer.getSurname().trim());
    }

}