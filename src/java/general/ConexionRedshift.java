package general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionRedshift {

  // DESARROLLO
    private final String server = "redshift-cluster-1.csq1muuuo03p.us-east-1.redshift.amazonaws.com";
    private final String dbname = "dev";
    private final String port = "5439";
    private final String user = "master";
    private final String passw = "Reload20";
 

  private Connection con;

  public ConexionRedshift() {
    con = null;
  }

  public void conectar() throws ClassNotFoundException, SQLException {
    Class.forName("com.amazon.redshift.jdbc.Driver");
    String url = "jdbc:redshift://" + server + ":" + port + "/" + dbname;
    con = DriverManager.getConnection(url, user, passw);
  }

  public void cerrar() throws SQLException {
    if (con != null) {
      con.close();
    }
  }

  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return con.prepareStatement(sql);
  }

  public void autoCommit(boolean commit) throws SQLException {
    if (con != null) {
      con.setAutoCommit(commit);
    }
  }

  public void Commit() throws SQLException {
    if (con != null) {
      con.commit();
    }
  }

  public void Rollback() throws SQLException {
    if (con != null) {
      con.rollback();
    }
  }

}
