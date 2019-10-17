package metricas.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metricas.MetricasBO;
import metricas.modelos.Historico;
import org.json.JSONException;
import org.json.JSONObject;
import usuarios.modelos.Usuario;

public class insertHistorico extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, JSONException {
    response.setContentType("application/json;charset=UTF-8");

    PrintWriter out = response.getWriter();
    Usuario u;
    HttpSession session = request.getSession(true);
    u = (Usuario) session.getAttribute("usuario");
    if (u != null) {

      try {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
          sb.append(str);
        }
        JSONObject obj = new JSONObject(sb.toString());

        Historico his = new Historico(
                obj.getString("cloud"),
                obj.getString("algoritmo"),
                obj.getString("maquinas"),
                obj.getInt("registros"),
                obj.getInt("tiempo"));

        MetricasBO mBO = new MetricasBO();
        response.setStatus(HttpServletResponse.SC_CREATED);
        out.println(mBO.guardarHistorico(his));

        out.flush();
        out.close();

      } catch (SQLException | ClassNotFoundException | JSONException ex) {
        Logger.getLogger(insertHistorico.class.getName()).log(Level.SEVERE, null, ex);
        response.setStatus(500);
        JSONObject obj = new JSONObject();
        obj.put("error", true);
        obj.put("tipoerror", 1);
        obj.put("mensaje", "Error al procesar datos");
        obj.put("excepcion", ex);
        out.println(obj);
        out.flush();
        out.close();
      }
    } else {
      response.setStatus(401);
      JSONObject obj = new JSONObject();
      obj.put("error", true);
      obj.put("tipoerror", 2);
      obj.put("mensaje", "Usuario sin autenticar");
      obj.put("excepcion", "");
      out.println(obj);
      out.flush();
      out.close();
    }

  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(insertHistorico.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(insertHistorico.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }

}
