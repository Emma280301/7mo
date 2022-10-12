package application;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import static com.google.firebase.cloud.FirestoreClient.getFirestore;

public class Conexion implements Serializable {

    static Firestore bd;

    public static void conectar() throws IOException {

        FileInputStream serviceAccount = new FileInputStream("integrador.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://integrador-295c7-default-rtdb.firebaseio.com/").build();

        FirebaseApp.initializeApp(options);
        bd = getFirestore();
        System.out.println("Conectado con exito");
    }

    public static boolean insertarDatos(String coleccion,String documento,
                                        Map<String, Object> data) {
        try {
            DocumentReference docRef = bd.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Actualizado: " + result.get().getUpdateTime());
            return true;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}