package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Countries {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_country", nullable = false)
    private int idCountry;
    @Basic
    @Column(name = "countryName", nullable = false, length = 100)
    private String countryName;
    @Basic
    @Column(name = "codeAlpha2", nullable = false, length = 2)
    private String codeAlpha2;
    @OneToMany(mappedBy = "countriesByIdCountry")
    private Collection<Cities> citiesByIdCountry;

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCodeAlpha2() {
        return codeAlpha2;
    }

    public void setCodeAlpha2(String codeAlpha2) {
        this.codeAlpha2 = codeAlpha2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Countries countries = (Countries) o;
        return idCountry == countries.idCountry && Objects.equals(countryName, countries.countryName) && Objects.equals(codeAlpha2, countries.codeAlpha2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCountry, countryName, codeAlpha2);
    }

    public Collection<Cities> getCitiesByIdCountry() {
        return citiesByIdCountry;
    }

    public void setCitiesByIdCountry(Collection<Cities> citiesByIdCountry) {
        this.citiesByIdCountry = citiesByIdCountry;
    }
}
