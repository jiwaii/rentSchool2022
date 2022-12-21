package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Users", schema = "rentSchool2022")
@NamedQueries({
        @NamedQuery(name = "Users.findAll", query = "SELECT u FROM UsersEntity u ORDER BY u.idUser desc "),
        @NamedQuery(name = "Users.getUser", query = "SELECT u FROM UsersEntity u WHERE :user = u")

})
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @Column(name = "id_role", nullable = true)
    private int idRole;
    @Basic
    @Column(name = "address", nullable = false, length = 100)
    private String address;
    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Basic
    @Column(name = "responsibleType", nullable = false)
    private Object responsibleType;
    @Basic
    @Column(name = "firstname", nullable = false, length = 100)
    private String firstname;
    @Basic
    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;
    @Basic
    @Column(name = "barcode", nullable = true, length = 100)
    private String barcode;
    @Basic
    @Column(name = "id_city", nullable = false)
    private int idCity;
    @Basic
    @Column(name = "id_account", nullable = true)
    private int idAccount;
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<RemindersEntity> remindersByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id_role")
    private RolesEntity rolesByIdRole;
    @ManyToOne
    @JoinColumn(name = "id_city", referencedColumnName = "id_city", nullable = false)
    private CitiesEntity citiesByIdCity;
    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id_account")
    private AccountsEntity accountsByIdAccount;
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<UsersRentalsEntity> usersRentalsByIdUser;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getResponsibleType() {
        return responsibleType;
    }

    public void setResponsibleType(Object responsibleType) {
        this.responsibleType = responsibleType;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return idUser == that.idUser && idCity == that.idCity && Objects.equals(idRole, that.idRole) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(responsibleType, that.responsibleType) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(barcode, that.barcode) && Objects.equals(idAccount, that.idAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idRole, address, email, responsibleType, firstname, lastname, barcode, idCity, idAccount);
    }

    public Collection<RemindersEntity> getRemindersByIdUser() {
        return remindersByIdUser;
    }

    public void setRemindersByIdUser(Collection<RemindersEntity> remindersByIdUser) {
        this.remindersByIdUser = remindersByIdUser;
    }

    public RolesEntity getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(RolesEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }

    public CitiesEntity getCitiesByIdCity() {
        return citiesByIdCity;
    }

    public void setCitiesByIdCity(CitiesEntity citiesByIdCity) {
        this.citiesByIdCity = citiesByIdCity;
    }

    public AccountsEntity getAccountsByIdAccount() {
        return accountsByIdAccount;
    }

    public void setAccountsByIdAccount(AccountsEntity accountsByIdAccount) {
        this.accountsByIdAccount = accountsByIdAccount;
    }

    public Collection<UsersRentalsEntity> getUsersRentalsByIdUser() {
        return usersRentalsByIdUser;
    }

    public void setUsersRentalsByIdUser(Collection<UsersRentalsEntity> usersRentalsByIdUser) {
        this.usersRentalsByIdUser = usersRentalsByIdUser;
    }
}
