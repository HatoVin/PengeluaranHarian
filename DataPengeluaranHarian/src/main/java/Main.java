
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author ASUS
 */
public class Main extends Application{

    public static void main(String[] args) {
    launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File ("src/main/java/View/homePage.fxml").toURI().toURL();
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.setTitle("Data Pengeluaran");
        stage.show();
    }
}
