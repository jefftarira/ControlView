package metricas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

  public String getTotalDataPorAnioOrigen() throws ClassNotFoundException, SQLException, JSONException {
    ArrayList<Incidentes> lista = DB.getTotalPointByYear();

    ArrayList<String> ciudades = getNameGroupCity(lista);
    ArrayList<Integer> years = getOrderGroupYear(lista);

    JSONArray jsonArrayDataSets = new JSONArray();
    JSONArray jsonArraySeries = new JSONArray();
    for (String c : ciudades) {
      jsonArraySeries.add(c);

      JSONArray jsonArrayDataSet = new JSONArray();
      for (Integer y : years) {

        int valSet = 0;
        for (Incidentes i : lista) {
          if (i.getNombreOrigen().trim().toUpperCase().equals(c.trim().toUpperCase())
                  && i.getAnio() == y) {
            valSet = i.getConteo();
          }
        }
        jsonArrayDataSet.add(valSet);
      }
      jsonArrayDataSets.add(jsonArrayDataSet);
    }

    JSONArray jsonArrayLabels = new JSONArray();
    for (Integer y : years) {
      jsonArrayLabels.add(y.toString());
    }

    JSONObject jsonData = new JSONObject();
    jsonData.put("labels", jsonArrayLabels);
    jsonData.put("series", jsonArraySeries);
    jsonData.put("data", jsonArrayDataSets);
    return jsonData.toString();
  }

  private ArrayList<Integer> getOrderGroupYear(ArrayList<Incidentes> lista) {
    ArrayList<Integer> years = new ArrayList<>();

    //    Agrupando años
    for (Incidentes i : lista) {
      years.add(i.getAnio());
    }
    Collections.sort(years);

    return years;
  }

  private ArrayList<String> getNameGroupCity(ArrayList<Incidentes> lista) {
    ArrayList ciudades = new ArrayList<String>();

    for (Incidentes i : lista) {
      ciudades.add(i.getNombreOrigen());
    }
    HashSet<String> hashSet = new HashSet<String>(ciudades);
    ciudades.clear();
    ciudades.addAll(hashSet);

    return ciudades;
  }
}
