package configuraciones.aws.servicios;

import configuraciones.aws.CredencialesAwsBO;
import configuraciones.aws.modelos.CredencialAws;
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

public class guardarcredencialesaws extends HttpServlet {

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

        CredencialAws c = new CredencialAws(
                obj.getInt("id"),
                obj.getString("access_key"),
                obj.getString("secret_key"),
                obj.getString("descripcion"),
                obj.getString("endpoint_emr"),
                obj.getString("region"),
                obj.getString("bucket_s3"),
                "A"
        );

        CredencialesAwsBO cBO = new CredencialesAwsBO();
        response.setStatus(HttpServletResponse.SC_CREATED);
        out.println(cBO.guardarCredencialesAws(c));

        out.flush();
        out.close();

      } catch (SQLException | ClassNotFoundException | JSONException ex) {
        Logger.getLogger(guardarcredencialesaws.class.getName()).log(Level.SEVERE, null, ex);
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
      Logger.getLogger(guardarcredencialesaws.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(guardarcredencialesaws.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }

}
