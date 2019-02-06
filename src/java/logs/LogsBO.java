package logs;

import java.sql.SQLException;
import logs.modelos.Log;

public class LogsBO {

  private final LogsDAO DB;

  public LogsBO() {
    DB = new LogsDAO();
  }

  public void guardarRegistro(Log log) throws ClassNotFoundException, SQLException {
    DB.guardarRegistro(log);
  }

}
