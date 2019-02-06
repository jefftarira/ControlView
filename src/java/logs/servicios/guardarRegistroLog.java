package logs.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logs.LogsBO;
import logs.modelos.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class guardarRegistroLog extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, JSONException {

    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      StringBuilder sb = new StringBuilder();
      BufferedReader br = request.getReader();
      String str = null;
      while ((str = br.readLine()) != null) {
        sb.append(str);
      }
      JSONObject obj = new JSONObject(sb.toString());

      Log log = new Log();
      log.setOrgien(request.getRemoteAddr());
      log.setDescripcion("SERVER " + InetAddress.getLocalHost());
      log.setModulo(obj.getString("modulo").toUpperCase().trim());
      log.setServer(InetAddress.getLocalHost().toString());

      LogsBO lBO = new LogsBO();
      lBO.guardarRegistro(log);
      response.setStatus(HttpServletResponse.SC_CREATED);
      out.flush();
      out.close();

    } catch (SQLException | ClassNotFoundException | JSONException ex) {
      Logger.getLogger(guardarRegistroLog.class.getName()).log(Level.SEVERE, null, ex);
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
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(guardarRegistroLog.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(guardarRegistroLog.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }

}
