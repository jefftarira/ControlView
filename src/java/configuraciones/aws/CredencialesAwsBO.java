package configuraciones.aws;

import configuraciones.aws.modelos.CredencialAws;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class CredencialesAwsBO {

  private final CredencialesAwsDAO DB;

  public CredencialesAwsBO() throws ClassNotFoundException, SQLException {
    DB = new CredencialesAwsDAO();
  }

  public String getCredencialesAws() throws ClassNotFoundException, SQLException, JSONException {
    JSONObject obj = new JSONObject();

    CredencialAws c = DB.getCredenciales();
    if (c.getAccess_key() != null) {
      obj.put("id", c.getId());
      obj.put("access_key", c.getAccess_key());
      obj.put("secret_key", c.getSecret_key());
      obj.put("descripcion", c.getDescripcion());
      obj.put("endpoint_emr", c.getEndpoint_emr());
      obj.put("region", c.getRegion());
      obj.put("bucket_s3", c.getBucket_s3());
    } else {
      obj.put("id", 0);
      obj.put("access_key", "");
      obj.put("secret_key", "");
      obj.put("descripcion", "");
      obj.put("endpoint_emr", "");
      obj.put("region", "");
      obj.put("bucket_s3", "");
    }
    return obj.toString();
  }

  public int guardarCredencialesAws(CredencialAws credencial) throws ClassNotFoundException, SQLException {
    if (credencial.getId() == 0) {
      return DB.crearCredencial(credencial);
    } else {
      return DB.modificarCredencial(credencial);
    }

  }



}
