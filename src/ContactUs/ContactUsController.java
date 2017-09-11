package ContactUs;

/**
 * Created by hadi on 5/1/2017.
 */

import ChatMenu.ChatMenuController;
import Client.Client;
import FriendsMenu.FriendsMenuController;
import GamesMenu.GamesMenuController;
import MainMenu.MainMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactUsController implements Initializable{

    private Client client;
    public void setClient(Client client){
        this.client=client;
        username.setText(client.username);
    }

    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));
    public void initialize(URL url, ResourceBundle rb){
        ListView.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();
        try {
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private AnchorPane ap;

    @FXML
    private JFXButton GameButton;

    @FXML
    private JFXButton FriendsButton;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label> ListView;

    @FXML
    private Label message;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    void makeChatMenu(MouseEvent event) throws IOException{
        FXMLLoader  loader=new FXMLLoader(getClass().getResource("/ChatMenu/ChatMenu.fxml"));
        Parent ChatMenu_parent= loader.load();
        ChatMenuController ChatMenu=loader.getController();
        ChatMenu.setClient(client);
        Scene ChatMenu_scene= new Scene(ChatMenu_parent);
        Stage ChatMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ChatMenu_stage.setScene(ChatMenu_scene);
        ChatMenu_stage.show();
        BackgroundTask.stop();
        ChatMenu.makeRefresh();
    }

    @FXML
    void makeFriendsMenu(MouseEvent event) throws IOException {
        FXMLLoader  loader=new FXMLLoader(getClass().getResource("/FriendsMenu/FriendsMenu.fxml"));
        Parent FriendsMenu_parent= loader.load();
        FriendsMenuController FriendsMenu=loader.getController();
        FriendsMenu.setClient(client);
        Scene FriendsMenu_scene= new Scene(FriendsMenu_parent);
        Stage FriendsMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FriendsMenu_stage.setScene(FriendsMenu_scene);
        FriendsMenu_stage.show();
        BackgroundTask.stop();
        FriendsMenu.makeRefresh();
    }

    @FXML
    void makeGamesMenu(MouseEvent event) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/GamesMenu/GamesMenu.fxml"));
        Parent GamesMenu_parent= loader.load();
        GamesMenuController GamesMenu=loader.getController();
        GamesMenu.setClient(client);
        Scene GamesMenu_scene= new Scene(GamesMenu_parent);
        Stage GamesMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        GamesMenu_stage.setScene(GamesMenu_scene);
        GamesMenu_stage.show();
        BackgroundTask.stop();
        GamesMenu.makeRefresh();
    }


    @FXML
    void makeMenu(MouseEvent event) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/MainMenu/MainMenu.fxml"));
        Parent MainMenu_parent= loader.load();
        MainMenuController mainMenu=loader.getController();
        mainMenu.setClient(client);
        Scene MainMenu_scene= new Scene(MainMenu_parent);
        Stage MainMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        MainMenu_stage.setScene(MainMenu_scene);
        MainMenu_stage.show();
        BackgroundTask.stop();
        mainMenu.makeRefresh();
    }

    public void makeRefresh() {
        getOnlineFriends();
        getReqNotif();
        getMessageNotif();
        Stage stage=(Stage) ap.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
    }
    void logout(){
        client.logout();
    }
    void getOnlineFriends (){
        String result = client.getOnlineFriends();
        System.out.println(result);
        String[] resultString = result.split("//");
        if(isNumeric(resultString[0])) {

            Integer friendCount = Integer.valueOf(resultString[0]);
            ListView.getItems().clear();

            while (friendCount >= 1) {

                Label lbl = new Label("  "+resultString[friendCount]);
                lbl.setMaxSize(206, 42);
                lbl.setMinSize(206, 42);

                try {

                    lbl.setStyle("-fx-background-color: #282828; -fx-text-fill: White; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                    ImageView image = new ImageView(new Image(new FileInputStream("./images/useron.png")));
                    image.setPreserveRatio(true);
                    image.setFitWidth(30);
                    image.setFitHeight(30);
                    lbl.setGraphic(image);

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                ListView.getItems().add(lbl);
                friendCount--;
            }
        }

    }

    void getReqNotif(){
        String result=client.getReqNotif();

        String[] resultString = result.split("//");
        if (isNumeric(resultString[0])) {

            Integer friendCount = Integer.valueOf(resultString[0]);
            while (friendCount >= 1) {
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text(" "+resultString[friendCount] + " sent you a friend request.")
                        .graphic(null).hideAfter(Duration.seconds(10))
                        .darkStyle();
                Request.show();
                friendCount--;
            }
        }

    }
    void getMessageNotif(){
        String result =client.getMessageNotif();
        String[] resultString = result.split("//");
        if (isNumeric(resultString[0])) {

            Integer messageCount = Integer.valueOf(resultString[0]);
            while (messageCount >= 1) {
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text(" "+resultString[messageCount] + " sent you a message.")
                        .graphic(null).hideAfter(Duration.seconds(10))
                        .darkStyle();
                Request.show();
                messageCount--;
            }
        }

    }
    public static boolean isNumeric(String str) {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
