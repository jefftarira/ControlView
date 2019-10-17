package metricasgcp;

import general.ConexionHive;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import metricas.modelos.TableInfo;
import metricasgcp.modelos.CredencialGcp;

public class MetricasgcpDAO {

  private ConexionHive conH;

  public MetricasgcpDAO() {
    conH = new ConexionHive();
  }

  public ArrayList getTotalRowTablesHive() throws ClassNotFoundException, SQLException {
    ArrayList<TableInfo> lista = new ArrayList<>();
    conH.setSERVER(CredencialGcp.dataproc_endpoint);
    conH.conectar();
    PreparedStatement ps = conH.prepareStatement("show tables");
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      lista.add(new TableInfo(
              rs.getString("tab_name")
      ));

    }
    rs.close();
    conH.cerrar();
    return lista;
  }

}
