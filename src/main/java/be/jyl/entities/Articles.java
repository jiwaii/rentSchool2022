package be.jyl.entities;

import be.jyl.enums.State;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Articles {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_article", nullable = false)
    private int idArticle;
    @Basic
    @Column(name = "id_category", nullable = false)
    private int idCategory;
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
    @PrimaryKeyJoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Categories categoriesByIdCategory;
    @OneToMany(mappedBy = "articlesByIdArticle")
    private Collection<ArticlesRentals> articlesRentalsByIdArticle;

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
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
        return idArticle == articles.idArticle && idCategory == articles.idCategory && Objects.equals(articleName, articles.articleName) && Objects.equals(refSn, articles.refSn) && Objects.equals(barcode, articles.barcode) && Objects.equals(state, articles.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArticle, idCategory, articleName, refSn, barcode, state);
    }

    public Categories getCategoriesByIdCategory() {
        return categoriesByIdCategory;
    }

    public void setCategoriesByIdCategory(Categories categoriesByIdCategory) {
        this.categoriesByIdCategory = categoriesByIdCategory;
    }

    public Collection<ArticlesRentals> getArticlesRentalsByIdArticle() {
        return articlesRentalsByIdArticle;
    }

    public void setArticlesRentalsByIdArticle(Collection<ArticlesRentals> articlesRentalsByIdArticle) {
        this.articlesRentalsByIdArticle = articlesRentalsByIdArticle;
    }
}
