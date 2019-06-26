package usuarios.servicios;

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
import usuarios.UsuariosBO;
import usuarios.modelos.Usuario;

public class login extends HttpServlet {

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
      JSONObject jObj = new JSONObject(sb.toString());

      String usuario = jObj.getString("usuario");
      String clave = jObj.getString("clave");

      Usuario u = null;
      UsuariosBO userBO = new UsuariosBO();
      u = userBO.autenticar(usuario, clave);

      if (u != null) {
        HttpSession session = request.getSession(true);
        session.setAttribute("usuario", u);
        session.setMaxInactiveInterval(86400);

        response.setStatus(201);
        JSONObject res = new JSONObject();
        res.put("mensaje", "Ha ingresado al sistema.");
        out.println(res);
        out.flush();
        out.close();
      } else {
        response.setStatus(401);
        JSONObject res = new JSONObject();
        res.put("error", true);
        res.put("tipoerror", 2);
        res.put("mensaje", "Usuario o contraseña incorrecto!");
        res.put("excepcion", "");
        out.println(res);
        out.flush();
        out.close();
      }

    } catch (SQLException | ClassNotFoundException | JSONException ex) {
      Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
      response.setStatus(500);
      JSONObject res = new JSONObject();
      res.put("error", true);
      res.put("tipoerror", 1);
      res.put("mensaje", "Error al autenticar.");
      res.put("excepcion", ex);
      out.println(res);
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
      Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }

}
