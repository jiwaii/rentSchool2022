package be.jyl.validators;

import be.jyl.entities.Articles;
import be.jyl.services.ArticlesService;
import be.jyl.tools.NotificationManager;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="articleExistsInRentalsValidator")
public class ArticleExistInRentalValidator implements Validator {

    private static final String RENTAL_EXIST = "Cette article a déjà été loué.";
    private ArticlesService articlesService;

    public ArticleExistInRentalValidator(){
        this.articlesService = new ArticlesService();
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Articles articleChecked = (Articles) value;

        try{
            if(articlesService.isCurrentlyRented(articleChecked) == true){
                throw new ValidatorException((new FacesMessage(FacesMessage.SEVERITY_ERROR, "RENTAL_EXIST", "coucou")));

            }
        }catch (Exception e){
            NotificationManager.addErrorMessage(e.getMessage());
        }
    }
}