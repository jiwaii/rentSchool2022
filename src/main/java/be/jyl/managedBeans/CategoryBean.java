/**
 * It's a Java Bean class that manages the categories
 */
package be.jyl.managedBeans;


import be.jyl.entities.Articles;
import be.jyl.services.CategoriesService;
import be.jyl.entities.Categories;
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
import java.util.ResourceBundle;

@Named
@ViewScoped
/**
 * A Java Bean class.
 */
public class CategoryBean implements Serializable {
    private Logger log = Logger.getLogger(CategoryBean.class);
    private CategoriesService categoriesService;
    private List<Categories> categories;
    private Categories selectedCategory;

    @PostConstruct
    public void init() {
        categoriesService = new CategoriesService();
        categories = categoriesService.getAllCategories();

    }

    // Saving the selected category.
    public void save(){
        log.log(Level.INFO,"Save: "+ selectedCategory.getIdCategory());
        if (selectedCategory.getIdCategory()==0) {
            categoriesService.addCategory(selectedCategory);
            NotificationManager.addInfoMessage("notification.categoryAdded");
        } else {
            categoriesService.updateCategory(selectedCategory);
            //Langue des facesMessage à rajouter
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            NotificationManager.addInfoMessage("notification.categoryUpdated");
        }
        categories = categoriesService.getAllCategories();
        PrimeFaces.current().executeScript("PF('manageCategoryDialog').hide() ajax='false'");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }
    public void delete() {
        if (categoriesService.isCategoryUsed(selectedCategory)) {
            NotificationManager.addErrorMessage("notification.categoryDeleteImpossible");
        } else {
            categoriesService.deleteCategory(selectedCategory);
            categories.remove(selectedCategory);
            NotificationManager.addErrorMessage("notification.categoryDeleteSucces");
        }
    }

    public boolean isCategoryUsed(Categories category) {
        return categoriesService.isCategoryUsed(category);
    }

    public void clearSelection() {
        selectedCategory = new Categories();
    }

    public Categories getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Categories selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void openNew() {
        this.selectedCategory = new Categories();
    }


}
