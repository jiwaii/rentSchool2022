package be.jyl.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class RolesPermissions {
    @Column(name = "id_permission", nullable = false)
    private int idPermission;
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_Roles_Permissions", nullable = false)
    private int idRolesPermissions;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_permission", referencedColumnName = "id_permission")
    private Permissions permissionsByIdPermission;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_role", referencedColumnName = "id_role")
    private Roles rolesByIdRole;

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

    public int getIdRolesPermissions() {
        return idRolesPermissions;
    }

    public void setIdRolesPermissions(int idRolesPermissions) {
        this.idRolesPermissions = idRolesPermissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesPermissions that = (RolesPermissions) o;
        return idPermission == that.idPermission && idRole == that.idRole && idRolesPermissions == that.idRolesPermissions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermission, idRole, idRolesPermissions);
    }

    public Permissions getPermissionsByIdPermission() {
        return permissionsByIdPermission;
    }

    public void setPermissionsByIdPermission(Permissions permissionsByIdPermission) {
        this.permissionsByIdPermission = permissionsByIdPermission;
    }

    public Roles getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(Roles rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }
}
