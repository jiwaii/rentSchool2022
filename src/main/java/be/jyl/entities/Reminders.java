package be.jyl.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "Reminders.findAll",query = "select r From Reminders r order by r.idReminder DESC")
})

public class Reminders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_reminder", nullable = false)
    private int idReminder;
    @Basic
    @Column(name = "reminderDate", nullable = false)
    private Date reminderDate;
    @Basic
    @Column(name = "message", nullable = false, length = 250)
    private String message;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users usersByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_rental")
    private Rentals rentalsByIdRental;

    public int getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(int idReminder) {
        this.idReminder = idReminder;
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
        return idReminder == reminders.idReminder && usersByIdUser.getIdUser() == usersByIdUser.getIdUser() && rentalsByIdRental.getIdRental() == rentalsByIdRental.getIdRental() && Objects.equals(reminderDate, reminders.reminderDate) && Objects.equals(message, reminders.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReminder, usersByIdUser, rentalsByIdRental, reminderDate, message);
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
