package logs;

import general.ConexionRedshift;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import logs.modelos.Log;

public class LogsDAO {

  private ConexionRedshift con;

  private String sMaxIdLog = "select max(id) as total \n"
          + "from logs";

  private String iLog = "insert into logs (id, descripcion, modulo, estado, fecha, origen, server)\n"
          + "values (?, ?, ?, ?, GETDATE(), ?, ?)";

  public LogsDAO() {
    con = new ConexionRedshift();
  }

  public void guardarRegistro(Log log) throws ClassNotFoundException, SQLException {

    con.conectar();
    con.autoCommit(false);

    try {
      PreparedStatement ps = con.prepareStatement(sMaxIdLog);
      ResultSet rs = ps.executeQuery();
      rs.next();
      int maxId = rs.getInt("total") + 1;

      ps = con.prepareStatement(iLog);
      ps.setInt(1, maxId);
      ps.setString(2, log.getDescripcion());
      ps.setString(3, log.getModulo());
      ps.setString(4, "A");
      ps.setString(5, log.getOrgien());
      ps.setString(6, log.getServer());
      ps.executeUpdate();

      con.Commit();
      con.cerrar();
    } catch (SQLException ex) {
      con.Rollback();
      con.cerrar();
      throw new SQLException(ex);
    }

  }

}
