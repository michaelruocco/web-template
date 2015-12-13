package uk.co.tpplc.mruoc.service;

import uk.co.tpplc.mruoc.model.Customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerValidator {

    private static final Pattern PATTERN = Pattern.compile("(\\d{6})");

    public boolean hasValidId(Customer customer) {
        Matcher matcher = PATTERN.matcher(customer.getId());
        return matcher.matches();
    }

}