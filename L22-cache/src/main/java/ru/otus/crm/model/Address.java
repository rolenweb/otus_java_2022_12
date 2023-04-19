package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Cloneable{
    @Id
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "street", nullable = false)
    private String street;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "address")
    private Client client;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Override
    public Address clone() {
        return new Address(
                this.id,
                this.street
        );
    }
}
