package usuarios;

import general.ConexionMysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import usuarios.modelos.Usuario;

public class UsuariosDAO {

  private final ConexionMysql con;

  public UsuariosDAO() throws ClassNotFoundException, SQLException {
    con = new ConexionMysql();
  }

  private String sAutenticar = " select id,usuario,nombres,apellidos,mail "
          + " from usuarios "
          + " where usuario = ? "
          + " and clave=md5(?) "
          + " and status='A' ";

  public Usuario autenticar(Usuario usr) throws SQLException, ClassNotFoundException {
    Usuario u = null;
    PreparedStatement ps;
    con.conectar();
    ps = con.prepareStatement(sAutenticar);
    ps.setString(1, usr.getUsuario().trim().toUpperCase());
    ps.setString(2, usr.getClave().trim());
    ResultSet rs;
    rs = ps.executeQuery();
    if (rs.next()) {
      u = new Usuario(rs.getInt("id"),
              rs.getString("usuario"),
              rs.getString("nombres"),
              rs.getString("apellidos"),
              rs.getString("mail"));
    }
    rs.close();
    con.cerrar();
    return u;
  }
}
