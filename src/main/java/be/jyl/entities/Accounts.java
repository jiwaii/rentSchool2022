package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@NamedQueries(value = {
    @NamedQuery(name = "Account.findAll",query = "select a From Accounts a order by a.idAccount desc "),
    @NamedQuery(name = "Account.login",query = "select  a from Accounts a where a.login = :pLogin and a.password= :pPassword")
})
@Entity
public class Accounts {
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
    private Collection<Users> usersByIdAccount;

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
        Accounts accounts = (Accounts) o;
        return idAccount == accounts.idAccount && Objects.equals(login, accounts.login) && Objects.equals(password, accounts.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAccount, login, password);
    }

    public Collection<Users> getUsersByIdAccount() {
        return usersByIdAccount;
    }

    public void setUsersByIdAccount(Collection<Users> usersByIdAccount) {
        this.usersByIdAccount = usersByIdAccount;
    }
}
