package SplashScreen;

import Login.Login;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by hadi on 4/30/2017.
 */
public class SplashScreenController implements Initializable{

    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<javafx.event.ActionEvent>() {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            makeApp();
            BackgroundTask.stop();
        }
    }));
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        try {
            image.setImage(new Image(new FileInputStream("./src/CRUNX.png")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();

    }

    void makeApp() {
        Stage stage = (Stage) ap.getScene().getWindow();

        Login login = new Login();
        try {
            login.start(Login.classStage);
            stage.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private ImageView image;
    @FXML
    private AnchorPane ap;


}
