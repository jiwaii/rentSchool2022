package be.jyl.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Permissions", schema = "rentSchool2022", catalog = "")
public class PermissionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_permission", nullable = false)
    private int idPermission;
    @Basic
    @Column(name = "permissionName", nullable = false, length = 100)
    private String permissionName;
    @Basic
    @Column(name = "summary", nullable = true, length = 250)
    private String summary;
    @OneToMany(mappedBy = "permissionsByIdPermission")
    private Collection<RolesPermissionsEntity> rolesPermissionsByIdPermission;

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionsEntity that = (PermissionsEntity) o;
        return idPermission == that.idPermission && Objects.equals(permissionName, that.permissionName) && Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermission, permissionName, summary);
    }

    public Collection<RolesPermissionsEntity> getRolesPermissionsByIdPermission() {
        return rolesPermissionsByIdPermission;
    }

    public void setRolesPermissionsByIdPermission(Collection<RolesPermissionsEntity> rolesPermissionsByIdPermission) {
        this.rolesPermissionsByIdPermission = rolesPermissionsByIdPermission;
    }
}
