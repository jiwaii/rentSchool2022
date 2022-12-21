package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Accounts", schema = "rentSchool2022", catalog = "")
public class AccountsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_account", nullable = false)
    private int idAccount;
    @Basic
    @Column(name = "login", nullable = false, length = 100)
    private String login;
    @Basic
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @OneToMany(mappedBy = "accountsByIdAccount")
    private Collection<UsersEntity> usersByIdAccount;

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountsEntity that = (AccountsEntity) o;
        return idAccount == that.idAccount && Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAccount, login, password);
    }

    public Collection<UsersEntity> getUsersByIdAccount() {
        return usersByIdAccount;
    }

    public void setUsersByIdAccount(Collection<UsersEntity> usersByIdAccount) {
        this.usersByIdAccount = usersByIdAccount;
    }
}
