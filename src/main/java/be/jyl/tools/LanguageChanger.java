package be.jyl.tools;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class LanguageChanger implements Serializable {
    private Logger log = Logger.getLogger(LanguageChanger.class);
    private static final long serialVersionUID = 1L;
    private String locale;

    private Map<String,Object> countries;
    private List<String> countriesList;
    @PostConstruct
    public void init() {
        countriesList = new ArrayList<>();
        countriesList.add("fr");
        countriesList.add("en");
    }

    public List<String> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(List<String> countriesList) {
        this.countriesList = countriesList;
    }

    public Map<String, Object> getCountries() {
        return countries;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void changeToEN(){
        log.log(Level.INFO,"changeToEN");
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(Locale.ENGLISH);
    }
    public void changeToFR(){
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(Locale.FRENCH);

    }
    public void changeToES(){
        Locale localeEs = new Locale("es","ES");
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(localeEs);
    }
    //value change event listener
    public void localeChange(ValueChangeEvent e) {
        locale = e.getNewValue().toString();

        if ("fr".equals(locale)) {
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale(Locale.FRENCH);
        }
        if ("en".equals(locale)) {
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale(Locale.ENGLISH);
        }

//        for (Map.Entry<String, Object> entry : countries.entrySet()) {
//
//            if(entry.getValue().toString().equals(locale)) {
//                FacesContext.getCurrentInstance()
//                        .getViewRoot().setLocale((Locale)entry.getValue());
//                //locale = newLocaleValue;
//
////                Map<String,Object> selectedEntry = (Map<String, Object>) entry;
////                countries.remove(entry);
////                countries.putAll(selectedEntry);
//            }
//        }
    }
}
