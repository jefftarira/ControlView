package configuraciones.aws;

import configuraciones.aws.modelos.CredencialAws;
import general.ConexionMysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredencialesAwsDAO {

  private final ConexionMysql con;

  private final String sCredencialesAws = "select id, access_key, secret_key,\n"
          + "descripcion, endpoint_emr, region, bucket_s3, status\n"
          + "from credenciales_aws\n"
          + "where status = 'A'\n"
          + "limit 1";

  private final String sCredencialesAwsById = "select id, access_key, secret_key,\n"
          + " descripcion, endpoint_emr, region, bucket_s3, status\n"
          + "from credenciales_aws\n"
          + "where status = 'A'\n"
          + " and  id = ? ";

  private final String insertCredencial = "insert into credenciales_aws\n"
          + "(access_key, secret_key, descripcion, endpoint_emr, region, bucket_s3)\n"
          + "    value (?, ?, ?, ?, ?, ?)";

  private final String updateCredencial = "update credenciales_aws set access_key = ? , secret_key = ?, descripcion = ?, endpoint_emr = ?,\n"
          + "region = ?, bucket_s3 = ?\n"
          + "where id = ? ";

  public CredencialesAwsDAO() throws ClassNotFoundException, SQLException {
    con = new ConexionMysql();
  }

  public CredencialAws getCredenciales() throws ClassNotFoundException, SQLException {
    con.conectar();
    PreparedStatement ps = con.prepareStatement(sCredencialesAws);
    ResultSet rs = ps.executeQuery();

    CredencialAws caws = new CredencialAws();
    if (rs.next()) {
      caws.setId(rs.getInt(1));
      caws.setAccess_key(rs.getString(2));
      caws.setSecret_key(rs.getString(3));
      caws.setDescripcion(rs.getString(4));
      caws.setEndpoint_emr(rs.getString(5));
      caws.setRegion(rs.getString(6));
      caws.setBucket_s3(rs.getString(7));
      caws.setStatus(rs.getString(8));
    }
    rs.close();
    con.cerrar();
    return caws;
  }

  public CredencialAws getCredencialById(int id) throws ClassNotFoundException, SQLException {
    con.conectar();
    PreparedStatement ps = con.prepareStatement(sCredencialesAwsById);
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();

    CredencialAws caws = new CredencialAws();
    if (rs.next()) {
      caws.setId(rs.getInt(1));
      caws.setAccess_key(rs.getString(2));
      caws.setSecret_key(rs.getString(3));
      caws.setDescripcion(rs.getString(4));
      caws.setEndpoint_emr(rs.getString(5));
      caws.setRegion(rs.getString(6));
      caws.setBucket_s3(rs.getString(7));
      caws.setStatus(rs.getString(8));
    }
    rs.close();
    con.cerrar();
    return caws;
  }

  public int crearCredencial(CredencialAws caws) throws ClassNotFoundException, SQLException {
    con.conectar();
    con.autoCommit(false);

    int valor = 0;
    try {
      PreparedStatement ps = con.prepareStatement(insertCredencial);
      ps.setString(1, caws.getAccess_key().trim());
      ps.setString(2, caws.getSecret_key().trim());
      ps.setString(3, caws.getDescripcion().trim());
      ps.setString(4, caws.getEndpoint_emr().trim());
      ps.setString(5, caws.getRegion().trim());
      ps.setString(6, caws.getBucket_s3().trim());
      valor = ps.executeUpdate();
      con.Commit();
      con.cerrar();
    } catch (SQLException ex) {
      con.Rollback();
      con.cerrar();
      throw new SQLException(ex);
    }
    return valor;
  }

  public int modificarCredencial(CredencialAws caws) throws ClassNotFoundException, SQLException {
    con.conectar();
    con.autoCommit(false);

    int valor = 0;
    try {
      PreparedStatement ps = con.prepareStatement(updateCredencial);
      ps.setString(1, caws.getAccess_key().trim());
      ps.setString(2, caws.getSecret_key().trim());
      ps.setString(3, caws.getDescripcion().trim());
      ps.setString(4, caws.getEndpoint_emr().trim());
      ps.setString(5, caws.getRegion().trim());
      ps.setString(6, caws.getBucket_s3().trim());
      ps.setInt(7, caws.getId());
      valor = ps.executeUpdate();
      con.Commit();
      con.cerrar();
    } catch (SQLException ex) {
      con.Rollback();
      con.cerrar();
      throw new SQLException(ex);
    }
    return valor;
  }

}
