package be.jyl.validators;

import be.jyl.entities.Articles;
import be.jyl.services.ArticlesService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "articleBarcodeExistValidator")
public class ArticleBarcodeExistValidator implements Validator {

    private static final String BARCODE_EXIST = "Ce code-barre existe déjà.";
    private ArticlesService articlesService;

    public ArticleBarcodeExistValidator() {
        this.articlesService = new ArticlesService();
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String barcode = (String) value;
        Integer selectIdArticle = (Integer) component.getAttributes().get("idArticle");
        Articles article = articlesService.findByBarcode(barcode);

        if (article != null && selectIdArticle != article.getIdArticle()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", BARCODE_EXIST));
        }
    }
}