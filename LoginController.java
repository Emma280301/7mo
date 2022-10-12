package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML private TextField correo;
	@FXML private PasswordField contraseña;
	@FXML private Label error;
    @FXML private Label contador;
	@FXML private Label intento;
	@FXML private Button boton;

	@FXML static String nombre, paterno, materno, usuario = "", email, c1, c2;
	public static BD mBd;
	@FXML private cifrados cifrado;
	@FXML static int falla = 0, aumento;
	@FXML static int hora = 0, minuto = 0, segundo = 0, milisegundo = 0;
	@FXML static boolean comienzo;
	
	
	@FXML
	public void registrarse(ActionEvent event) throws IOException {
		 FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Registro.fxml"));
    	 Scene scene = new Scene(fxmlLoader.load(), 900, 430);
    	 Stage stage = new Stage();
    	 stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	 stage.setTitle("Invernadero");
    	 stage.setScene(scene);
    	 stage.setX(150);
    	 stage.setY(150);
    	 stage.show();
	}
	
	@FXML
	public void IniciarSesion(ActionEvent event) throws IOException {
		 cifrados cifrado = new cifrados();
		 mBd = new BD("login", "root", "");
	     usuario mUsuario = new usuario();
     	 String Pasword = "";
     	 System.out.println(correo.getText() + "-" + contraseña.getText());
         if (!correo.getText().trim().equals("") && !contraseña.getText().trim().equals("")) {
        	 System.out.println(mBd.Conectar() + "");
              if (mBd.Conectar()) {
                  mUsuario = mBd.GetUsuario(correo.getText().trim());
                  /*user = correo.getText();
                  Pasword = cifrado.cifradomd5(pwd.trim());
                  Pasword2 = cifrado.descifradomd5(Pasword);*/
                  try {
                 	 //String user = (String) correo.getText();
                	  usuario = (String) correo.getText();
                      String pwd = new String(contraseña.getText());
                      Pasword = cifrado.cifradomd5(pwd.trim());
                      String SQL = "SELECT usuario FROM usuario WHERE usuario = '"+usuario+"' AND contrasena = '"+Pasword+"'";
                      mBd.mResultSet =  mBd.mStatement.executeQuery(SQL);
                      if (mBd.mResultSet.next()) {
                     	 JOptionPane.showMessageDialog(null, "Bienvenido al sistema");
                     	 FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("vista.fxml"));
                     	 Scene scene = new Scene(fxmlLoader.load(), 1355, 708);
                     	 Stage stage = new Stage();
                     	 stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                     	 stage.setTitle("Invernadero");
                     	 stage.setScene(scene);
                     	 stage.setX(0);
                     	 stage.setY(0);
                     	 stage.show();
                      }else {
                         error.setText( "El usuario y/o contraseña es incorrecta");
                         falla++;
                     	correo.setText(null);
                       	contraseña.setText(null);
                         //System.out.println(falla);
                         /*System.out.println(Pasword + "\n" +correo.getText());
                         System.out.println(Pasword2);*/
                     }
                      if(falla == 3) {
                       	control();
                       	JOptionPane.showMessageDialog(null, "Limite de Intentos Superados");
                       	intento.setText("Reintentalo en: ");	
                       	correo.setEditable(false);
                       	contraseña.setEditable(false);
                       	correo.setText(null);
                       	contraseña.setText(null);
                       	boton.setDisable(true);
                       	contador.setVisible(true);
                       	comienzo = true;
                       	Limite lim = new Limite(contador, correo, contraseña, boton, intento);
                       	error.setText(null);
                       	lim.start();
                       }
                  }catch(SQLException e) {
                 	 JOptionPane.showMessageDialog(null, e.getMessage());
                  }
              }else{
                 JOptionPane.showMessageDialog(null, "Error al conectar");
          }
          }else{
             JOptionPane.showMessageDialog(null, "Ingrese el usuario y contraseña");
     }
	}
	
	public static void control() {
    	aumento += 5;
    	segundo += aumento;
    	
    }
}
