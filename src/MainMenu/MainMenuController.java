package MainMenu;

import ChatMenu.ChatMenuController;
import Client.Client;
import ContactUs.ContactUsController;
import FriendsMenu.FriendsMenuController;
import GamesMenu.GamesMenuController;
import PersonalInfo.PersonalInfoController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import javafx.scene.input.*;

import javax.swing.text.html.StyleSheet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable{
    private Client client;
    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));
    public void initialize(URL url, ResourceBundle rb){
        ListView.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        circle.setStyle("-fx-opacity: 0;");
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();
        try {
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
            logob.setImage(new Image(new FileInputStream("./images/bj-logo.png")));
            logot.setImage(new Image(new FileInputStream("./images/tictactoe.png")));

        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }
    public void setClient(Client client){
        this.client=client;
        username.setText(client.username);
        username1.setText(client.username+"!");
    }

    @FXML
    private JFXButton GameButton;

    @FXML
    private ImageView logot;

    @FXML
    private ImageView logob;

    @FXML
    private JFXButton FriendsButton;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    private Label username;

    @FXML
    private AnchorPane ap;

    @FXML
    private Label username1;

    @FXML
    private JFXListView<Label> ListView;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private ImageView defaultProfile;
    @FXML
    private Circle circle;

    @FXML
    private Label reqCount;

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
    private Pane profile;

    @FXML
    void makeContactUsMenu(MouseEvent event) throws IOException{
        FXMLLoader  loader=new FXMLLoader(getClass().getResource("/ContactUs/ContactUs.fxml"));
        Parent ContactUs_parent= loader.load();
        ContactUsController ContactUs=loader.getController();
        ContactUs.setClient(client);
        Scene ContactUs_scene= new Scene(ContactUs_parent);
        Stage ContactUs_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ContactUs_stage.setScene(ContactUs_scene);
        ContactUs_stage.show();
        BackgroundTask.stop();
        ContactUs.makeRefresh();
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
    public void makeRefresh() {
        getOnlineFriends();
        getReqNotif();
        getRequest();
        getMessageNotif();
        Stage stage=(Stage) ap.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
    }
    @FXML
    void makeProfile(MouseEvent event) throws IOException {
        FXMLLoader  loader=new FXMLLoader(getClass().getResource("/PersonalInfo/PersonalInfo.fxml"));
        Parent PersonalInfo_parent= loader.load();
        PersonalInfoController PersonalInfo=loader.getController();
        PersonalInfo.setClient(client);
        PersonalInfo.setTosee(username.getText());
        Scene PersonalInfo_scene= new Scene(PersonalInfo_parent);
        Stage PersonalInfo_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        PersonalInfo_stage.setScene(PersonalInfo_scene);
        PersonalInfo_stage.show();
        BackgroundTask.stop();
        PersonalInfo.makeRefresh();
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

    void getReqNotif() throws NullPointerException{
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
    void logout(){
        client.logout();
    }
    void getRequest(){
        String result = client.getRequest();
        String[] reqList = result.split("//");
        if(isNumeric(reqList[0])){
            if(Integer.valueOf(reqList[0])==0){
                circle.setStyle("-fx-opacity: 0;");
            }
            else {
                circle.setStyle("-fx-opacity: 1;");
                reqCount.setText(reqList[0]);
            }
        }
        else{
            circle.setStyle("-fx-opacity: 0;");
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

}