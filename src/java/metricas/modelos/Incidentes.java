package metricas.modelos;

import java.sql.Date;

public class Incidentes {

  public int id;
  public Date fecha;
  public String nombreOrigen;
  public int conteo;

  public Incidentes() {
  }

  public Incidentes(int id, Date fecha, String nombreOrigen, int conteo) {
    this.id = id;
    this.fecha = fecha;
    this.nombreOrigen = nombreOrigen;
    this.conteo = conteo;
  }

  public Incidentes(Date fecha, String nombreOrigen, int conteo) {
    this.fecha = fecha;
    this.nombreOrigen = nombreOrigen;
    this.conteo = conteo;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getNombreOrigen() {
    return nombreOrigen;
  }

  public void setNombreOrigen(String nombreOrigen) {
    this.nombreOrigen = nombreOrigen;
  }

  public int getConteo() {
    return conteo;
  }

  public void setConteo(int conteo) {
    this.conteo = conteo;
  }

}
