package general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionHive {

  private String SERVER = "";
  private final String DBNAME = "default";
  private final String PORT = "10000";
  private final String USER = "hive";
  private final String PASSW = "hive";

  private Connection con;

  public ConexionHive() {
    con = null;
  }

  public void conectar() throws ClassNotFoundException, SQLException {
    Class.forName("org.apache.hive.jdbc.HiveDriver");
    String url = "jdbc:hive2://" + SERVER + ":" + PORT + "/" + DBNAME;
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

  public String getSERVER() {
    return SERVER;
  }

  public void setSERVER(String SERVER) {
    this.SERVER = SERVER;
  }

}
