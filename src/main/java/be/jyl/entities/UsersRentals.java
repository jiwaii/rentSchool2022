package be.jyl.entities;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "users_rentals", schema = "rentschool2022")
public class UsersRentals {
    @Basic
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @Column(name = "id_rental", nullable = false)
    private int idRental;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_Users_Rentals", nullable = false)
    private int idUsersRentals;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_user", referencedColumnName = "id_user")
    private Users usersByIdUser;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_rental", referencedColumnName = "id_rental")
    private Rentals rentalsByIdRental;

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

    public int getIdUsersRentals() {
        return idUsersRentals;
    }

    public void setIdUsersRentals(int idUsersRentals) {
        this.idUsersRentals = idUsersRentals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersRentals that = (UsersRentals) o;
        return idUser == that.idUser && idRental == that.idRental && idUsersRentals == that.idUsersRentals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idRental, idUsersRentals);
    }

    public Users getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(Users usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }

    public Rentals getRentalsByIdRental() {
        return rentalsByIdRental;
    }

    public void setRentalsByIdRental(Rentals rentalsByIdRental) {
        this.rentalsByIdRental = rentalsByIdRental;
    }
}
