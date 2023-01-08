package datos;

import domain.Usuario;
import java.sql.*;
import java.util.*;

public class UsuarioDAO implements IAccesoDatos<Usuario> {

    private Connection conexionTransaccional;
    //Sentencias
    private static final String SQL_SELECT = "SELECT * FROM usuario";
    private static final String SQL_INSERT = "INSERT INTO usuario(usuario, password) VALUES(?, ?)";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE idUsuario = ?";
    private static final String SQL_UPDATE = "UPDATE usuario SET usuario = ?, password = ? WHERE idUsuario = ?";

    //Constructores
    public UsuarioDAO() {
    }

    public UsuarioDAO(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    //Métodos
    @Override
    public List<Usuario> listar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                usuario = new Usuario(rs.getInt("idUsuario"), rs.getString("usuario"), rs.getString("password"));
                usuarios.add(usuario);
            }

        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }

        }

        return usuarios;
    }

    @Override
    public int insertar(Usuario obj) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, obj.getUsuario());
            stmt.setString(2, obj.getPassword());

            registros = stmt.executeUpdate(); //modifica el estado de la base de datos (insert, update, delete)

        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }

        return registros;
    }

    @Override
    public int eliminar(Usuario obj) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0; //cantidad de registros modificados

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, obj.getIdUsuario());

            registro = stmt.executeUpdate(); //modifica el estado de la base de datos (insert, update, delete)

        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }

        return registro;
    }

    @Override
    public int actualizar(Usuario obj) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0; //cantidad de registros modificados

        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, obj.getUsuario());
            stmt.setString(2, obj.getPassword());
            stmt.setInt(3, obj.getIdUsuario());

            registro = stmt.executeUpdate(); //modifica el estado de la base de datos (insert, update, delete)

        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }

        return registro;
    }

}
