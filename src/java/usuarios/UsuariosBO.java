package usuarios;

import java.sql.SQLException;
import usuarios.modelos.Usuario;

public class UsuariosBO {

  private UsuariosDAO DB;

  public UsuariosBO() throws ClassNotFoundException, SQLException {
    DB = new UsuariosDAO();
  }

  public Usuario autenticar(String usuario, String clave) 
          throws SQLException, ClassNotFoundException {
    Usuario user = new Usuario(usuario, clave);
    return DB.autenticar(user);
  }

}
