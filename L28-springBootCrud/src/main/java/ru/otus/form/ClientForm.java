package ru.otus.form;

import java.util.Set;

public class ClientForm {
    private String name;
    private String street;
    private Set<String> phones;

    public void setName(String name) {
        this.name = name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public Set<String> getPhones() {
        return phones;
    }
}
