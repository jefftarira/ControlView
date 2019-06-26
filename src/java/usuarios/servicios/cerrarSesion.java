package usuarios.servicios;

import java.io.IOException;
import java.io.PrintWriter;
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

public class cerrarSesion extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, JSONException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    boolean bError = false;
    String mensajeError = "";

    HttpSession session = request.getSession(true);

    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u != null) {
      session.removeAttribute("usuario");
    } else {
      bError = true;
      mensajeError = "Ocurrio un error al cerrar session";
    }
    JSONObject obj = new JSONObject();
    obj.put("error", bError);
    obj.put("mensaje", mensajeError);
    out.println(obj);

  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(cerrarSesion.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (JSONException ex) {
      Logger.getLogger(cerrarSesion.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }

}
