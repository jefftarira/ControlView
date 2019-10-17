package configuraciones.gcp;

import configuraciones.gcp.modelos.CredencialGcp;
import general.ConexionMysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredencialesGcpDAO {

  private final ConexionMysql con;

  private final String sCredencialesGcp = "select id,\n"
          + "       nombre_proyecto,\n"
          + "       email_iam,\n"
          + "       id_unico,\n"
          + "       id_clave,\n"
          + "       bucket_file,\n"
          + "       endpoint_dataproc,\n"
          + "       status\n"
          + "from credenciales_gcp\n"
          + "where status = 'A'\n"
          + "limit 1";

  private final String sCredencialesGcpById = "select id,\n"
          + "       nombre_proyecto,\n"
          + "       email_iam,\n"
          + "       id_unico,\n"
          + "       id_clave,\n"
          + "       bucket_file,\n"
          + "       endpoint_dataproc,\n"
          + "       status\n"
          + "from credenciales_gcp\n"
          + "where status = 'A'\n"
          + "and id = ?\n"
          + "limit 1";

  private final String insertCredencial = "insert into credenciales_gcp\n"
          + "(nombre_proyecto, email_iam, id_unico, id_clave, bucket_file, endpoint_dataproc)\n"
          + "values (?, ?, ?, ?, ?, ?)";

  private final String updateCredencial = "update credenciales_gcp\n"
          + "set nombre_proyecto   = ?,\n"
          + "    email_iam         = ?,\n"
          + "    id_unico          = ?,\n"
          + "    id_clave          = ?,\n"
          + "    bucket_file       = ?,\n"
          + "    endpoint_dataproc = ?\n"
          + "where id = ?\n"
          + "and status = 'A'";

  public CredencialesGcpDAO() throws ClassNotFoundException, SQLException {
    con = new ConexionMysql();
  }

  public CredencialGcp getCredenciales() throws ClassNotFoundException, SQLException {
    con.conectar();
    PreparedStatement ps = con.prepareStatement(sCredencialesGcp);
    ResultSet rs = ps.executeQuery();

    CredencialGcp cre = new CredencialGcp();
    if (rs.next()) {
      cre.setId(rs.getInt(1));
      cre.setNombre_proyecto(rs.getString(2));
      cre.setEmail_iam(rs.getString(3));
      cre.setId_unico(rs.getString(4));
      cre.setId_clave(rs.getString(5));
      cre.setBucket_file(rs.getString(6));
      cre.setEndpoint_dataproc(rs.getString(7));
      cre.setStatus(rs.getString(8));
    }
    rs.close();
    con.cerrar();
    return cre;
  }

  public CredencialGcp getCredencialById(int id) throws ClassNotFoundException, SQLException {
    con.conectar();
    PreparedStatement ps = con.prepareStatement(sCredencialesGcpById);
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();

    CredencialGcp cre = new CredencialGcp();
    if (rs.next()) {
      cre.setId(rs.getInt(1));
      cre.setNombre_proyecto(rs.getString(2));
      cre.setEmail_iam(rs.getString(3));
      cre.setId_unico(rs.getString(4));
      cre.setId_clave(rs.getString(5));
      cre.setBucket_file(rs.getString(6));
      cre.setEndpoint_dataproc(rs.getString(7));
      cre.setStatus(rs.getString(8));
    }
    rs.close();
    con.cerrar();
    return cre;
  }

  public int crearCredencial(CredencialGcp cre) throws ClassNotFoundException, SQLException {
    con.conectar();
    con.autoCommit(false);

    int valor = 0;
    try {
      PreparedStatement ps = con.prepareStatement(insertCredencial);
      ps.setString(1, cre.getNombre_proyecto().trim());
      ps.setString(2, cre.getEmail_iam().trim());
      ps.setString(3, cre.getId_unico().trim());
      ps.setString(4, cre.getId_clave().trim());
      ps.setString(5, cre.getBucket_file().trim());
      ps.setString(6, cre.getEndpoint_dataproc().trim());      
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

  public int modificarCredencial(CredencialGcp cre) throws ClassNotFoundException, SQLException {
    con.conectar();
    con.autoCommit(false);

    int valor = 0;
    try {
      PreparedStatement ps = con.prepareStatement(updateCredencial);
      ps.setString(1, cre.getNombre_proyecto().trim());
      ps.setString(2, cre.getEmail_iam().trim());
      ps.setString(3, cre.getId_unico().trim());
      ps.setString(4, cre.getId_clave().trim());
      ps.setString(5, cre.getBucket_file().trim());
      ps.setString(6, cre.getEndpoint_dataproc().trim());
      ps.setInt(7, cre.getId());
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
