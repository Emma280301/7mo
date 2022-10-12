package application;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Limite extends Thread{
	
	Label tiquta, intento;
	Button boton;
	TextField correo;
	PasswordField contraseña;
	static int a;

    public Limite(Label contador, TextField correo, PasswordField contraseña, Button boton, Label intento) {
        this.tiquta = contador;
        this.correo = correo;
        this.contraseña = contraseña;
        this.boton = boton;
        this.intento = intento;
    }

    public void run(){
        try {
            int x = 0;
            while(LoginController.comienzo){
                Thread.sleep(1);
                ejercutarHilo(x);
                x++;
            }
        }catch (Exception e){
            System.out.println("Excepción en el hilo" + e.getMessage());
        }
   }

    private void ejercutarHilo(int x) {
       // System.out.println("" + x);

        if(LoginController.milisegundo == 0 && LoginController.segundo > 0){
        	LoginController.milisegundo = 999;
        	LoginController.segundo--;
            if(LoginController.segundo == 0 && LoginController.minuto > 0){
            	LoginController.segundo = 59;
            	LoginController.minuto--;
                if(LoginController.minuto == 0 && LoginController.hora > 0){
                	LoginController.minuto = 59;
                    LoginController.hora--;
                }
            }
        }
        if(LoginController.hora == 0 && LoginController.minuto == 0 && LoginController.segundo == 0 && LoginController.milisegundo == 0) {
        	LoginController.comienzo = false;
        	LoginController.falla = 0;
        	Platform.runLater(() -> correo.setEditable(true));
        	Platform.runLater(() -> contraseña.setEditable(true));
        	Platform.runLater(() -> boton.setDisable(false));
        	Platform.runLater(() -> intento.setText(null));
        	Platform.runLater(() -> tiquta.setVisible(false));
        	Platform.runLater(() -> correo.setText(null));
        	Platform.runLater(() -> contraseña.setText(null));
        	System.out.println(LoginController.falla +"--" + LoginController.comienzo);
        }else {
        	LoginController.milisegundo--;
        }

        String textomili="", textiseg="", textomin="", textohora="";
        if(LoginController.milisegundo< 10){
            textomili="00"+LoginController.milisegundo;
        }else if(LoginController.milisegundo<100){
            textomili="0"+LoginController.milisegundo;
        }else{
            textomili = ""+LoginController.milisegundo;
        }
        if(LoginController.segundo<10){
            textiseg="0"+LoginController.segundo;
        }else{
            textiseg=""+LoginController.segundo;
        }
        if(LoginController.minuto<10){
            textomin="0"+LoginController.minuto;
        }else{
            textomin=""+LoginController.minuto;
        }
        if(LoginController.hora<10){
            textohora="0"+LoginController.hora;
        }else{
            textohora=""+LoginController.hora;
        }

        String reloj = textohora + ":" + textomin + ":" + textiseg + ":" + textomili;
        Platform.runLater(() -> tiquta.setText(reloj));
    }
}
