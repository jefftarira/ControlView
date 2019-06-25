package usuarios.modelos;

import java.sql.Timestamp;

public class Usuario {

  private int id;
  private String usuario;
  private String clave;
  private boolean cambiarClave;
  private String nombres;
  private String apellidos;
  private String mail;
  private Timestamp fechaRegistro;
  private String buscar;
  private String status;

  public Usuario() {
  }

  public Usuario(int id, String usuario, String clave, boolean cambiarClave,
          String nombres, String apellidos, String mail, Timestamp fechaRegistro,
          String buscar, String status) {
    this.id = id;
    this.usuario = usuario;
    this.clave = clave;
    this.cambiarClave = cambiarClave;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.mail = mail;
    this.fechaRegistro = fechaRegistro;
    this.buscar = buscar;
    this.status = status;
  }

  public Usuario(int id, String usuario, String nombres, String apellidos, String mail) {
    this.id = id;
    this.usuario = usuario;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.mail = mail;
  }

  public Usuario(String usuario, String clave) {
    this.usuario = usuario;
    this.clave = clave;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public boolean isCambiarClave() {
    return cambiarClave;
  }

  public void setCambiarClave(boolean cambiarClave) {
    this.cambiarClave = cambiarClave;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public Timestamp getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Timestamp fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getBuscar() {
    return buscar;
  }

  public void setBuscar(String buscar) {
    this.buscar = buscar;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
