package be.jyl.managedBeans;

import be.jyl.entities.Articles;
import be.jyl.entities.Rentals;
import be.jyl.entities.Users;
import be.jyl.services.ArticlesService;
import be.jyl.services.RentalsService;
import be.jyl.services.UserService;
import be.jyl.tools.DateConverter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

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
    private Date endDateSelected;
    private List<Articles> articlesList;
    private String userSearchText;
    private String articlesSearchText;

    /**
     * ------------------------
     *      Functions
     * ------------------------
     **/
    @PostConstruct
    public void init(){
        usersList = userService.listUsers();
        articlesList = articlesService.articlesAvailableList();
    }
    public List<Rentals> rentalsList(){
        return rentalsService.rentalsList();
    }

    /**
     * Met à jour la liste des utilisateur dans la page "Faire une location"
     * avec une recherche
     */
    public void usersListByName(){
        log.log(Level.INFO,"userListByName");
        usersList = userService.listUserByName(userSearchText) ;
    }

    /**
     * Met à jours l'utlisateur sélectionné dans la bean
     * @param selectEvent
     */
    public void dtUserSelection(SelectEvent selectEvent){
        userSelected = (Users)selectEvent.getObject();
        log.log(Level.INFO, "dtUserSelection() = "+userSelected.getLastname()+" "+userSelected.getFirstname());

    }

    /**
     * Met à jour l'article sélèctionné dans la bean
     * @param selectEvent
     */
    public void dtArticleSelection(SelectEvent selectEvent){
        articleSelected = (Articles) selectEvent.getObject();
        log.log(Level.INFO, "dtArticleSelection() = "+articleSelected.getArticleName());
    }
    public void dateSelection(SelectEvent selectEvent){
        log.log(Level.INFO,"dateSelection() = "+selectEvent.getObject().toString());
    }

    public String createRental() throws ParseException {
        log.log(Level.INFO,"createRental()");
        DateConverter dateConverter = new DateConverter();
        dateConverter.getSqlDateFromUtilDate(endDateSelected);
        java.sql.Date dateNow = new java.sql.Date(new java.util.Date().getTime());

        log.log(Level.INFO,"UserID : "+userSelected.getIdUser());
        log.log(Level.INFO,"UserName : "+userSelected.getFirstname()+" "+userSelected.getLastname());



        //Rental
        Rentals newRental = new Rentals();
        newRental.setUser(userSelected);
        FacesContext context = FacesContext.getCurrentInstance();
        Users userSession = (Users) context.getExternalContext().getSessionMap().get("user") ;
        log.log(Level.INFO, "user connected is : "+userSession.getFirstname());
        newRental.setUserRent(userSession);
        log.log(Level.INFO,"userRental is  : "+newRental.getUserRent().getFirstname()+" "
                + newRental.getUserRent().getLastname());
        newRental.setDateBegin(dateNow);
        newRental.setDateEnd(dateConverter.getSqlDateFromUtilDate(endDateSelected));

        rentalsService.persistNewRental(newRental, articleSelected);

        return "index.xhtml";
    }



    /**
     * -------------------------
     * Getters & Setters
     * -------------------------
     **/

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

    public Date getEndDateSelected() {
        return endDateSelected;
    }

    public void setEndDateSelected(Date endDateSelected) {
        this.endDateSelected = endDateSelected;
    }
}
