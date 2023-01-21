package be.jyl.managedBeans;

import be.jyl.entities.Rentals;
import be.jyl.services.RentalsService;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class RentalBean implements Serializable {
    private Logger log = Logger.getLogger(RentalBean.class);
    private RentalsService rentalsService = new RentalsService();

    public List<Rentals> rentalsList(){
        return rentalsService.rentalsList();
    }
}
