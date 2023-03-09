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

@Named
@ViewScoped
public class ArticleBean implements Serializable {
    private Logger log = Logger.getLogger(ArticleBean.class);
    private ArticlesService articlesService;
    private CategoriesService categoriesService;
    private List<Articles> articles;
    private Articles selectedArticle;
    private List<Categories> categoriesList;
    private int idCategory;

    @PostConstruct
    public void init(){
        articlesService = new ArticlesService();
        articles = articlesService.getAllArticles();
        //categoriesList = categoriesService.getAllCategories();
    }

    public void save(){
        log.log(Level.INFO,"Save: "+ selectedArticle.getIdArticle());
        if (selectedArticle.getIdArticle()==0) {
            log.log(Level.INFO,"categorie: "+ selectedArticle.getCategoryByIdCategory().getIdCategory());
            articlesService.addArticle(selectedArticle);
            //Ajout new article service pour recharger completement la datatable
            //car le selectedarticle prend uniquement l'ID a la catégorie et non l'object contenant tout.
            //Il faudrait idéalement récupérer l'objet avec l'id pour vérifier si il existe et garder le nom aussi
            articlesService = new ArticlesService();
            //Langue des facesMessage à rajouter
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("#{bundle['notification.articleAdded']"));
        } else {
            articlesService.updateArticle(selectedArticle);
            //Langue des facesMessage à rajouter
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("#{bundle['notification.articleUpdated']"));
        }
        //la liste article doit être rechargée
        articles = articlesService.getAllArticles();
        log.log(Level.INFO,"categorie: "+ selectedArticle.getArticleName());
        log.log(Level.INFO,"categorie: "+ selectedArticle.getCategoryByIdCategory().getCategoryName());
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
        log.log(Level.INFO,"selectedArticle cat: "+ selectedArticle.getCategoryByIdCategory().getCategoryName());
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }


}
