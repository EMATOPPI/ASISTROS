package com.contaduria.auth.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase para manejar la clave compuesta de la entidad Permiso
 */
public class PermisoId implements Serializable {

    private Long idRoles;
    private Long idMenus;

    // Constructores
    public PermisoId() {}

    public PermisoId(Long idRoles, Long idMenus) {
        this.idRoles = idRoles;
        this.idMenus = idMenus;
    }

    // equals y hashCode son requeridos para claves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermisoId permisoId = (PermisoId) o;
        return Objects.equals(idRoles, permisoId.idRoles) &&
                Objects.equals(idMenus, permisoId.idMenus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoles, idMenus);
    }

    // Getters y Setters
    public Long getIdRoles() { return idRoles; }
    public void setIdRoles(Long idRoles) { this.idRoles = idRoles; }

    public Long getIdMenus() { return idMenus; }
    public void setIdMenus(Long idMenus) { this.idMenus = idMenus; }
}