package be.jyl.managedBeans;

import be.jyl.entities.Categories;
import be.jyl.entities.Reminders;
import be.jyl.entities.Rentals;
import be.jyl.services.CategoriesService;
import be.jyl.services.RentalsService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;

@Named
@ViewScoped
public class RemindersBean implements Serializable {
    private Logger log = Logger.getLogger(Reminders.class);
    private RentalsService rentalsService;
    private List<Rentals> lateRentalsList;

    @PostConstruct
    public void init() {
        rentalsService = new RentalsService();
        lateRentalsList = rentalsService.lateRentalsList();
    }

    public long getNumberOfDayLate(java.sql.Date endDate) {
        LocalDate todayDate = LocalDate.now();
        return ChronoUnit.DAYS.between( endDate.toLocalDate(), todayDate);
    }

    public List<Rentals> getLateRentalsList() {
        return lateRentalsList;
    }

    public void setLateRentalsList(List<Rentals> lateRentalsList) {
        this.lateRentalsList = lateRentalsList;
    }
}
