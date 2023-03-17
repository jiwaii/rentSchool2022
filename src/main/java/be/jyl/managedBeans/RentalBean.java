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
import com.mysql.cj.xdevapi.CreateIndexParams;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

@Named
@ViewScoped
public class RentalBean implements Serializable {
    private Logger log = Logger.getLogger(RentalBean.class);
    private RentalsService rentalsService = new RentalsService();
    private UserService userService = new UserService();
    private ArticlesService articlesService = new ArticlesService();
    private List<Rentals> rentalsList;
    private Rentals rentalSelected;
    private String rentalSearchText;
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
        return rentalsService.currentRentalsList();
    }

    /**
     * Met à jour la liste des utilisateur dans la page "Faire une location"
     * avec une recherche
     */
    public void usersListByName(){
        log.log(Level.INFO,"userListByName");
        usersList = userService.listUserByName(userSearchText,userSession);
    }
    public void articleListByNameRefBarcode(){
        log.log(Level.INFO, "articleListByNameRefBarcode");
        articlesList = articlesService.articlesAvailableListBySearch(articlesSearchText);
    }
    /**
     * Met à jours l'utlisateur sélectionné dans la bean
     * @param selectEvent
     */
    public void dtUserSelection(SelectEvent selectEvent){
        userSelected = (Users)selectEvent.getObject();
        log.log(Level.INFO, "dtUserSelection() = "+userSelected.getLastname()+" "+userSelected.getFirstname());

    }
    public void rentalListBySearch(){
        log.log(Level.INFO, "search is = "+rentalSearchText);
        rentalsList = rentalsService.rentalsListBySearch(rentalSearchText);
        log.log(Level.INFO, "nobre de ligne: "+ rentalsList.size());
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

    /**
     * @// TODO: 11-03-23 message d'erreur à afficher (message primefaces) 
     * @return
     * @throws ParseException
     */
    public String submitNewRental() throws ParseException {
        log.log(Level.INFO,"createRental()");
        if (userSession != null){
            if (userSelected != null && articleSelected != null && endDateSelected != null){

                // Variable imperatives pour créer la location :
                java.sql.Date dateNow ;
                java.sql.Date dateEnd ;

                //affectation des variables date :
                dateNow = new java.sql.Date(new java.util.Date().getTime());
                DateConverter dateConverter = new DateConverter(); //classe créé par moi pour la conversion des dates
                dateEnd = dateConverter.getSqlDateFromUtilDate(endDateSelected);
                log.log(Level.INFO,"dateEnd SQL : "+dateEnd);
                //affectation Recuperation de l'utilisateur de la session :


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

                //pas besoin de changer l'état de l'article
                //articleSelected.setState(State.rental); // mettre à jour l'article en status loué

                ArticlesRentals articlesRentals = new ArticlesRentals();
                articlesRentals.setQty(1);
                articlesRentals.setArticlesByIdArticle(articleSelected);
                articlesRentals.setRentalsByIdRental(newRental);
                articlesRentals.setDateReturned(null);
                Collection<ArticlesRentals> articlesRentalsCollection = new ArrayList<ArticlesRentals>();
                articlesRentalsCollection.add(articlesRentals);

                newRental.setRentalsArticlesByIdRental(articlesRentalsCollection);

                rentalsService.persistNewRental(newRental);
                //mise à jour de la liste location et article disponible pour l'utilisateur :
                rentalsList = rentalsService.currentRentalsList();
                articlesList = articlesService.articlesAvailableList();
                endDateSelected = null;
                return "index.xhtml";
            } else {
                //FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"ATTENTION", "PrimeFaces rock"));
                return "rentalCreation.xhtml";
            }
        }
        else {
            return "login.xhtml";
        }

    }
    public String goIndex(){
        rentalsList = rentalsService.currentRentalsList();
        for (Rentals r:rentalsList
             ) {
           log.log(Level.INFO,r.getUserRent().getFirstname()+
                   "  "+r.getUserRent().getLastname());
        }
        return "index.xhtml";
    }
    public String endRental(){
        //rentalsService.removeRental(rentalSelected);
        rentalsService.endRental(rentalSelected);
        rentalsList = rentalsService.currentRentalsList();
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

    public String getRentalSearchText() {
        return rentalSearchText;
    }

    public void setRentalSearchText(String rentalSearchText) {
        this.rentalSearchText = rentalSearchText;
    }
}
