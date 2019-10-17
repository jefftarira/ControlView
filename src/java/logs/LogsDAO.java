package logs;

import general.ConexionMysql;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import logs.modelos.Log;

public class LogsDAO {

  private ConexionMysql con;

  private String iLog = "insert into log (descripcion, modulo, estado, fecha, origen, server)\n"
          + "values (?, ?, ?, sysdate(), ?, ?)";

  public LogsDAO() {
    con = new ConexionMysql();
  }

  public void guardarRegistro(Log log) throws ClassNotFoundException, SQLException {

    con.conectar();
    con.autoCommit(false);

    try {
      PreparedStatement ps = con.prepareStatement(iLog);
      ps.setString(1, log.getDescripcion());
      ps.setString(2, log.getModulo());
      ps.setString(3, "A");
      ps.setString(4, log.getOrgien());
      ps.setString(5, log.getServer());
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
