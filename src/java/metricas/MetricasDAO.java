package metricas;

import configuraciones.aws.CredencialesAwsDAO;
import configuraciones.aws.modelos.CredencialAws;
import general.ConexionHive;
import general.ConexionMysql;
import general.ConexionRedshift;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import metricas.modelos.Historico;
import metricas.modelos.Incidentes;
import metricas.modelos.PointCalifornia;
import metricas.modelos.TableInfo;

public class MetricasDAO {

  private ConexionMysql conM;
  private ConexionHive conH;

  private final String sCountRowTablesAws = "select 'california' as name, count(c.id) as total\n"
          + "from california c\n"
          + "union\n"
          + "select 'pekin' as name, count(p.id) as total\n"
          + "from pekin p";

  private final String sFileNameCountsAws = "select file_name, count(id) as total\n"
          + "from california\n"
          + "group by file_name\n"
          + "order by total desc";

  private final String iHistorico = "insert into historial (cloud, algoritmo, maquinas, registros, tiempo)\n"
          + "values (?, ?, ?, ?, ? )";

  private final String sHistorial = "select id, cloud, algoritmo, maquinas, registros, tiempo, fecha\n"
          + "from historial\n"
          + "where status = 'A'\n"
          + "order by fecha desc";

  public MetricasDAO() {
//    con = new ConexionRedshift();
    conH = new ConexionHive();
    conM = new ConexionMysql();

  }

  public ArrayList getTotalRowTablesAws() throws ClassNotFoundException, SQLException {
    ArrayList<TableInfo> lista = new ArrayList<>();
    CredencialesAwsDAO cDAO = new CredencialesAwsDAO();
    CredencialAws credencial = cDAO.getCredenciales();
    conH.setSERVER(credencial.getEndpoint_emr());
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

  public ArrayList getFileNameCountsAws() throws ClassNotFoundException, SQLException {

    ArrayList<PointCalifornia> lista = new ArrayList<>();
    CredencialesAwsDAO cDAO = new CredencialesAwsDAO();
    CredencialAws credencial = cDAO.getCredenciales();
    conH.setSERVER(credencial.getEndpoint_emr());
    conH.conectar();
    PreparedStatement ps = conH.prepareStatement(sFileNameCountsAws);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      lista.add(new PointCalifornia(
              rs.getString("file_name"),
              rs.getInt("total")
      ));
    }

    rs.close();
    conH.cerrar();
    return lista;
  }

  public int insertHistorial(Historico his) throws SQLException, ClassNotFoundException {
    conM.conectar();
    conM.autoCommit(false);

    int valor = 0;
    try {
      PreparedStatement ps = conM.prepareStatement(iHistorico);
      ps.setString(1, his.getCloud());
      ps.setString(2, his.getAlgoritmo());
      ps.setString(3, his.getMaquinas());
      ps.setInt(4, his.getRegistros());
      ps.setInt(5, his.getTiempo());
      valor = ps.executeUpdate();
      conM.Commit();
      conM.cerrar();
    } catch (SQLException ex) {
      conM.Rollback();
      conM.cerrar();
      throw new SQLException(ex);
    }
    return valor;
  }

  public ArrayList<Historico> getListHistorico() throws ClassNotFoundException, SQLException {
    ArrayList<Historico> lista = new ArrayList<>();

    conM.conectar();
    PreparedStatement ps = conM.prepareStatement(sHistorial);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      lista.add(
              new Historico(
                      rs.getInt("id"),
                      rs.getString("cloud"),
                      rs.getString("algoritmo"),
                      rs.getString("maquinas"),
                      rs.getInt("registros"),
                      rs.getTimestamp("fecha"),
                      rs.getInt("tiempo"))
      );
   }
    rs.close();
    conM.cerrar();
    return lista;
  }

}
