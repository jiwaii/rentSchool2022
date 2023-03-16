package be.jyl.entities;

import be.jyl.enums.State;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@NamedQueries(value = {
        @NamedQuery(name = "articles.findAll",query = "SELECT a FROM Articles a "),
        @NamedQuery(name = "articles.available", query = "SELECT a FROM Articles a " +
                "WHERE a.state = be.jyl.enums.State.available "),
        @NamedQuery(name = "articles.findWhere",query = "SELECT a FROM Articles a " +
                "WHERE (a.articleName like :pArticleSearch OR a.barcode like :pArticleSearch OR a.refSn like :pArticleSearch) " +
                "AND a.state = be.jyl.enums.State.available "),
        //Retourne les articles qui ne sont pas en location basé sur la date de dateRetour de articleRental
        @NamedQuery(name = "articles.availableBasedOnDateReturn", query = "SELECT a FROM Articles " +
                "a WHERE a.idArticle NOT IN (SELECT ar.articlesByIdArticle.idArticle FROM ArticlesRentals ar WHERE ar.dateReturned IS NULL) "+
                "AND a.state = be.jyl.enums.State.available"),
        @NamedQuery(name="articles.findByRefSn", query="SELECT a FROM Articles a WHERE a.refSn = :refSn"),
        @NamedQuery(name="articles.findByBarcode", query="SELECT a FROM Articles a WHERE a.barcode = :barcode")
})
@Entity
public class Articles {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_article", nullable = false)
    private int idArticle;
    @Basic
    @Column(name = "articleName", nullable = false, length = 100)
    private String articleName;
    @Basic
    @Column(name = "ref_sn", nullable = true, length = 100)
    private String refSn;
    @Basic
    @Column(name = "barcode", nullable = true, length = 100)
    private String barcode;
    @Basic
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;
    @ManyToOne
    @JoinColumn(name = "id_category" , nullable = false)
    private Categories categoryByIdCategory;
    @OneToMany(mappedBy = "articlesByIdArticle")
    private Collection<ArticlesRentals> articlesRentalsByIdArticle;

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getRefSn() {
        return refSn;
    }

    public void setRefSn(String refSn) {
        this.refSn = refSn;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articles articles = (Articles) o;
        return idArticle == articles.idArticle && categoryByIdCategory.getIdCategory() == articles.categoryByIdCategory.getIdCategory() && Objects.equals(articleName, articles.articleName) && Objects.equals(refSn, articles.refSn) && Objects.equals(barcode, articles.barcode) && Objects.equals(state, articles.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArticle, categoryByIdCategory ,articleName, refSn, barcode, state);
    }

    public Categories getCategoryByIdCategory() {
        return categoryByIdCategory;
    }

    public void setCategoryByIdCategory(Categories categoriesByIdCategory) {
        this.categoryByIdCategory = categoriesByIdCategory;
    }

    public Collection<ArticlesRentals> getArticlesRentalsByIdArticle() {
        return articlesRentalsByIdArticle;
    }

    public void setArticlesRentalsByIdArticle(Collection<ArticlesRentals> articlesRentalsByIdArticle) {
        this.articlesRentalsByIdArticle = articlesRentalsByIdArticle;
    }
}
