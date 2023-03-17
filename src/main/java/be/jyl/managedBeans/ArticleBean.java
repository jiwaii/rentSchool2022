package be.jyl.managedBeans;


import be.jyl.entities.Articles;
import be.jyl.entities.Categories;
import be.jyl.enums.State;
import be.jyl.services.ArticlesService;
import be.jyl.services.CategoriesService;
import be.jyl.tools.NotificationManager;
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
    private CategoriesService categoriesService;
    private List<Articles> articles;
    private Articles selectedArticle;
    private List<Categories> categoriesList;
    private List<Articles> filteredArticles;

    @PostConstruct
    public void init() {
        articlesService = new ArticlesService();
        categoriesService = new CategoriesService();
        articles = articlesService.getAllArticles();
    }

    public void save() {
        if (selectedArticle.getIdArticle() == 0) {
            articlesService.addArticle(selectedArticle);
            NotificationManager.addInfoMessage("notification.articleAdded");
        } else {
            articlesService.updateArticle(selectedArticle);
            NotificationManager.addInfoMessage("notification.articleUpdated");
        }
        //la liste article doit être rechargée
        articles = articlesService.getAllArticles();
        PrimeFaces.current().executeScript("PF('manageArticleDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-articles");
    }

    public void delete() {
        if (articlesService.isArticleUsed(selectedArticle)) {
            NotificationManager.addErrorMessage("notification.articleDeleteImpossible");
        } else {
            articlesService.deleteArticle(selectedArticle);
            articles.remove(selectedArticle);
            NotificationManager.addErrorMessage("notification.articleDeleteSucces");
        }
    }

    public void clearSelection() {
        selectedArticle = new Articles();
    }

    public Articles getSelectedArticle() {
        return selectedArticle;
    }

    public void setSelectedArticle(Articles selectedArticle) {
        this.selectedArticle = selectedArticle;
        categoriesList = categoriesService.getAllCategories();
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void openNew() {
        this.selectedArticle = new Articles();
        //setting the category to don't return null to the view wich cause a problem
        this.selectedArticle.setCategoryByIdCategory(new Categories());
        categoriesList = categoriesService.getAllCategories();
        log.log(Level.INFO, "catlist: " + categoriesList);
    }

    public boolean isCurrentlyRented(){
        return articlesService.isCurrentlyRented(selectedArticle);
    }

    public boolean isArticleUsed(Articles article) {
        return articlesService.isArticleUsed(article);
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


    public List<Articles> getFilteredArticles() {
        return filteredArticles;
    }

    public void setFilteredArticles(List<Articles> filteredArticles) {
        this.filteredArticles = filteredArticles;
    }
}
