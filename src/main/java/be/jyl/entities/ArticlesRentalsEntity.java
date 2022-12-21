package be.jyl.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Articles_Rentals", schema = "rentSchool2022", catalog = "")
public class ArticlesRentalsEntity {
    @Basic
    @Column(name = "id_article", nullable = false)
    private int idArticle;
    @Basic
    @Column(name = "id_rental", nullable = false)
    private int idRental;
    @Basic
    @Column(name = "qty", nullable = false)
    private int qty;
    @ManyToOne
    @JoinColumn(name = "id_article", referencedColumnName = "id_article", nullable = false)
    private ArticlesEntity articlesByIdArticle;
    @ManyToOne
    @JoinColumn(name = "id_rental", referencedColumnName = "id_rental", nullable = false)
    private RentalsEntity rentalsByIdRental;

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getIdRental() {
        return idRental;
    }

    public void setIdRental(int idRental) {
        this.idRental = idRental;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticlesRentalsEntity that = (ArticlesRentalsEntity) o;
        return idArticle == that.idArticle && idRental == that.idRental && qty == that.qty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArticle, idRental, qty);
    }

    public ArticlesEntity getArticlesByIdArticle() {
        return articlesByIdArticle;
    }

    public void setArticlesByIdArticle(ArticlesEntity articlesByIdArticle) {
        this.articlesByIdArticle = articlesByIdArticle;
    }

    public RentalsEntity getRentalsByIdRental() {
        return rentalsByIdRental;
    }

    public void setRentalsByIdRental(RentalsEntity rentalsByIdRental) {
        this.rentalsByIdRental = rentalsByIdRental;
    }
}
