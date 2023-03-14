package be.jyl.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
@NamedQueries(value = {
        @NamedQuery(name = "Article.isCurrentlyRented",
                query = "SELECT CASE WHEN (COUNT(ar) >0) THEN true ELSE false end from ArticlesRentals ar "
                        + "where ar.articlesByIdArticle = :article "
                        + "AND ar.dateReturned IS NULL")
})
@Entity
@Table(name = "articles_rentals", schema = "rentschool2022")
public class ArticlesRentals {

    @Basic
    @Column(name = "qty", nullable = false)
    private int qty;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_Articles_Rentals", nullable = false)
    private int idArticlesRentals;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_article")
    private Articles articlesByIdArticle;
    @ManyToOne
    @JoinColumn(name = "id_rental")
    private Rentals rentalsByIdRental;

    @Basic
    @Column(name = "dateReturned", nullable = true)
    private Date dateReturned;


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

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }
}
