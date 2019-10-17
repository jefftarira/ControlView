package general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionMysql {

  // DESARROLLO
  private final String SERVER = "localhost";
  private final String DBNAME = "control";
  private final String PORT = "3306";
  private final String USER = "user";
  private final String PASSW = "12345678";

  //AWS
  //  private final String SERVER = "db.czo22n7vybjz.us-east-1.rds.amazonaws.com";
  //  private final String DBNAME = "control";
  //  private final String PORT = "3306";
  //  private final String USER = "user";
  //  private final String PASSW = "reload20";
  private Connection con;

  public ConexionMysql() {
    con = null;
  }

  public void conectar() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DBNAME + "?useSSL=false";
    con = DriverManager.getConnection(url, USER, PASSW);
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
