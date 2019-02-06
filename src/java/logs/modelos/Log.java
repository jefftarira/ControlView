package logs.modelos;

import java.sql.Timestamp;

public class Log {

  private int id;
  private String descripcion;
  private String modulo;
  private String estado;
  private Timestamp fecha;
  private String orgien;
  private String server;

  public Log() {
  }

  public Log(int id, String descripcion, String modulo, String estado,
          Timestamp fecha, String orgien, String server) {
    this.id = id;
    this.descripcion = descripcion;
    this.modulo = modulo;
    this.estado = estado;
    this.fecha = fecha;
    this.orgien = orgien;
    this.server = server;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getModulo() {
    return modulo;
  }

  public void setModulo(String modulo) {
    this.modulo = modulo;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Timestamp getFecha() {
    return fecha;
  }

  public void setFecha(Timestamp fecha) {
    this.fecha = fecha;
  }

  public String getOrgien() {
    return orgien;
  }

  public void setOrgien(String orgien) {
    this.orgien = orgien;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

}
