package be.jyl.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Rentals", schema = "rentSchool2022", catalog = "")
public class RentalsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_rental", nullable = false)
    private int idRental;
    @Basic
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @Column(name = "dateBegin", nullable = false)
    private Date dateBegin;
    @Basic
    @Column(name = "dateEnd", nullable = false)
    private Date dateEnd;
    @Basic
    @Column(name = "id_userRent", nullable = false)
    private int idUserRent;
    @OneToMany(mappedBy = "rentalsByIdRental")
    private Collection<ArticlesRentalsEntity> articlesRentalsByIdRental;
    @OneToMany(mappedBy = "rentalsByIdRental")
    private Collection<RemindersEntity> remindersByIdRental;
    @OneToMany(mappedBy = "rentalsByIdRental")
    private Collection<UsersRentalsEntity> usersRentalsByIdRental;

    public int getIdRental() {
        return idRental;
    }

    public void setIdRental(int idRental) {
        this.idRental = idRental;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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

    public int getIdUserRent() {
        return idUserRent;
    }

    public void setIdUserRent(int idUserRent) {
        this.idUserRent = idUserRent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalsEntity that = (RentalsEntity) o;
        return idRental == that.idRental && idUser == that.idUser && idUserRent == that.idUserRent && Objects.equals(dateBegin, that.dateBegin) && Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRental, idUser, dateBegin, dateEnd, idUserRent);
    }

    public Collection<ArticlesRentalsEntity> getArticlesRentalsByIdRental() {
        return articlesRentalsByIdRental;
    }

    public void setArticlesRentalsByIdRental(Collection<ArticlesRentalsEntity> articlesRentalsByIdRental) {
        this.articlesRentalsByIdRental = articlesRentalsByIdRental;
    }

    public Collection<RemindersEntity> getRemindersByIdRental() {
        return remindersByIdRental;
    }

    public void setRemindersByIdRental(Collection<RemindersEntity> remindersByIdRental) {
        this.remindersByIdRental = remindersByIdRental;
    }

    public Collection<UsersRentalsEntity> getUsersRentalsByIdRental() {
        return usersRentalsByIdRental;
    }

    public void setUsersRentalsByIdRental(Collection<UsersRentalsEntity> usersRentalsByIdRental) {
        this.usersRentalsByIdRental = usersRentalsByIdRental;
    }
}
