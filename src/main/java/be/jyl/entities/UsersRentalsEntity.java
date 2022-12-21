package be.jyl.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Users_Rentals", schema = "rentSchool2022", catalog = "")
public class UsersRentalsEntity {
    @Basic
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @Column(name = "id_rental", nullable = false)
    private int idRental;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity usersByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_rental", referencedColumnName = "id_rental", nullable = false)
    private RentalsEntity rentalsByIdRental;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdRental() {
        return idRental;
    }

    public void setIdRental(int idRental) {
        this.idRental = idRental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersRentalsEntity that = (UsersRentalsEntity) o;
        return idUser == that.idUser && idRental == that.idRental;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idRental);
    }

    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }

    public RentalsEntity getRentalsByIdRental() {
        return rentalsByIdRental;
    }

    public void setRentalsByIdRental(RentalsEntity rentalsByIdRental) {
        this.rentalsByIdRental = rentalsByIdRental;
    }
}
