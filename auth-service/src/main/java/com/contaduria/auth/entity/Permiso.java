package com.contaduria.auth.entity;

import jakarta.persistence.*;

/**
 * Entidad que representa los permisos de acceso a menús
 * Mapea directamente a la tabla 'permisos' existente
 */
@Entity
@Table(name = "permisos")
@IdClass(PermisoId.class)
public class Permiso {

    @Id
    @Column(name = "idroles")
    private Long idRoles;

    @Id
    @Column(name = "idmenus")
    private Long idMenus;

    @Column(name = "ver")
    private Integer ver;

    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idroles", insertable = false, updatable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmenus", insertable = false, updatable = false)
    private Menu menu;

    // Constructores
    public Permiso() {}

    public Permiso(Long idRoles, Long idMenus, Integer ver) {
        this.idRoles = idRoles;
        this.idMenus = idMenus;
        this.ver = ver;
    }

    // Métodos de utilidad
    public boolean puedeVer() {
        return ver != null && ver == 1;
    }

    // Getters y Setters
    public Long getIdRoles() { return idRoles; }
    public void setIdRoles(Long idRoles) { this.idRoles = idRoles; }

    public Long getIdMenus() { return idMenus; }
    public void setIdMenus(Long idMenus) { this.idMenus = idMenus; }

    public Integer getVer() { return ver; }
    public void setVer(Integer ver) { this.ver = ver; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }
}
