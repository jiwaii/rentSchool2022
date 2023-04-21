package be.jyl.entities;

import javax.persistence.*;
import java.util.Objects;

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
    private Roles idRole;
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

    public Roles getIdRole() {
        return idRole;
    }

    public void setIdRole(Roles idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return  idRole == users.idRole && Objects.equals(login, users.login) && Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, idRole);
    }

//    public Borrowers getBorrowersByIdBorrower() {
//        return borrowersByIdBorrower;
//    }
//
//    public void setBorrowersByIdBorrower(Borrowers borrowersByIdBorrower) {
//        this.borrowersByIdBorrower = borrowersByIdBorrower;
//    }
}
