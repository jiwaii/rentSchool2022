package be.jyl.validators;

import be.jyl.entities.Articles;
import be.jyl.managedBeans.CategoryBean;
import be.jyl.services.ArticlesService;
import be.jyl.tools.NotificationManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.List;

@FacesValidator(value="articleRefSnExistValidator")
public class ArticleRefSnExistValidator implements Validator {
    private Logger log = Logger.getLogger(ArticleRefSnExistValidator.class);

    private static final String REFSN_EXIST = "Cette reference existe déjà.";

    private ArticlesService articlesService;

    public ArticleRefSnExistValidator(){
        this.articlesService = new ArticlesService();
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Boolean selectedArticleHaveSameId = null;
        String refSn = (String) value;
        Long selectIdArticle = (Long) component.getAttributes().get("idArticle");
        List<Articles> articles;
        articles = articlesService.findByRefSn(refSn);

        for(Articles article : articles) {
            if(article.getIdArticle()==selectIdArticle){
                selectedArticleHaveSameId = true;
            }
            else selectedArticleHaveSameId = false;
        }
        if(!articles.isEmpty() && !selectedArticleHaveSameId){
            log.log(Level.INFO,"IN");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", REFSN_EXIST));
        }

    }
}
