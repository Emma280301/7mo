package application;
	
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Iniciar Sesión");
        stage.setScene(scene);
        stage.setX(400);
        stage.setY(150);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
    	Conexion.conectar();
        launch();
    }
    
	
	
	
	
	
	
	
	
	
	
	
/*	
	@Override
	public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("vista.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("Invernadero");
        stage.setScene(scene);
        stage.show();
    }
	
	public static void main(String[] args) throws IOException {
		Conexion.conectar();
		launch();
		
	}
	
	*
	*
	*
	*
	*
	*
	*
	*@Override
    public void start(Stage stage) throws IOException {
    	scene = new Scene(loadFXML("Login"), 400, 400);
        stage.setTitle("Invernadero"); 
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));;    	
        return fxmlLoader.load();
    }
	*/
}
