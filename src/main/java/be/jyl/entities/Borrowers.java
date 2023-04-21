package be.jyl.entities;

import be.jyl.enums.ResponsibleType;

import javax.persistence.*;
import java.util.Objects;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Borrowers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "address", nullable = false, length = 100)
    private String address;
    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Basic
    @Column(name = "responsibleType", nullable = false)
    private ResponsibleType responsibleType;
    @Basic
    @Column(name = "firstname", nullable = false, length = 100)
    private String firstname;
    @Basic
    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;
    @Basic
    @Column(name = "barcode", nullable = true, length = 100)
    private String barcode;
    @Basic
    @Column(name = "id_city", nullable = true)
    private Integer idCity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ResponsibleType getResponsibleType() {
        return responsibleType;
    }

    public void setResponsibleType(ResponsibleType responsibleType) {
        this.responsibleType = responsibleType;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrowers borrowers = (Borrowers) o;
        return id == borrowers.id && Objects.equals(address, borrowers.address) && Objects.equals(email, borrowers.email) && Objects.equals(responsibleType, borrowers.responsibleType) && Objects.equals(firstname, borrowers.firstname) && Objects.equals(lastname, borrowers.lastname) && Objects.equals(barcode, borrowers.barcode) && Objects.equals(idCity, borrowers.idCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, email, responsibleType, firstname, lastname, barcode, idCity);
    }
}
