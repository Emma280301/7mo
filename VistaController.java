package application;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Table;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class VistaController {
	@FXML public Label lblUsuario;
	@FXML public Label lblTemperatura, lblFechaTemperatura, lblHoraTemperatura, lblAmbiental, lblSuelo, T, A, S;
	@FXML static double valorInicialT;
	@FXML static int valorInicialA, valorInicialS;
	@FXML public static boolean empezar;
	@FXML public static TableView <Datos> table;
	@FXML public static TableColumn <Datos, String> Temperatura;
	@FXML public static TableColumn <Datos, String> Fecha;
	@FXML public static TableColumn <Datos, String> Hora;
	@FXML public LineChart<String, Double> lineChart;
	@FXML public LineChart<String, Double> lineChart2;
	@FXML public LineChart<String, Double> lineChart3;
	@FXML public LineChart<String, Double> lineChart4;
	@FXML public CategoryAxis x;
	@FXML public NumberAxis y;
	@FXML private ScrollPane sb;
	@FXML private Pane pane1, pane2, pane3, pane4;

	
	@FXML public void iniciarTemperatura(){
        empezar = true;
		sb.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Hilo hilo = new Hilo(lblTemperatura, lblFechaTemperatura, lblHoraTemperatura, lineChart, lblAmbiental, lblSuelo, lineChart2, lineChart3, lineChart4);
        hilo.start();
    }
	
	@FXML public  void pausarTemperatura(){
        empezar = false;
        sb.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        System.out.println(empezar);
    }
	
	@FXML public void vistaGeneral() {
		pane1.setVisible(true);
        pane2.setVisible(false);
        pane3.setVisible(false);
        pane4.setVisible(false);
        lblTemperatura.setVisible(true);
        lblAmbiental.setVisible(true);
        lblSuelo.setVisible(true);
        T.setVisible(true);
        A.setVisible(true);
        S.setVisible(true);
	}
	
	@FXML public void vistaTemperatura() {
		pane1.setVisible(false);
        pane2.setVisible(true);
        pane3.setVisible(false);
        pane4.setVisible(false);
        lblTemperatura.setVisible(true);
        lblAmbiental.setVisible(false);
        lblSuelo.setVisible(false);
        T.setVisible(true);
        A.setVisible(false);
        S.setVisible(false);
	}
	
	@FXML public void vistaAmbiental() {
		pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(true);
        pane4.setVisible(false);
        lblTemperatura.setVisible(false);
        lblAmbiental.setVisible(true);
        lblSuelo.setVisible(false);
        T.setVisible(false);
        A.setVisible(true);
        S.setVisible(false);
	}

	@FXML public void vistaSuelo() {
		pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        pane4.setVisible(true);
        lblTemperatura.setVisible(false);
        lblAmbiental.setVisible(false);
        lblSuelo.setVisible(true);
        T.setVisible(false);
        A.setVisible(false);
        S.setVisible(true);
	}

	@FXML public void vistaSalir(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
    	Scene scene = new Scene(fxmlLoader.load(), 400, 400);
    	Stage stage = new Stage();
    	stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.setTitle("Invernadero");
    	stage.setScene(scene);
    	stage.setX(400);
    	stage.setY(150);
    	empezar = false;
    	stage.show();
	}

}
