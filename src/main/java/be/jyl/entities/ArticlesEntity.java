package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Articles", schema = "rentSchool2022", catalog = "")
public class ArticlesEntity {
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
    private Object state;
    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id_category", nullable = false)
    private CategoriesEntity categoriesByIdCategory;
    @OneToMany(mappedBy = "articlesByIdArticle")
    private Collection<ArticlesRentalsEntity> articlesRentalsByIdArticle;

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

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticlesEntity that = (ArticlesEntity) o;
        return idArticle == that.idArticle && idCategory == that.idCategory && Objects.equals(articleName, that.articleName) && Objects.equals(refSn, that.refSn) && Objects.equals(barcode, that.barcode) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArticle, idCategory, articleName, refSn, barcode, state);
    }

    public CategoriesEntity getCategoriesByIdCategory() {
        return categoriesByIdCategory;
    }

    public void setCategoriesByIdCategory(CategoriesEntity categoriesByIdCategory) {
        this.categoriesByIdCategory = categoriesByIdCategory;
    }

    public Collection<ArticlesRentalsEntity> getArticlesRentalsByIdArticle() {
        return articlesRentalsByIdArticle;
    }

    public void setArticlesRentalsByIdArticle(Collection<ArticlesRentalsEntity> articlesRentalsByIdArticle) {
        this.articlesRentalsByIdArticle = articlesRentalsByIdArticle;
    }
}
