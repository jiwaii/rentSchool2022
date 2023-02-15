package be.jyl.managedBeans;

import be.jyl.entities.Articles;
import be.jyl.entities.Rentals;
import be.jyl.entities.Users;
import be.jyl.services.ArticlesService;
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
    private ArticlesService articlesService = new ArticlesService();
    private List<Rentals> rentalsList;
    private Users userSelected ;
    private List<Users> usersList;
    private List<Articles> articlesMultipleSelected;
    private Articles articleSelected;
    private List<Articles> articlesList;
    private String userSearchText;
    private String articlesSearchText;

    /**
     * ------------------------
     *      Functions
     * ------------------------
     */
    @PostConstruct
    public void init(){
        usersList = userService.listUsers();
        articlesList = articlesService.articlesAvailableList();
    }
    public List<Rentals> rentalsList(){
        return rentalsService.rentalsList();
    }
    public void usersListByName(){
        log.log(Level.INFO,"userListByName");
        usersList = userService.listUserByName(userSearchText) ;
    }
    public void dtUserSelection(SelectEvent selectEvent){
        userSelected = (Users)selectEvent.getObject();
        log.log(Level.INFO, "dtUserSelection() = "+userSelected.getLastname()+" "+userSelected.getFirstname());

    }
    public void dtArticleSelection(SelectEvent selectEvent){
        articleSelected = (Articles) selectEvent.getObject();
        log.log(Level.INFO, "dtArticleSelection() = "+articleSelected.getArticleName());
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
        return articlesMultipleSelected;
    }

    public void setArticlesMultipleSelected(List<Articles> articlesMultipleSelected) {
        articlesMultipleSelected = articlesMultipleSelected;
    }

    public List<Articles> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<Articles> articlesList) {
        this.articlesList = articlesList;
    }

    public String getUserSearchText() {
        return userSearchText;
    }

    public void setUserSearchText(String userSearchText) {
        this.userSearchText = userSearchText;
    }

    public String getArticlesSearchText() {
        return articlesSearchText;
    }

    public void setArticlesSearchText(String articlesSearchText) {
        this.articlesSearchText = articlesSearchText;
    }

    public Articles getArticleSelected() {
        return articleSelected;
    }

    public void setArticleSelected(Articles articleSelected) {
        this.articleSelected = articleSelected;
    }
}
