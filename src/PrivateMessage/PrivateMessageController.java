package PrivateMessage;

import Client.Client;
import MainMenu.MainMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrivateMessageController implements Initializable{
    private Client client;
    private String user2;
    public void setClient(Client client){
        this.client=client;
        username.setText(client.username);
    }
    public void setClient2(String user2){
        this.user2=user2;
        friend1.setText(user2);
    }
    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));

    public void initialize(URL url, ResourceBundle rb) {
        conv.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        ListView.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");

        try {
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
            home.setImage(new Image(new FileInputStream("./images/homebutton.png")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();

    }
    @FXML
    private ImageView home;

    @FXML
    private AnchorPane ap;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label> ListView;

    @FXML
    private JFXListView<Label> conv;

    @FXML
    private JFXButton sendMessage;

    @FXML
    private JFXTextField message;

    @FXML
    private Label friend1;

    @FXML
    void sendMessage(ActionEvent event) {
        String msg=message.getText();
        message.clear();
        client.sendMessage(user2, msg);
        getMessages();
    }
    @FXML
    void sendMessageEnter(ActionEvent event) {
        String msg=message.getText();
        message.clear();
        client.sendMessage(user2, msg);
        getMessages();
    }

    @FXML
    void makeMenu(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu/MainMenu.fxml"));
            Parent MainMenu_parent = loader.load();
            MainMenuController mainMenu = loader.getController();
            mainMenu.setClient(client);
            Scene MainMenu_scene = new Scene(MainMenu_parent);
            Stage MainMenu_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            MainMenu_stage.setScene(MainMenu_scene);
            MainMenu_stage.show();
            BackgroundTask.stop();
            mainMenu.makeRefresh();
    }

    public void makeRefresh() {
        getOnlineFriends();
        getReqNotif();
        getMessages();
        getMessageNotif();
        Stage stage=(Stage) ap.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
    }
    void getOnlineFriends (){
        String result = client.getOnlineFriends();
        String[] onFriendList = result.split("//");
        Integer friendCount = Integer.valueOf(onFriendList[0]);
        ListView.getItems().clear();
        if(isNumeric(onFriendList[0])){
            while (friendCount >= 1) {

                Label lbl = new Label("  " + onFriendList[friendCount]);
                lbl.setMaxSize(206, 42);
                lbl.setMinSize(206, 42);

                try {

                    lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
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
    void logout(){
        client.logout();
    }
    void getMessages(){
       String s=client.getMessages(user2);
       try {
           if (!s.equals("NO")) {
               conv.getItems().clear();
               String[] messages = s.split("//");
               Integer msgnumb = Integer.parseInt(messages[0]);
               Integer msgnum = msgnumb;
               Integer i=0;
               while (i<msgnumb) {

                   String[] message = messages[i+1].split("--");
                   if (message[0].equals(client.username)) {
                       Label lbl = new Label("  " + message[1]);
                       lbl.setMaxSize(480, 40);
                       lbl.setMinSize(480, 30);
                       lbl.setAlignment(Pos.BASELINE_RIGHT);
                       lbl.setStyle("-fx-background-color: #282828; -fx-text-fill: #ffffff; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                       conv.getItems().add(lbl);
                   } else {
                       Label lbl = new Label("  " + message[1]);
                       lbl.setMaxSize(480, 40);
                       lbl.setMinSize(480, 30);
                       lbl.setAlignment(Pos.BASELINE_LEFT);
                       lbl.setStyle("-fx-background-color: #585858; -fx-text-fill: #ffffff; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                       conv.getItems().add(lbl);

                   }
                   i++;


               }
               conv.scrollTo(conv.getItems().size()-1);

           }
       }catch (NullPointerException e){
           conv.getItems().clear();
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