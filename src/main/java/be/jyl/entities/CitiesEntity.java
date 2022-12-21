package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Cities", schema = "rentSchool2022", catalog = "")
public class CitiesEntity {
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
    @JoinColumn(name = "id_country", referencedColumnName = "id_country", nullable = false)
    private CountriesEntity countriesByIdCountry;
    @OneToMany(mappedBy = "citiesByIdCity")
    private Collection<UsersEntity> usersByIdCity;

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
        CitiesEntity that = (CitiesEntity) o;
        return idCity == that.idCity && postalCode == that.postalCode && idCountry == that.idCountry && Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCity, cityName, postalCode, idCountry);
    }

    public CountriesEntity getCountriesByIdCountry() {
        return countriesByIdCountry;
    }

    public void setCountriesByIdCountry(CountriesEntity countriesByIdCountry) {
        this.countriesByIdCountry = countriesByIdCountry;
    }

    public Collection<UsersEntity> getUsersByIdCity() {
        return usersByIdCity;
    }

    public void setUsersByIdCity(Collection<UsersEntity> usersByIdCity) {
        this.usersByIdCity = usersByIdCity;
    }
}
