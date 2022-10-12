package application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.common.collect.Table;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Datos {
	public static int wd = 1300; 
	public static LineChart<String, Double> linechart;
	public static LineChart<String, Double> linechart2;
	public static LineChart<String, Double> linechart3;
	public static LineChart<String, Double> linechart4;
	
	public Datos(LineChart<String, Double> lineChart, LineChart<String, Double> lineChart2, LineChart<String, Double> lineChart3, LineChart<String, Double> lineChart4) {
		this.linechart = lineChart;
		this.linechart2 = lineChart2;
		this.linechart3 = lineChart3;
		this.linechart4 = lineChart4;
	}
	
	List<recuperarDatos> listaDatos = new ArrayList<recuperarDatos>();
	
	public void buscarDatos() throws InterruptedException, ExecutionException{
		//CollectionReference ddatos = Conexion.bd.collection("Invernadero");
		Query ddatos = Conexion.bd.collection("Invernadero").orderBy("Fecha").orderBy("Hora");
		ApiFuture<QuerySnapshot> querySnapshot = ddatos.get();
		
		for(DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			recuperarDatos recuperar = new recuperarDatos(document.getId(), document.getString("Temperatura"),
					document.getString("Humedad Ambiental"),  
					document.getString("Humedad del Suelo"),document.getString("Fecha"), document.getString("Hora"));
			listaDatos.add(recuperar);
		}
		mostrarDatos2();
	}
	
	public void mostrarDatos2() {
		XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
		XYChart.Series<String, Double> series2 = new XYChart.Series<String, Double>();
		XYChart.Series<String, Double> series3 = new XYChart.Series<String, Double>();
		XYChart.Series<String, Double> series4 = new XYChart.Series<String, Double>();
		XYChart.Series<String, Double> series5 = new XYChart.Series<String, Double>();
		XYChart.Series<String, Double> series6 = new XYChart.Series<String, Double>();
		
		for(int posD = 0; posD < listaDatos.size(); posD++) {
			
			double temperatura = Double.parseDouble(listaDatos.get(posD).getTemperatura());
			double ambiente = Double.parseDouble(listaDatos.get(posD).getAmbiente());
			double suelo = Double.parseDouble(listaDatos.get(posD).getSuelo());
			String hora = String.valueOf(listaDatos.get(posD).getFecha() + listaDatos.get(posD).getHora());
			
			series.getData().add(new XYChart.Data( hora, temperatura));
			series2.getData().add(new XYChart.Data( hora, ambiente));
			series3.getData().add(new XYChart.Data( hora, suelo));
			series4.getData().add(new XYChart.Data( hora, temperatura));
			series5.getData().add(new XYChart.Data( hora, ambiente));
			series6.getData().add(new XYChart.Data( hora, suelo));

			series.setName("T");
			series2.setName("A");
			series3.setName("S");
			series4.setName("T");
			series5.setName("A");
			series6.setName("S");
			
		}
		wd += 70;
		linechart.setPrefWidth(wd);
		linechart2.setPrefWidth(wd);
		linechart3.setPrefWidth(wd);
		linechart4.setPrefWidth(wd);
		Platform.runLater(() -> linechart.getData().addAll(series, series2, series3));
		Platform.runLater(() -> linechart2.getData().addAll(series4));
		Platform.runLater(() -> linechart3.getData().addAll(series5));
		Platform.runLater(() -> linechart4.getData().addAll(series6));
	}
	
	
	/*public void mostrarDatos() {
		agregarFilas(VistaController.table, 0);
		for(int posD = 0; posD < listaDatos.size(); posD++) {
			agregarFilas(VistaController.table, VistaController.table.getRowCount() + 1);
			VistaController.table.setValueAt(listaDatos.get(posD).getTemperatura(), posD, 0);
			VistaController.table.setValueAt(listaDatos.get(posD).getFecha(), posD, 1);
			VistaController.table.setValueAt(listaDatos.get(posD).getHora(), posD, 2);
		}
	}
	
	public void agregarFilas(TableView table, int filas) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(filas);
	}*/
	
}