package configuraciones.gcp.modelos;

public class CredencialGcp {

  private int id;
  private String nombre_proyecto;
  private String email_iam;
  private String id_unico;
  private String id_clave;
  private String bucket_file;
  private String endpoint_dataproc;
  private String status;

  public CredencialGcp() {
  }

  public CredencialGcp(int id, String nombre_proyecto, String email_iam,
          String id_unico, String id_clave, String bucket_file,
          String endpoint_dataproc, String status) {
    this.id = id;
    this.nombre_proyecto = nombre_proyecto;
    this.email_iam = email_iam;
    this.id_unico = id_unico;
    this.id_clave = id_clave;
    this.bucket_file = bucket_file;
    this.endpoint_dataproc = endpoint_dataproc;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre_proyecto() {
    return nombre_proyecto;
  }

  public void setNombre_proyecto(String nombre_proyecto) {
    this.nombre_proyecto = nombre_proyecto;
  }

  public String getEmail_iam() {
    return email_iam;
  }

  public void setEmail_iam(String email_iam) {
    this.email_iam = email_iam;
  }

  public String getId_unico() {
    return id_unico;
  }

  public void setId_unico(String id_unico) {
    this.id_unico = id_unico;
  }

  public String getId_clave() {
    return id_clave;
  }

  public void setId_clave(String id_clave) {
    this.id_clave = id_clave;
  }

  public String getBucket_file() {
    return bucket_file;
  }

  public void setBucket_file(String bucket_file) {
    this.bucket_file = bucket_file;
  }

  public String getEndpoint_dataproc() {
    return endpoint_dataproc;
  }

  public void setEndpoint_dataproc(String endpoint_dataproc) {
    this.endpoint_dataproc = endpoint_dataproc;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
