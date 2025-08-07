package com.contaduria.auth.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para respuesta de validación de tokens
 * Usado por otros microservicios para verificar autenticación
 */
public class ValidacionTokenResponse {

    private boolean valido;
    private Long usuarioId;
    private String usuario;
    private String nombreCompleto;
    private Long empleadoId;
    private List<String> roles;
    private List<String> permisos;
    private Boolean puedeVerTodosClientes;
    private LocalDateTime expiracion;
    private String razonInvalido;

    // Constructores
    public ValidacionTokenResponse() {}

    public ValidacionTokenResponse(boolean valido) {
        this.valido = valido;
    }

    // Métodos estáticos para crear respuestas
    public static ValidacionTokenResponse tokenValido(Long usuarioId, String usuario, String nombreCompleto,
                                                      Long empleadoId, List<String> roles, List<String> permisos,
                                                      Boolean puedeVerTodosClientes, LocalDateTime expiracion) {
        ValidacionTokenResponse response = new ValidacionTokenResponse(true);
        response.usuarioId = usuarioId;
        response.usuario = usuario;
        response.nombreCompleto = nombreCompleto;
        response.empleadoId = empleadoId;
        response.roles = roles;
        response.permisos = permisos;
        response.puedeVerTodosClientes = puedeVerTodosClientes;
        response.expiracion = expiracion;
        return response;
    }

    public static ValidacionTokenResponse tokenInvalido(String razon) {
        ValidacionTokenResponse response = new ValidacionTokenResponse(false);
        response.razonInvalido = razon;
        return response;
    }

    // Getters y Setters
    public boolean isValido() { return valido; }
    public void setValido(boolean valido) { this.valido = valido; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public List<String> getPermisos() { return permisos; }
    public void setPermisos(List<String> permisos) { this.permisos = permisos; }

    public Boolean getPuedeVerTodosClientes() { return puedeVerTodosClientes; }
    public void setPuedeVerTodosClientes(Boolean puedeVerTodosClientes) { this.puedeVerTodosClientes = puedeVerTodosClientes; }

    public LocalDateTime getExpiracion() { return expiracion; }
    public void setExpiracion(LocalDateTime expiracion) { this.expiracion = expiracion; }

    public String getRazonInvalido() { return razonInvalido; }
    public void setRazonInvalido(String razonInvalido) { this.razonInvalido = razonInvalido; }
}
