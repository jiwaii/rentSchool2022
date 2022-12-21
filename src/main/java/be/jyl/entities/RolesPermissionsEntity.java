package be.jyl.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Roles_Permissions", schema = "rentSchool2022", catalog = "")
public class RolesPermissionsEntity {
    @Basic
    @Column(name = "id_permission", nullable = false)
    private int idPermission;
    @Basic
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @ManyToOne
    @JoinColumn(name = "id_permission", referencedColumnName = "id_permission", nullable = false)
    private PermissionsEntity permissionsByIdPermission;
    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private RolesEntity rolesByIdRole;

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesPermissionsEntity that = (RolesPermissionsEntity) o;
        return idPermission == that.idPermission && idRole == that.idRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermission, idRole);
    }

    public PermissionsEntity getPermissionsByIdPermission() {
        return permissionsByIdPermission;
    }

    public void setPermissionsByIdPermission(PermissionsEntity permissionsByIdPermission) {
        this.permissionsByIdPermission = permissionsByIdPermission;
    }

    public RolesEntity getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(RolesEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }
}
