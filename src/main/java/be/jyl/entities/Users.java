package be.jyl.entities;

import javax.persistence.*;
import java.util.Objects;
@NamedQueries( value = {
        @NamedQuery(name = "Users.all",query = "SELECT u FROM Users u order by u.id desc "),
        @NamedQuery(name = "Users.where",query = "select u from Users u " +
                "WHERE (u.firstname like :pName or u.lastname like :pName)"),
        //Requetes pour le role secrétariat
        @NamedQuery(name = "Users.findProfsStudentsOnly",query = "SELECT c FROM Users c " +
                "WHERE c.role.roleName != 'administrateur' OR c.role.roleName = 'emprunteur'   "),
        @NamedQuery(name = "Users.findWhereProfStudentOnly",query = "SELECT c FROM Users c " +
                "Where (c.firstname like :pFirstname or c.lastname like :pLastname) " +
                "AND (c.role.roleName != 'administrateur' OR c.role.roleName = 'emprunteur') "),
        @NamedQuery(name = "User.login", query = "SELECT u FROM Users u " +
                "WHERE u.login = :pLogin and u.password = :pPassword"),
        @NamedQuery(name = "User.login",query = "select  a from Users a where a.login = :pLogin and a.password= :pPassword"),
        @NamedQuery(name = "User.findWhereLogin",query = "select a from Users a where lower(a.login) = :pLogin")

})
@Entity
public class Users extends Borrowers{
//    @Basic
//    @Column(name = "id_borrower", nullable = false)
//    private int idBorrower;
    @Basic
    @Column(name = "login", nullable = false, length = 100)
    private String login;
    @Basic
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_role",referencedColumnName = "id_role")
    private Roles role;
//    @ManyToOne
//    @PrimaryKeyJoinColumn(name = "id_borrower", referencedColumnName = "")
//    private Borrowers borrowersByIdBorrower;

//    public int getIdBorrower() {
//        return idBorrower;
//    }
//
//    public void setIdBorrower(int idBorrower) {
//        this.idBorrower = idBorrower;
//    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return  role == users.role && Objects.equals(login, users.login) && Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, role);
    }

//    public Borrowers getBorrowersByIdBorrower() {
//        return borrowersByIdBorrower;
//    }
//
//    public void setBorrowersByIdBorrower(Borrowers borrowersByIdBorrower) {
//        this.borrowersByIdBorrower = borrowersByIdBorrower;
//    }
}
