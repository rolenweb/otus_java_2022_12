package ru.otus.domain;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public class Address {

    @Id
    private final Long id;
    @Nonnull
    private final String clientId;

    @Nonnull
    private final String street;

    public Address(@Nonnull String clientId, @Nonnull String street) {
        this.id = null;
        this.clientId = clientId;
        this.street = street;
    }

    @PersistenceCreator
    public Address(Long id, @Nonnull String clientId, @Nonnull String street) {
        this.id = id;
        this.clientId = clientId;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    @Nonnull
    public String getStreet() {
        return street;
    }

    @Nonnull
    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
