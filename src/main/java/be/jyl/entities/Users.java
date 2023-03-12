package be.jyl.entities;

import be.jyl.enums.ResponsibleType;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@NamedQueries( value = {
        @NamedQuery(name = "Users.findAll",query = "SELECT u FROM Users u order by u.idUser desc "),
        @NamedQuery(name = "Users.findWhere",query = "select u from Users u " +
                "WHERE (u.firstname like :pFirstname or u.lastname like :pLastname)"),
        //Requetes pour le role secrétariat
        @NamedQuery(name = "Users.findProfsStudentsOnly",query = "SELECT c FROM Users c " +
                "WHERE c.rolesByIdRole.roleName != 'administrateur' OR c.rolesByIdRole.roleName = 'emprunteur'   "),
        @NamedQuery(name = "Users.findWhereProfStudentOnly",query = "SELECT c FROM Users c " +
                "Where (c.firstname like :pFirstname or c.lastname like :pLastname) " +
                "AND (c.rolesByIdRole.roleName != 'administrateur' OR c.rolesByIdRole.roleName = 'emprunteur') "),
        @NamedQuery(name = "User.login", query = "SELECT u FROM Users u " +
                "WHERE u.accountsByIdAccount.login = :pLogin and u.accountsByIdAccount.password = :pPassword"),

        @NamedQuery(name = "User.findAllNoAccount", query = "SELECT u FROM Users u WHERE u.accountsByIdAccount = null"),
        @NamedQuery(name = "User.findWhereNoAccount", query = "SELECT u FROM Users u WHERE u.accountsByIdAccount = null " +
                "AND (u.firstname like :pFirstname or u.lastname like :pLastname)")



})
@Entity
@Table(name = "users")
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;
//    @Basic
//    @Column(name = "id_role", nullable = true)
//    private Integer idRole;
    @Basic
    @Column(name = "address", nullable = false, length = 100)
    private String address;
    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Basic
    @Column(name = "responsibleType", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResponsibleType responsibleType;
    @Basic
    @Column(name = "firstname", nullable = false, length = 100)
    private String firstname;
    @Basic
    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;
    @Basic
    @Column(name = "barcode", nullable = true, length = 100)
    private String barcode;
//    @Basic
//    @Column(name = "id_city", nullable = false)
//    private int idCity;
//    @Basic
//    @Column(name = "id_account", nullable = true)
//    private Integer idAccount;
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<Reminders> remindersByIdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role")
    private Roles rolesByIdRole;
    @ManyToOne
    @JoinColumn(name = "id_city")
    private Cities citiesByIdCity;
    @ManyToOne
    @JoinColumn(name = "id_account")
    private Accounts accountsByIdAccount;


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

//    public Integer getIdRole() {
//        return idRole;
//    }
//
//    public void setIdRole(Integer idRole) {
//        this.idRole = idRole;
//    }

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

    public ResponsibleType getResponsibleType() {
        return responsibleType;
    }

    public void setResponsibleType(ResponsibleType responsibleType) {
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

//    public int getIdCity() {
//        return idCity;
//    }
//
//    public void setIdCity(int idCity) {
//        this.idCity = idCity;
//    }

//    public Integer getIdAccount() {
//        return idAccount;
//    }
//
//    public void setIdAccount(Integer idAccount) {
//        this.idAccount = idAccount;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return idUser == users.idUser  && Objects.equals(address, users.address) && Objects.equals(email, users.email) && Objects.equals(responsibleType, users.responsibleType) && Objects.equals(firstname, users.firstname) && Objects.equals(lastname, users.lastname) && Objects.equals(barcode, users.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, address, email, responsibleType, firstname, lastname, barcode);
    }

    public Collection<Reminders> getRemindersByIdUser() {
        return remindersByIdUser;
    }

    public void setRemindersByIdUser(Collection<Reminders> remindersByIdUser) {
        this.remindersByIdUser = remindersByIdUser;
    }

    public Roles getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(Roles rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }

    public Cities getCitiesByIdCity() {
        return citiesByIdCity;
    }

    public void setCitiesByIdCity(Cities citiesByIdCity) {
        this.citiesByIdCity = citiesByIdCity;
    }

    public Accounts getAccountsByIdAccount() {
        return accountsByIdAccount;
    }

    public void setAccountsByIdAccount(Accounts accountsByIdAccount) {
        this.accountsByIdAccount = accountsByIdAccount;
    }

}
