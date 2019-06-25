package usuarios.modelos;

public class Permiso {

  private int id;
  private int idUsuario;
  private int idModulo;
  private String nombreModulo;
  private int ingresar;
  private int editar;
  private String status;

  public Permiso() {
  }

  public Permiso(int id, int idUsuario, int idModulo, String nombreModulo,
          int ingresar, int editar, String status) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.idModulo = idModulo;
    this.nombreModulo = nombreModulo;
    this.ingresar = ingresar;
    this.editar = editar;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public int getIdModulo() {
    return idModulo;
  }

  public void setIdModulo(int idModulo) {
    this.idModulo = idModulo;
  }

  public String getNombreModulo() {
    return nombreModulo;
  }

  public void setNombreModulo(String nombreModulo) {
    this.nombreModulo = nombreModulo;
  }

  public int getIngresar() {
    return ingresar;
  }

  public void setIngresar(int ingresar) {
    this.ingresar = ingresar;
  }

  public int getEditar() {
    return editar;
  }

  public void setEditar(int editar) {
    this.editar = editar;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
