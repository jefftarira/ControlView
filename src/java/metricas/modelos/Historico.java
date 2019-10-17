package metricas.modelos;

import java.sql.Timestamp;

public class Historico {

  private int id;
  private String cloud;
  private String algoritmo;
  private String maquinas;
  private int registros;
  private Timestamp fecha;
  private int tiempo;

  public Historico() {
  }

  public Historico(int id, String cloud, String algoritmo, String maquinas,
          int registros, Timestamp fecha, int tiempo) {
    this.id = id;
    this.cloud = cloud;
    this.algoritmo = algoritmo;
    this.maquinas = maquinas;
    this.registros = registros;
    this.fecha = fecha;
    this.tiempo = tiempo;
  }

  public Historico(String cloud, String algoritmo, String maquinas, int registros, int tiempo) {
    this.cloud = cloud;
    this.algoritmo = algoritmo;
    this.maquinas = maquinas;
    this.registros = registros;
    this.tiempo = tiempo;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCloud() {
    return cloud;
  }

  public void setCloud(String cloud) {
    this.cloud = cloud;
  }

  public String getAlgoritmo() {
    return algoritmo;
  }

  public void setAlgoritmo(String algoritmo) {
    this.algoritmo = algoritmo;
  }

  public String getMaquinas() {
    return maquinas;
  }

  public void setMaquinas(String maquinas) {
    this.maquinas = maquinas;
  }

  public int getRegistros() {
    return registros;
  }

  public void setRegistros(int registros) {
    this.registros = registros;
  }

  public Timestamp getFecha() {
    return fecha;
  }

  public void setFecha(Timestamp fecha) {
    this.fecha = fecha;
  }

  public int getTiempo() {
    return tiempo;
  }

  public void setTiempo(int tiempo) {
    this.tiempo = tiempo;
  }
}
