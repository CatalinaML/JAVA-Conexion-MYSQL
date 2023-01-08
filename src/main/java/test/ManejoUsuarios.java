package test;

import datos.Conexion;
import datos.UsuarioDAO;
import domain.Usuario;
import java.sql.*;

public class ManejoUsuarios {

    public static void main(String[] args) {
        
        Connection conexion = null;

        try {
            conexion = Conexion.getConnection();

            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            UsuarioDAO usuarioDao = new UsuarioDAO(conexion);
            
            Usuario usuarioNuevo = new Usuario("unUsuario", "contaseña");
            usuarioDao.insertar(usuarioNuevo);
            
            Usuario modificar = new Usuario(4, "maria magdalena necesito que esto entre al rollback por favor", "unaContraseña");
            usuarioDao.actualizar(modificar);
            
            conexion.commit();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            
            try {
                conexion.rollback();//todas las sentencias q se modificaron en la bd no se ejecutan si hay un error
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }
}
