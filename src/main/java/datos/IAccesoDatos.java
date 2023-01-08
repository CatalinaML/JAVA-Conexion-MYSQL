
package datos;

import java.sql.SQLException;
import java.util.*;

public interface IAccesoDatos<E> {
    
    public List<E> listar() throws SQLException;
    public int insertar(E obj)throws SQLException;
    public int eliminar(E obj)throws SQLException;
    public int actualizar(E obj)throws SQLException;
}
