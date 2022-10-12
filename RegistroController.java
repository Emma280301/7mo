package application;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroController {
	 
	@FXML private TextField txtedad;
	@FXML private TextField txtemail;
	@FXML private TextField txtmaterno;
	@FXML private TextField txtname;
	@FXML private PasswordField txtpassword;
	@FXML private PasswordField txtpassword2;
	@FXML private TextField txtpaterno;
	@FXML private TextField txttelefono;
	@FXML private TextField txtuser;
	
	private static BD mBd;
	private static cifrados cifrar;
	
	public void regresar(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
    	Scene scene = new Scene(fxmlLoader.load(), 400, 400);
    	Stage stage = new Stage();
    	stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.setTitle("Invernadero");
    	stage.setScene(scene);
    	stage.setX(400);
    	stage.setY(150);
    	stage.show();
	}
	
	public void nuevoRegistro(ActionEvent event) throws IOException {
		mBd = new BD("login", "root", "");
		cifrar = new cifrados();
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(txtemail.getText());
        
        System.out.println("Contraseña " + txtpassword.getText().toString());
        String pwd = new String(txtpassword.getText());
        System.out.println("Cifrado: " + cifrar.cifradomd5(pwd));
        
		if (!txtname.getText().trim().equals("") && !txtmaterno.getText().trim().equals("") && !txtpaterno.getText().trim().equals("")
                && !txtemail.getText().trim().equals("") && !txtuser.getText().trim().equals("") && !txttelefono.getText().trim().equals("")
                && !txtpassword.getText().trim().equals("") && !txtpassword2.getText().trim().equals("")) {
            if (mather.find() == true) {
                if (txtpassword.getText().trim().equals(txtpassword2.getText().trim())) {
                    if (txttelefono.getText().length() == 10) {
                        usuario mUsuario = new usuario();
                        mUsuario.setNombre(txtname.getText().trim());
                        mUsuario.setApellido_materno(txtmaterno.getText().trim());
                        mUsuario.setApellido_paterno(txtpaterno.getText().trim());
                        mUsuario.setContrasena(cifrar.cifradomd5(txtpassword.getText().toString()));
                        mUsuario.setTelefono(txttelefono.getText().trim());
                        mUsuario.setUsuario(txtuser.getText().trim());
                        mUsuario.setCorreo(txtemail.getText().trim());
                        
                        if (mBd.Conectar()) {
                            if (mBd.AddUser(mUsuario)) {
                                JOptionPane.showMessageDialog(null, "Usuario Registrado");
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("vista.fxml"));
                            	Scene scene = new Scene(fxmlLoader.load(), 1397, 708);
                            	Stage stage = new Stage();
                            	stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            	stage.setTitle("Invernadero");
                            	stage.setScene(scene);
                            	stage.setX(0);
                            	stage.setY(0);
                            	stage.show();
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "Error al conectar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Número de telefono inválido");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "las contraseñas no coinciden");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El correo es inválido");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Llena todos los campos");
        }
	
	}
}
