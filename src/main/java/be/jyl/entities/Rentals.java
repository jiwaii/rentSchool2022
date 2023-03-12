package be.jyl.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
@NamedQueries(value = {
        @NamedQuery(name = "Rentals.findAll",query = "select r From Rentals r"),
        @NamedQuery(name = "Rentals.findCurrentRentals", query = "SELECT r FROM Rentals r JOIN r.rentalsArticlesByIdRental ar WHERE ar.dateReturned IS NULL"),
        @NamedQuery(name = "Rentals.findLateRentals", query = "SELECT r FROM Rentals r JOIN r.rentalsArticlesByIdRental ar WHERE r.dateEnd < :today AND ar.dateReturned IS NULL")

})
@Entity
public class Rentals {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_rental", nullable = false)
    private int idRental;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private Users user;
    @Basic
    @Column(name = "dateBegin", nullable = false)
    private Date dateBegin;
    @Basic
    @Column(name = "dateEnd", nullable = false)
    private Date dateEnd;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_userRent")
    private Users userRent;

    public Collection<ArticlesRentals> getRentalsArticlesByIdRental() {
        return rentalsArticlesByIdRental;
    }

    public void setRentalsArticlesByIdRental(Collection<ArticlesRentals> rentalsArticlesByIdRental) {
        this.rentalsArticlesByIdRental = rentalsArticlesByIdRental;
    }

    @OneToMany(mappedBy = "rentalsByIdRental", cascade = CascadeType.PERSIST)
    private Collection<ArticlesRentals> rentalsArticlesByIdRental;


    public int getIdRental() {
        return idRental;
    }

    public void setIdRental(int idRental) {
        this.idRental = idRental;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    public Users getUserRent() {
        return userRent;
    }

    public void setUserRent(Users userRent) {
        this.userRent = userRent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rentals rentals = (Rentals) o;
        return idRental == rentals.idRental && user == rentals.user && userRent == rentals.userRent && Objects.equals(dateBegin, rentals.dateBegin) && Objects.equals(dateEnd, rentals.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRental, user, dateBegin, dateEnd, userRent);
    }

}
