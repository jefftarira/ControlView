package metricasgcp.servicios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metricasgcp.MetricasgcpBO;
import org.json.JSONException;
import org.json.JSONObject;
import usuarios.modelos.Usuario;

public class infodataproc extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, JSONException {

    response.setContentType("application/json;charset=UTF-8");

    Usuario u;
    HttpSession session = request.getSession(true);
    u = (Usuario) session.getAttribute("usuario");
    if (u != null) {
      PrintWriter out = response.getWriter();
      try {
        MetricasgcpBO mBO = new MetricasgcpBO();
        String json = mBO.getInfoDataproc();
        response.setStatus(HttpServletResponse.SC_OK);
        out.println(json);
        out.flush();
        out.close();

      } catch (ClassNotFoundException | JSONException ex) {
        Logger.getLogger(infodataproc.class.getName()).log(Level.SEVERE, null, ex);
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
      JSONObject obj = new JSONObject();
      obj.put("error", true);
      obj.put("tipoerror", 2);
      obj.put("exception", "");
      obj.put("mensaje", "Usuario sin autenticar");
      PrintWriter out = response.getWriter();
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
      Logger.getLogger(infodataproc.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(infodataproc.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }
}
