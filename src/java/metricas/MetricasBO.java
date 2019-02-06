package metricas;

import java.sql.SQLException;
import java.util.ArrayList;
import metricas.modelos.Incidentes;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

public class MetricasBO {

  private MetricasDAO DB;

  public MetricasBO() {
    DB = new MetricasDAO();
  }

  public String getConteoPorRangoFechas()
          throws ClassNotFoundException, SQLException, JSONException {
    ArrayList<Incidentes> lista = DB.getConteoPorRangoFechas();

    JSONArray jsonArray = new JSONArray();
    for (Incidentes incidente : lista) {
      JSONObject jsonElement = new JSONObject();
      jsonElement.put("fecha", incidente.getFecha());
      jsonElement.put("origen", incidente.getNombreOrigen());
      jsonElement.put("conteo", incidente.getConteo());

      jsonArray.add(jsonElement);
    }
    return jsonArray.toJSONString();
  }

}
