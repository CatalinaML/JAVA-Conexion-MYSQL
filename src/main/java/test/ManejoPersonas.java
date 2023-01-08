package test;

import datos.*;
import domain.Persona;
import java.sql.*;

public class ManejoPersonas {

    public static void main(String[] args) {

        //todo esto es para poder hacer commits y rollbacks
        //que las conexiones no se abran y cierren continuamente
        //esto sirve para ejecutar sentencias continuas
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }

            PersonaDAO personaDao = new PersonaDAO(conexion);

            Persona cambio = new Persona(1, "nomeimporta", "siguesinimportar", "alguno", "untel");
            personaDao.actualizar(cambio);
            
            Persona nueva = new Persona("aaaa", "bbbbb", "unemail", "algun teelefono");
            personaDao.insertar(nueva);
            
            conexion.commit(); //se guardan los cambios
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
