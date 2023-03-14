package be.jyl.managedBeans;

import be.jyl.entities.Reminders;
import be.jyl.entities.Rentals;
import be.jyl.entities.Users;
import be.jyl.services.RemindersService;
import be.jyl.services.RentalsService;
import be.jyl.tools.Mailer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.MessagingException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Named
@ViewScoped
public class RemindersBean implements Serializable {
    private Logger log = Logger.getLogger(Reminders.class);
    private RentalsService rentalsService;
    private List<Rentals> lateRentalsList;
    private List<Reminders> remindersList;
    private Rentals selectedRental;
    private String message;
    private RemindersService remindersService;
    private Users userSession;
    private Users user = new Users();

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        this.userSession = new Users();
        this.userSession = (Users) context.getExternalContext().getSessionMap().get("userSession") ;
        rentalsService = new RentalsService();
        lateRentalsList = rentalsService.lateRentalsList();
        remindersService = new RemindersService();

    }

    public long getNumberOfDayLate(java.sql.Date endDate) {
        LocalDate todayDate = LocalDate.now();
        return ChronoUnit.DAYS.between( endDate.toLocalDate(), todayDate);
    }

    public void sendReminderEmail() throws MessagingException {
        // On crée une instance de la classe Mailer
        Mailer mailer = new Mailer();
        // On appelle la méthode sendMailReminder
        log.log(Level.INFO, "Sending reminder Mail for rental: " +selectedRental.getIdRental());
        mailer.sendMailReminder(selectedRental, null);
        message = null;
        saveReminder();
    }

    public void sendCustomReminderEmail() throws MessagingException {
        // On crée une instance de la classe Mailer
        Mailer mailer = new Mailer();
        // On appelle la méthode sendMailReminder
        log.log(Level.INFO, "Sending reminder Mail for rental: " +selectedRental.getIdRental());
        mailer.sendCustomReminder(selectedRental, null, message);
        saveReminder();
    }

    public void saveReminder(){
        Reminders reminders = new Reminders();
        reminders.setRentalsByIdRental(selectedRental);
        reminders.setUsersByIdUser(userSession);
        reminders.setMessage(message);
        remindersService.addReminder(reminders);
    }

    public void openHistory(){
        remindersList = remindersService.getAllReminders();
        log.log(Level.INFO, "reminderList: " +remindersList);
    }

    public List<Rentals> getLateRentalsList() {
        return lateRentalsList;
    }

    public void setLateRentalsList(List<Rentals> lateRentalsList) {
        this.lateRentalsList = lateRentalsList;
    }

    public Rentals getSelectedRental() {
        return selectedRental;
    }

    public void setSelectedRental(Rentals selectedRental) {
        this.selectedRental = selectedRental;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String messageContent) {
        this.message = messageContent;
    }

    public List<Reminders> getRemindersList() {
        return remindersList;
    }

    public void setRemindersList(List<Reminders> remindersList) {
        this.remindersList = remindersList;
    }
}
