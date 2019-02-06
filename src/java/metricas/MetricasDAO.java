package metricas;

import general.ConexionRedshift;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import metricas.modelos.Incidentes;

public class MetricasDAO {

  private ConexionRedshift con;

  private final String sConteoPorRangoFechas = "select\n"
          + "  date(i.fecha) as fecha,\n"
          + "  o.descripcion,\n"
          + "  count(i.id)   as conteo\n"
          + "from incidentes i, origen o\n"
          + "where i.id_origen = o.id\n"
          + "      and (date(i.fecha) between '2017-03-01' and '2017-03-31')\n"
          + "group by 1, 2\n"
          + "order by 1";

  private final String otherSelect = "select date(i.fecha) as fecha,\n"
          + "       o.descripcion,\n"
          + "       count(i.id)   as conteo\n"
          + "from incidentes i,\n"
          + "     origen o\n"
          + "where i.id_origen = o.id\n"
          + "  and (date(i.fecha) between '2017-03-01' and '2017-03-31')\n"
          + "group by 1, 2\n"
          + "order by 1;\n"
          + "\n"
          + "select o.descripcion, extract(year from i.fecha), count(*)\n"
          + "from incidentes i,\n"
          + "     origen o\n"
          + "where i.id_origen = o.id\n"
          + "group by 1,2\n"
          + "order by 1,2";

  public MetricasDAO() {
    con = new ConexionRedshift();
  }

  public ArrayList<Incidentes> getConteoPorRangoFechas()
          throws ClassNotFoundException, SQLException {
    ArrayList<Incidentes> lista = new ArrayList<>();

    con.conectar();
    PreparedStatement ps = con.prepareStatement(sConteoPorRangoFechas);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      lista.add(
              new Incidentes(
                      rs.getDate("fecha"),
                      rs.getString("descripcion"),
                      rs.getInt("conteo")
              )
      );
    }

    rs.close();
    con.cerrar();
    return lista;
  }

}
