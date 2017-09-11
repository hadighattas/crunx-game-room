package SplashScreen; /**
 * Created by hadi on 4/30/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.FileInputStream;
import java.io.IOException;

public class SplashScreen extends Application {

    public static void main(String[] args) {
        launch(args);

    }
    @Override
    public void start(Stage primaryStage) throws IOException{
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
        Parent root = loader.load();
        Scene splashScreen=new Scene(root);
        primaryStage.getIcons().add(new Image(new FileInputStream("./images/logo.png")));
        primaryStage.setAlwaysOnTop(true);
        splashScreen.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - 636) / 2);
        primaryStage.setY((screenBounds.getHeight() - 209) / 2);
        primaryStage.setScene(splashScreen);
        primaryStage.show();



    }

}
