package configuraciones.gcp.servicios;

import configuraciones.gcp.CredencialesGcpBO;
import configuraciones.gcp.modelos.CredencialGcp;
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
import org.json.JSONException;
import org.json.JSONObject;
import usuarios.modelos.Usuario;

public class guardarcredencialesgcp extends HttpServlet {

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

        CredencialGcp c = new CredencialGcp(
                obj.getInt("id"),
                obj.getString("nombre_proyecto"),
                obj.getString("email_iam"),
                obj.getString("id_unico"),
                obj.getString("id_clave"),
                obj.getString("bucket_file"),
                obj.getString("endpoint_dataproc"),
                "A"
        );

        CredencialesGcpBO cBO = new CredencialesGcpBO();
        response.setStatus(HttpServletResponse.SC_CREATED);
        out.println(cBO.guardarCredencialesGcp(c));

        out.flush();
        out.close();

      } catch (SQLException | ClassNotFoundException | JSONException ex) {
        Logger.getLogger(guardarcredencialesgcp.class.getName()).log(Level.SEVERE, null, ex);
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
      Logger.getLogger(guardarcredencialesgcp.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(guardarcredencialesgcp.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }

}
