package be.jyl.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Reminders {
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
    @PrimaryKeyJoinColumn(name = "id_user", referencedColumnName = "id_user")
    private Users usersByIdUser;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_rental", referencedColumnName = "id_rental")
    private Rentals rentalsByIdRental;

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
        Reminders reminders = (Reminders) o;
        return idReminder == reminders.idReminder && idUser == reminders.idUser && idRental == reminders.idRental && Objects.equals(reminderDate, reminders.reminderDate) && Objects.equals(message, reminders.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReminder, idUser, idRental, reminderDate, message);
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
