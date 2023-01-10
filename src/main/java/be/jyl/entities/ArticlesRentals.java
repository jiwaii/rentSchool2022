package be.jyl.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Articles_Rentals", schema = "rentSchool2022")
public class ArticlesRentals {
    @Basic
    @Column(name = "id_article", nullable = false)
    private int idArticle;
    @Basic
    @Column(name = "id_rental", nullable = false)
    private int idRental;
    @Basic
    @Column(name = "qty", nullable = false)
    private int qty;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_Articles_Rentals", nullable = false)
    private int idArticlesRentals;
    @ManyToOne
    @JoinColumn(name = "id_article", referencedColumnName = "id_article", nullable = false)
    private Articles articlesByIdArticle;
    @ManyToOne
    @JoinColumn(name = "id_rental", referencedColumnName = "id_rental", nullable = false)
    private Rentals rentalsByIdRental;

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

    public int getIdArticlesRentals() {
        return idArticlesRentals;
    }

    public void setIdArticlesRentals(int idArticlesRentals) {
        this.idArticlesRentals = idArticlesRentals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticlesRentals that = (ArticlesRentals) o;
        return idArticle == that.idArticle && idRental == that.idRental && qty == that.qty && idArticlesRentals == that.idArticlesRentals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArticle, idRental, qty, idArticlesRentals);
    }

    public Articles getArticlesByIdArticle() {
        return articlesByIdArticle;
    }

    public void setArticlesByIdArticle(Articles articlesByIdArticle) {
        this.articlesByIdArticle = articlesByIdArticle;
    }

    public Rentals getRentalsByIdRental() {
        return rentalsByIdRental;
    }

    public void setRentalsByIdRental(Rentals rentalsByIdRental) {
        this.rentalsByIdRental = rentalsByIdRental;
    }
}
