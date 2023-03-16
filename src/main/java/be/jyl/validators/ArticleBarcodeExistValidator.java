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
import java.util.List;

@FacesValidator(value="articleBarcodeExistValidator")
public class ArticleBarcodeExistValidator implements Validator {

    private static final String BARCODE_EXIST = "Ce code-barre existe déjà.";
    private ArticlesService articlesService;

    public ArticleBarcodeExistValidator(){
        this.articlesService = new ArticlesService();
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String barcode = (String) value;
        List<Articles> articles;
        articles = articlesService.findByBarcode(barcode);

        try{
            if(articles.isEmpty()){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, BARCODE_EXIST, null));
            }
        }catch (Exception e){
            NotificationManager.addErrorMessage(e.getMessage());
        }
    }
}