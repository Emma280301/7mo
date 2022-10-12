package application;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class Hilo extends Thread{

    public Label temperatura, fecha, hora, ambiental, suelo;
    static double valorEnteroT;
    static double VminT = 23.9, VmaxT = 32.01, plusT, lessT;
    static int valorAmbiental, valorSuelo, VminS = 14, VmaxS = 22, VminA = 40, VmaxA = 60, plusA, lessA, plusS, lessS;
    public static LineChart<String, Double> linechart;
    public static LineChart<String, Double> linechart2;
    public static LineChart<String, Double> linechart3;
    public static LineChart<String, Double> linechart4;

    public Hilo(Label lblTemperatura, Label lblFechaTemperatura, Label lblHoraTemperatura, LineChart<String, Double> lineChart, Label lblAmbiental,
    		Label lblSuelo, LineChart<String, Double> lineChart2, LineChart<String, Double> lineChart3, LineChart<String, Double> lineChart4) {
        this.temperatura = lblTemperatura;
        this.fecha = lblFechaTemperatura;
        this.hora = lblHoraTemperatura;
        this.linechart = lineChart;
        this.ambiental = lblAmbiental;
        this.suelo = lblSuelo;
        this.linechart2 = lineChart2;
        this.linechart3 = lineChart3;
        this.linechart4 = lineChart4;
    }

    public  void run(){
        ejecutarHilo();
    }

    public void ejecutarHilo() {
    	Random random = new Random();
    	valorEnteroT =((int) random.nextInt((int) ((VmaxT - VminT) + 1)) + VminT);
    	valorSuelo = ((int) random.nextInt((int) ((VmaxS - VminS) + 1)) + VminS);
		valorAmbiental =((int) random.nextInt((int) ((VmaxA - VminA) + 1)) + VminA);
    	plusT = 0;
    	lessT = 0;
    	plusA = 0;
		lessA = 0;
    	plusS = 0;
    	lessS = 0;
       while (VistaController.empezar) {
            if(plusT <= 2 && lessT >= -2) {
            	plusT += 0.01;
                lessT -= 0.01;
            }else if(plusT >= 2 && lessT <= -2){
            	valorEnteroT =((int) random.nextInt((int) ((VmaxT - VminT) + 1)) + VminT);
            	plusT = 0;
            	lessT = 0;
            }
            if(plusA <= 5 && lessA >= -5) {
            	plusA += 1;
                lessA -= 1;
            }else if(plusA >= 5 && lessA <= -5){
            	valorAmbiental =((int) random.nextInt((int) ((VmaxA - VminA) + 1)) + VminA);
            	plusA = 0;
            	lessA = 0;
            }
            if(plusS <= 5 && lessS >= -5) {
            	plusS += 1;
                lessS -= 1;
            }else if(plusS >= 5 && lessS <= -5){
            	valorSuelo =((int) random.nextInt((int) ((VmaxS - VminS) + 1)) + VminS);
            	plusS = 0;
            	lessS = 0;
            }
            
            VistaController.valorInicialT = lessT + (plusT - lessT) * random.nextDouble();
            DecimalFormat df = new DecimalFormat("#.00");
            Double Temperatura = valorEnteroT + VistaController.valorInicialT;
            String temp = String.valueOf( df.format(Temperatura));
            
            
            VistaController.valorInicialA = (int) (lessA + (plusA - lessA) * ((random.nextInt((int) ((1 - 0) + 1)) + 0)));
            Integer Ambiental = (valorAmbiental + VistaController.valorInicialA);
            String Amb = String.valueOf(Ambiental);
            
            VistaController.valorInicialS= (int) (lessS + (plusS - lessS) * ((random.nextInt((int) ((1 - 0) + 1)) + 0)));
            Integer Suelo = (int) (valorSuelo + VistaController.valorInicialS);
            String Sue = String.valueOf(Suelo);
            System.out.println("T: " + temp + "-" + "A: "+ Amb + "-" + "S: " + Suelo);
            
            Map<String, Object> data = new HashMap<>();
            data.put("Temperatura", temp);
            data.put("Humedad Ambiental", Amb);
            data.put("Humedad del Suelo", Sue);
            data.put("Fecha", getDateT());
            data.put("Hora", getHourT());
            String uuid = java.util.UUID.randomUUID().toString();
            System.out.println(Conexion.insertarDatos("Invernadero", uuid, data));
            
            try {
                Platform.runLater(() -> this.temperatura.setText(temp + " °C"));
                Platform.runLater(() -> this.ambiental.setText(Ambiental + " %"));
                Platform.runLater(() -> this.suelo.setText(Suelo + " %"));
                Platform.runLater(() -> this.fecha.setText(getDateT() + " : "));
                Platform.runLater(() -> this.hora.setText(getHourT()));
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Datos dat = new Datos(linechart, linechart2, linechart3, linechart4);
    		//dat.mostrarDatos();
    		try {
    			dat.buscarDatos();
    		} catch (InterruptedException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} catch (ExecutionException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
       
    }
    
    public String getDateT( ){ 
    	String pattern = "dd-MM-yyyy";
    	SimpleDateFormat obj = new SimpleDateFormat(pattern);
    	
    	String date = obj.format(new Date());
    	return date;
    }
    
    public String getHourT( ){ 
    	String pattern = "HH:mm:ss";
    	SimpleDateFormat obj = new SimpleDateFormat(pattern);
    	
    	String date = obj.format(new Date());
    	return date;
    }

}
