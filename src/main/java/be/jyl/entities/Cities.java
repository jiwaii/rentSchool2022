package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@NamedQueries( value = {
        @NamedQuery(name = "Cities.findAll",query = "SELECT c FROM Cities c order by c.postalCode asc "),
        @NamedQuery(name = "Cities.finWhere",query = "select c from Cities c " +
                "where c.cityName like :pCityName or cast(c.postalCode as string) = :pCityName ")
    }
)
@Entity
public class Cities {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_city", nullable = false)
    private int idCity;
    @Basic
    @Column(name = "cityName", nullable = false, length = 100)
    private String cityName;
    @Basic
    @Column(name = "postalCode", nullable = false)
    private int postalCode;
    @Basic
    @Column(name = "id_country", nullable = false)
    private int idCountry;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_country", referencedColumnName = "id_country")
    private Countries countriesByIdCountry;
    @OneToMany(mappedBy = "citiesByIdCity")
    private Collection<Users> usersByIdCity;

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cities cities = (Cities) o;
        return idCity == cities.idCity && postalCode == cities.postalCode && idCountry == cities.idCountry && Objects.equals(cityName, cities.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCity, cityName, postalCode, idCountry);
    }

    public Countries getCountriesByIdCountry() {
        return countriesByIdCountry;
    }

    public void setCountriesByIdCountry(Countries countriesByIdCountry) {
        this.countriesByIdCountry = countriesByIdCountry;
    }

    public Collection<Users> getUsersByIdCity() {
        return usersByIdCity;
    }

    public void setUsersByIdCity(Collection<Users> usersByIdCity) {
        this.usersByIdCity = usersByIdCity;
    }
}
