package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BD {

    private static Connection mConection;
    public static Statement mStatement;
    public static ResultSet mResultSet;
    private final String bd;
    private final String user;
    private final String password;

    public BD(String bd, String user, String password) {
        mConection = null;
        mStatement = null;
        mResultSet = null;
        this.bd = bd;
        this.user = user;
        this.password = password;
    }

    public boolean Conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            mConection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/" + bd, user, password);
            return mConection != null;
        } catch (Exception e) {
        	System.out.println("Status: " + mConection);
        	e.printStackTrace();
            return false;
        }
    }
    
    public void Desconectar() {
        try {
            this.mConection.close();
        } catch (Exception e) {
        }
    }
 
    
    public Boolean AddUser(usuario mUsuario) {
          try {
            mStatement = mConection.createStatement();
            mStatement.execute("INSERT INTO usuario (nombre, apellido_paterno, apellido_materno, telefono, correo, usuario, contrasena) "
            + " VALUES ('" + mUsuario.getNombre() + "', '" + mUsuario.getApellido_paterno()+ "','" + mUsuario.getApellido_materno(
            )+ "','" + mUsuario.getTelefono()+ "','" + mUsuario.getCorreo()+ "','" + mUsuario.getUsuario()+ "','" + mUsuario.getContrasena()+ "')");
            return true;
        } catch (SQLException e) {
            System.err.println(e.toString());
            return false;
        }
    }
    
    public usuario GetUsuario(String usuario) {
        usuario mUsuario = null;
         try {
            mStatement = mConection.createStatement();
            mResultSet = mStatement.executeQuery("SELECT * FROM usuario WHERE usuario = '" + usuario + "' ");
            while (mResultSet.next()){
                mUsuario = new usuario();
                mUsuario.setUsuario_id(mResultSet.getInt("id_usuario"));
                mUsuario.setNombre(mResultSet.getString("nombre"));
                mUsuario.setApellido_paterno(mResultSet.getString("apellido_paterno"));
                mUsuario.setApellido_materno(mResultSet.getString("apellido_materno"));
                mUsuario.setUsuario(mResultSet.getString("usuario"));
                mUsuario.setContrasena(mResultSet.getString("contrasena"));
                mUsuario.setCorreo(mResultSet.getString("correo"));
                mUsuario.setTelefono(mResultSet.getString("telefono"));
                return mUsuario;
            }
        } catch (SQLException e) {
            return null;
        }
        return mUsuario;
    } 
    

}
