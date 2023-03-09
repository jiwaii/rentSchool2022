package be.jyl.managedBeans;


import be.jyl.entities.Categories;
import be.jyl.enums.State;
import be.jyl.services.ArticlesService;
import be.jyl.entities.Articles;
import be.jyl.services.CategoriesService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

//TODO: search
@Named
@ViewScoped
public class ArticleBean implements Serializable {
    private Logger log = Logger.getLogger(ArticleBean.class);
    private ArticlesService articlesService;
    private List<Articles> articles;
    private Articles selectedArticle;
    private List<Categories> categoriesList;

    @PostConstruct
    public void init(){
        articlesService = new ArticlesService();
        articles = articlesService.getAllArticles();
    }

    public void save(){
        if (selectedArticle.getIdArticle()==0) {
            articlesService.addArticle(selectedArticle);
            //Langue des facesMessage à rajouter
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("#{bundle['notification.articleAdded']"));
        } else {
            articlesService.updateArticle(selectedArticle);
            //Langue des facesMessage à rajouter
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("#{bundle['notification.articleUpdated']"));
        }
        //la liste article doit être rechargée
        articles = articlesService.getAllArticles();
        PrimeFaces.current().executeScript("PF('manageArticleDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-articles");

    }
    public void delete(Articles article) {
        articlesService.deleteArticle(article);
        articles.remove(article);
    }

    public void clearSelection() {
        selectedArticle = new Articles();
    }

    public Articles getSelectedArticle() {
        return selectedArticle;
    }

    public void setSelectedArticle(Articles selectedArticle) {
        this.selectedArticle = selectedArticle;
        categoriesList = articlesService.listCategories();
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void openNew() {
        this.selectedArticle = new Articles();
        //setting the category to don't return null to the view wich cause a problem
        this.selectedArticle.setCategoryByIdCategory(new Categories());
        categoriesList = articlesService.listCategories();
        log.log(Level.INFO,"catlist: "+ categoriesList);
    }

    public State[] getStatesList() {
        return State.values();
    }

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }



}
