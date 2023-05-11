package ru.otus.domain;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public class Phone {
    @Id
    private final Long id;

    @Nonnull
    private final String number;

    @Nonnull
    private final String clientId;

    public Phone(@Nonnull String number, @Nonnull String clientId) {
        this(null, number, clientId);
    }

    @PersistenceCreator
    public Phone(Long id, @Nonnull String number, @Nonnull String clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    @Nonnull
    public String getNumber() {
        return number;
    }

    @Nonnull
    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
