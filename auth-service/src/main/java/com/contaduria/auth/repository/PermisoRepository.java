package com.contaduria.auth.repository;

import com.contaduria.auth.entity.Permiso;
import com.contaduria.auth.entity.PermisoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para manejo de permisos del sistema
 */
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, PermisoId> {

    /**
     * Busca permisos por rol
     * @param rolId ID del rol
     * @return Lista de permisos del rol
     */
    List<Permiso> findByIdRoles(Long rolId);

    /**
     * Busca permisos por menú
     * @param menuId ID del menú
     * @return Lista de permisos del menú
     */
    List<Permiso> findByIdMenus(Long menuId);

    /**
     * Busca permisos que permiten ver (ver = 1)
     * @param rolId ID del rol
     * @return Lista de permisos de visualización del rol
     */
    @Query("SELECT p FROM Permiso p WHERE p.idRoles = :rolId AND p.ver = 1")
    List<Permiso> findPermisosVisualizacionByRol(@Param("rolId") Long rolId);

    /**
     * Busca permisos con información completa del menú
     * @param rolId ID del rol
     * @return Lista de permisos con datos del menú
     */
    @Query("SELECT p FROM Permiso p LEFT JOIN FETCH p.menu m WHERE p.idRoles = :rolId")
    List<Permiso> findPermisosConMenuByRol(@Param("rolId") Long rolId);

    /**
     * Busca todos los permisos activos con menús activos
     * @return Lista de permisos válidos
     */
    @Query("SELECT p FROM Permiso p " +
            "LEFT JOIN FETCH p.menu m " +
            "LEFT JOIN FETCH p.rol r " +
            "WHERE r.activo = true AND m.activo = true AND p.ver = 1")
    List<Permiso> findPermisosActivos();

    /**
     * Verifica si un rol tiene permiso específico para un menú
     * @param rolId ID del rol
     * @param menuId ID del menú
     * @return true si tiene permiso, false si no
     */
    @Query("SELECT COUNT(p) > 0 FROM Permiso p WHERE p.idRoles = :rolId AND p.idMenus = :menuId AND p.ver = 1")
    boolean tienePermisoVer(@Param("rolId") Long rolId, @Param("menuId") Long menuId);

    /**
     * Busca permisos por múltiples roles
     * @param rolesIds Lista de IDs de roles
     * @return Lista de permisos de los roles
     */
    @Query("SELECT DISTINCT p FROM Permiso p " +
            "LEFT JOIN FETCH p.menu m " +
            "WHERE p.idRoles IN :rolesIds AND p.ver = 1 AND m.activo = true")
    List<Permiso> findPermisosByRoles(@Param("rolesIds") List<Long> rolesIds);
}