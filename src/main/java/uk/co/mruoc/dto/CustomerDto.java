package uk.co.mruoc.dto;

import java.math.BigDecimal;

public class CustomerDto {

    private String id;
    private String firstName;
    private String surname;
    private BigDecimal balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getFullName() {
        StringBuilder s = new StringBuilder();
        s.append(firstName);
        s.append(" ");
        s.append(surname);
        return s.toString();
    }

}
