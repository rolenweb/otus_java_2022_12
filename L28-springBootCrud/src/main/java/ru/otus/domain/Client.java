package ru.otus.domain;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.stream.Collectors;


@Table("client")
public class Client implements Persistable<String> {

    @Id
    @Nonnull
    private final String id;

    @Nonnull
    private final String name;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @Transient
    private final boolean isNew;

    public Client(@Nonnull String id, @Nonnull String name, @Nonnull Address address, @Nonnull Set<Phone> phones, boolean isNew) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.isNew = isNew;
    }

    @PersistenceCreator
    public Client(@Nonnull String id, @Nonnull String name, @Nonnull Address address, @Nonnull Set<Phone> phones) {
        this(id, name, address, phones, false);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Address getAddress() {
        return address;
    }

    @Nonnull
    public Set<Phone> getPhones() {
        return phones;
    }

    public String getStreet() {
        return address.getStreet();
    }

    public String getPhoneNumbers() {
        return phones.stream().map(Phone::getNumber).collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
