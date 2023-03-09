package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@NamedQueries( value = {
        @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
        @NamedQuery(name = "Roles.findWhereRoleNameIs", query = "SELECT r FROM Roles r " +
                "WHERE r.roleName = :pRoleName ")
})
@Entity
public class Roles {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @Basic
    @Column(name = "roleName", nullable = false, length = 100)
    private String roleName;
    @OneToMany(mappedBy = "rolesByIdRole")
    private Collection<RolesPermissions> rolesPermissionsByIdRole;
    @OneToMany(mappedBy = "rolesByIdRole")
    private Collection<Users> usersByIdRole;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return idRole == roles.idRole && Objects.equals(roleName, roles.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, roleName);
    }

    public Collection<RolesPermissions> getRolesPermissionsByIdRole() {
        return rolesPermissionsByIdRole;
    }

    public void setRolesPermissionsByIdRole(Collection<RolesPermissions> rolesPermissionsByIdRole) {
        this.rolesPermissionsByIdRole = rolesPermissionsByIdRole;
    }

    public Collection<Users> getUsersByIdRole() {
        return usersByIdRole;
    }

    public void setUsersByIdRole(Collection<Users> usersByIdRole) {
        this.usersByIdRole = usersByIdRole;
    }
}
