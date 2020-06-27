package uk.co.mruoc.model;

import java.math.BigDecimal;

public class Customer {

    private final String id;
    private final String firstName;
    private final String surname;
    private final BigDecimal balance;

    public Customer(CustomerBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.surname = builder.surname;
        this.balance = builder.balance;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getFullName() {
        StringBuilder s = new StringBuilder();
        s.append(firstName);
        s.append(" ");
        s.append(surname);
        return s.toString().trim();
    }

    public static class CustomerBuilder {

        public String id;
        public String firstName;
        public String surname;
        public BigDecimal balance;

        public CustomerBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public CustomerBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public CustomerBuilder setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }

    }

}
