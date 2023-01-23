package be.jyl.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
@NamedQueries(value = {
        @NamedQuery(name = "Rentals.findAll",query = "select r From Rentals r")
})
@Entity
public class Rentals {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_rental", nullable = false)
    private int idRental;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_user", referencedColumnName = "id_user")
    private Users idUser;
    @Basic
    @Column(name = "dateBegin", nullable = false)
    private Date dateBegin;
    @Basic
    @Column(name = "dateEnd", nullable = false)
    private Date dateEnd;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_userRent", referencedColumnName = "id_user")
    private Users idUserRent;

    public Collection<ArticlesRentals> getRentalsArticlesByIdRental() {
        return rentalsArticlesByIdRental;
    }

    public void setRentalsArticlesByIdRental(Collection<ArticlesRentals> rentalsArticlesByIdRental) {
        this.rentalsArticlesByIdRental = rentalsArticlesByIdRental;
    }

    @OneToMany(mappedBy = "rentalsByIdRental")
    private Collection<ArticlesRentals> rentalsArticlesByIdRental;



    public int getIdRental() {
        return idRental;
    }

    public void setIdRental(int idRental) {
        this.idRental = idRental;
    }

    public Users getIdUser() {
        return idUser;
    }

    public void setIdUser(Users idUser) {
        this.idUser = idUser;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Users getIdUserRent() {
        return idUserRent;
    }

    public void setIdUserRent(int Users) {
        this.idUserRent = idUserRent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rentals rentals = (Rentals) o;
        return idRental == rentals.idRental && idUser == rentals.idUser && idUserRent == rentals.idUserRent && Objects.equals(dateBegin, rentals.dateBegin) && Objects.equals(dateEnd, rentals.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRental, idUser, dateBegin, dateEnd, idUserRent);
    }

}
