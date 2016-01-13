package uk.co.mruoc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class Customer {

    @Id
    private String id;
    private String firstName;
    private String surname;
    private BigDecimal balance;

    public Customer() { }

    private Customer(CustomerBuilder builder) {
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

    public static class CustomerBuilder {

        private String id;
        private String firstName;
        private String surname;
        private BigDecimal balance;

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
