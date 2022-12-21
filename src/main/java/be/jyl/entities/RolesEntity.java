package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Roles", schema = "rentSchool2022", catalog = "")
public class RolesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @Basic
    @Column(name = "roleName", nullable = false, length = 100)
    private String roleName;
    @OneToMany(mappedBy = "rolesByIdRole")
    private Collection<RolesPermissionsEntity> rolesPermissionsByIdRole;
    @OneToMany(mappedBy = "rolesByIdRole")
    private Collection<UsersEntity> usersByIdRole;

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
        RolesEntity that = (RolesEntity) o;
        return idRole == that.idRole && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, roleName);
    }

    public Collection<RolesPermissionsEntity> getRolesPermissionsByIdRole() {
        return rolesPermissionsByIdRole;
    }

    public void setRolesPermissionsByIdRole(Collection<RolesPermissionsEntity> rolesPermissionsByIdRole) {
        this.rolesPermissionsByIdRole = rolesPermissionsByIdRole;
    }

    public Collection<UsersEntity> getUsersByIdRole() {
        return usersByIdRole;
    }

    public void setUsersByIdRole(Collection<UsersEntity> usersByIdRole) {
        this.usersByIdRole = usersByIdRole;
    }
}
