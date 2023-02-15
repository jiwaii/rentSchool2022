package be.jyl.managedBeans;

import be.jyl.entities.Articles;
import be.jyl.entities.Rentals;
import be.jyl.entities.Users;
import be.jyl.services.RentalsService;
import be.jyl.services.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.xml.bind.Unmarshaller;
import java.io.Serializable;
import java.util.EventListener;
import java.util.List;

@Named
@SessionScoped
public class RentalBean implements Serializable {
    private Logger log = Logger.getLogger(RentalBean.class);
    private RentalsService rentalsService = new RentalsService();
    private UserService userService = new UserService();
    private List<Rentals> rentalsList;
    private Users userSelected ;
    private List<Users> usersList;
    private List<Articles> ArticlesMultipleSelected;
    private List<Articles> articlesList;

    /**
     * ------------------------
     *      Functions
     * ------------------------
     */
    @PostConstruct
    public void init(){
        usersList = userService.listUsers();
    }
    public List<Rentals> rentalsList(){
        return rentalsService.rentalsList();
    }
    public List<Users> usersListByName(){
        return userService.listUsers();
    }
    public void dtUserSelection(SelectEvent selectEvent){
        Users selectedUsersEvent = (Users)selectEvent.getObject();
        log.log(Level.INFO, "dtUserSelection() = "+selectedUsersEvent.getFirstname() );
    }




    /**
     * -------------------------
     * Getters & Setters
     * -------------------------
     */
    public List<Rentals> getRentalsList() {
        return rentalsList;
    }

    public void setRentalsList(List<Rentals> rentalsList) {
        this.rentalsList = rentalsList;
    }


    public Users getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(Users userSelected) {
        this.userSelected = userSelected;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public List<Articles> getArticlesMultipleSelected() {
        return ArticlesMultipleSelected;
    }

    public void setArticlesMultipleSelected(List<Articles> articlesMultipleSelected) {
        ArticlesMultipleSelected = articlesMultipleSelected;
    }

    public List<Articles> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<Articles> articlesList) {
        this.articlesList = articlesList;
    }
}
