package configuraciones.aws.modelos;

public class CredencialAws {

  private int id;
  private String access_key;
  private String secret_key;
  private String descripcion;
  private String endpoint_emr;
  private String region;
  private String bucket_s3;
  private String status;

  public CredencialAws() {
  }

  public CredencialAws(int id, String access_key, String secret_key,
          String descripcion, String endpoint_emr, String region,
          String bucket_s3, String status) {
    this.id = id;
    this.access_key = access_key;
    this.secret_key = secret_key;
    this.descripcion = descripcion;
    this.endpoint_emr = endpoint_emr;
    this.region = region;
    this.bucket_s3 = bucket_s3;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAccess_key() {
    return access_key;
  }

  public void setAccess_key(String access_key) {
    this.access_key = access_key;
  }

  public String getSecret_key() {
    return secret_key;
  }

  public void setSecret_key(String secret_key) {
    this.secret_key = secret_key;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getEndpoint_emr() {
    return endpoint_emr;
  }

  public void setEndpoint_emr(String endpoint_emr) {
    this.endpoint_emr = endpoint_emr;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getBucket_s3() {
    return bucket_s3;
  }

  public void setBucket_s3(String bucket_s3) {
    this.bucket_s3 = bucket_s3;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
