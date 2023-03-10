package be.jyl.managedBeans;

import be.jyl.entities.Articles;
import be.jyl.entities.ArticlesRentals;
import be.jyl.entities.Rentals;
import be.jyl.entities.Users;
import be.jyl.enums.State;
import be.jyl.services.ArticlesService;
import be.jyl.services.RentalsService;
import be.jyl.services.UserService;
import be.jyl.tools.DateConverter;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
    private Rentals rentalSelected;
    private Users userSelected ;
    private Users userSession;
    private List<Users> usersList;
    private List<Articles> articlesMultipleSelected;
    private Articles articleSelected;
    private Date endDateSelected;
    private List<Articles> articlesList;
    private String userSearchText;
    private String articlesSearchText;
    private Date minDate;

    /**
     * ------------------------
     *      Functions
     * ------------------------
     **/
    @PostConstruct
    public void init(){
        rentalsList = gRentalsList();
        FacesContext context = FacesContext.getCurrentInstance();
        this.userSession = (Users) context.getExternalContext().getSessionMap().get("userSession") ;
        log.log(Level.INFO,userSession.getRolesByIdRole().getRoleName().toString());
        //List d'utilisateur dépendant du role
        usersList = userService.listUsers(userSession);
        articlesList = articlesService.articlesAvailableList();
        minDate = new Date();
    }
    public List<Rentals> gRentalsList(){
        return rentalsService.rentalsList();
    }

    /**
     * Met à jour la liste des utilisateur dans la page "Faire une location"
     * avec une recherche
     */
    public void usersListByName(){
        log.log(Level.INFO,"userListByName");
        usersList = userService.listUserByName(userSearchText,userSession);
//        if (userSession.getRolesByIdRole().getRoleName().toString().equals("administrateur")){
//            usersList = userService.listUserByNamForAdmin(userSearchText) ;
//        }
//        else {
//            usersList = userService.listUserByName(userSearchText);
//        }
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
    public void dateSelectionKeyUpEvent(){
        log.log(Level.INFO,"Event Listener : "+ endDateSelected );
//        if (endDateSelected == null) FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage("Veillez inserez une date cohérente "));
    }
    public void dtRentalSelection(SelectEvent selectEvent){
//        rentalsSelected = (Rentals) selectEvent.getObject();
//        log.log(Level.INFO,"dtRentalSelection'() id = "+ rentalsSelected.getIdRental());
    }
    public void dtRentalDetails(Rentals rental){
        rentalSelected = rental;
    }

    public String createRental() throws ParseException {
        log.log(Level.INFO,"createRental()");
        if (userSession != null){
            if (userSelected != null && articleSelected != null){

            }
            // Variable imperatives pour créer la location :
            java.sql.Date dateNow ;
            java.sql.Date dateEnd ;

            //affectation des variables date :
            dateNow = new java.sql.Date(new java.util.Date().getTime());
            DateConverter dateConverter = new DateConverter(); //classe créé par moi pour la conversion des dates
            dateEnd = dateConverter.getSqlDateFromUtilDate(endDateSelected);
            log.log(Level.INFO,"dateEnd SQL : "+dateEnd);
            //affectation Recuperation de l'utilisateur de la session :
//        FacesContext context = FacesContext.getCurrentInstance();
//        userSession = (Users) context.getExternalContext().getSessionMap().get("user") ;

            /** -------------------------------------------------------------
             * @autor jiwaii
             *  Configuration des mes entités pour la creation de ma location :
            ---------------------------------------------------------------*/
            log.log(Level.INFO,"Start config entities");
            Rentals newRental = new Rentals();
            newRental.setUser(userSession);
            newRental.setUserRent(userSelected);
            newRental.setDateBegin(dateNow);
            newRental.setDateEnd(dateEnd);

            articleSelected.setState(State.rental); // mettre à jour l'article en status loué

            ArticlesRentals articlesRentals = new ArticlesRentals();
            articlesRentals.setQty(1);
            articlesRentals.setArticlesByIdArticle(articleSelected);
            articlesRentals.setRentalsByIdRental(newRental);
            Collection<ArticlesRentals> articlesRentalsCollection = new ArrayList<ArticlesRentals>();
            articlesRentalsCollection.add(articlesRentals);

            newRental.setRentalsArticlesByIdRental(articlesRentalsCollection);

            rentalsService.persistNewRental(newRental,articleSelected);
            //mise à jour de la liste location et article disponible pour la vue :
            rentalsList = rentalsService.rentalsList();
            articlesList = articlesService.articlesAvailableList();

            return "index.xhtml";
        }
        else {
            return "login.xhtml";
        }

    }
    public String deleteRental(){
        rentalsService.removeRental(rentalSelected);
        rentalsList = rentalsService.rentalsList();
        articlesList = articlesService.articlesAvailableList();
        return "index.xhtml";
    }
    public String extendDateRental(){
        //rentalSelected

        return "";
    }
    public String reportRentalIssue(){

        return "";
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

    public Rentals getRentalSelected() {
        return rentalSelected;
    }

    public void setRentalSelected(Rentals rentalSelected) {
        this.rentalSelected = rentalSelected;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
}
