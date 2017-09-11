package Login;

import FriendsMenu.FriendsMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image ;
import java.io.FileInputStream;

public class Login extends Application {
//    public FXMLLoader loader;
    public static Stage classStage = new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception{
        classStage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("CRUNX");
        primaryStage.getIcons().add(new Image(new FileInputStream("./images/logo.png")));
        primaryStage.setScene(new Scene(root, 1280,720));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }
}
