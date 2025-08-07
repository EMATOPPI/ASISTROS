package com.contaduria.auth.dto.response;

import java.time.LocalDateTime;

/**
 * DTO para reporte de actividad del sistema
 */
public class ReporteActividadResponse {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer totalRegistros;
    private Long loginsExitosos;
    private Long loginsFallidos;
    private Long cambiosContrasena;
    private Long operacionesCrud;
    private Double porcentajeExitoLogins;

    // Constructores
    public ReporteActividadResponse() {}

    // Getters y Setters
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public Integer getTotalRegistros() { return totalRegistros; }
    public void setTotalRegistros(Integer totalRegistros) { this.totalRegistros = totalRegistros; }

    public Long getLoginsExitosos() { return loginsExitosos; }
    public void setLoginsExitosos(Long loginsExitosos) { this.loginsExitosos = loginsExitosos; }

    public Long getLoginsFallidos() { return loginsFallidos; }
    public void setLoginsFallidos(Long loginsFallidos) { this.loginsFallidos = loginsFallidos; }

    public Long getCambiosContrasena() { return cambiosContrasena; }
    public void setCambiosContrasena(Long cambiosContrasena) { this.cambiosContrasena = cambiosContrasena; }

    public Long getOperacionesCrud() { return operacionesCrud; }
    public void setOperacionesCrud(Long operacionesCrud) { this.operacionesCrud = operacionesCrud; }

    public Double getPorcentajeExitoLogins() { return porcentajeExitoLogins; }
    public void setPorcentajeExitoLogins(Double porcentajeExitoLogins) { this.porcentajeExitoLogins = porcentajeExitoLogins; }
}