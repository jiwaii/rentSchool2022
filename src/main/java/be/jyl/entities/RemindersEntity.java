package be.jyl.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Reminders", schema = "rentSchool2022", catalog = "")
public class RemindersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_reminder", nullable = false)
    private int idReminder;
    @Basic
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @Column(name = "id_rental", nullable = false)
    private int idRental;
    @Basic
    @Column(name = "reminderDate", nullable = false)
    private Date reminderDate;
    @Basic
    @Column(name = "message", nullable = false, length = 250)
    private String message;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity usersByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_rental", referencedColumnName = "id_rental", nullable = false)
    private RentalsEntity rentalsByIdRental;

    public int getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(int idReminder) {
        this.idReminder = idReminder;
    }

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

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemindersEntity that = (RemindersEntity) o;
        return idReminder == that.idReminder && idUser == that.idUser && idRental == that.idRental && Objects.equals(reminderDate, that.reminderDate) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReminder, idUser, idRental, reminderDate, message);
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
