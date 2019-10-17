package configuraciones.gcp;

import configuraciones.gcp.modelos.CredencialGcp;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class CredencialesGcpBO {

  private final CredencialesGcpDAO DB;

  public CredencialesGcpBO() throws ClassNotFoundException, SQLException {
    DB = new CredencialesGcpDAO();
  }

  public String getCredencialesGcp() throws ClassNotFoundException, SQLException, JSONException {
    JSONObject obj = new JSONObject();

    CredencialGcp c = DB.getCredenciales();
    if (c.getId_clave() != null) {
      obj.put("id", c.getId());
      obj.put("nombre_proyecto", c.getNombre_proyecto());
      obj.put("email_iam", c.getEmail_iam());
      obj.put("id_unico", c.getId_unico());
      obj.put("id_clave", c.getId_clave());
      obj.put("bucket_file", c.getBucket_file());
      obj.put("endpoint_dataproc", c.getEndpoint_dataproc());
      System.out.println(c.getEmail_iam());

    } else {
      obj.put("id", 0);
      obj.put("nombre_proyecto", "");
      obj.put("email_iam", "");
      obj.put("id_unico", "");
      obj.put("id_clave", "");
      obj.put("bucket_file", "");
      obj.put("endpoint_dataproc", "");
    }
    return obj.toString();
  }

  public int guardarCredencialesGcp(CredencialGcp credencial) throws ClassNotFoundException, SQLException {
    if (credencial.getId() == 0) {
      return DB.crearCredencial(credencial);
    } else {
      return DB.modificarCredencial(credencial);
    }

  }

}
