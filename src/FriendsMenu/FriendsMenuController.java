package FriendsMenu;

/**
 * Created by hadi on 4/30/2017.
 */

import ChatMenu.ChatMenuController;
import Client.Client;
import ContactUs.ContactUsController;
import GamesMenu.GamesMenuController;
import MainMenu.MainMenuController;
import PersonalInfo.PersonalInfoController;
import SearchMenu.SearchMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FriendsMenuController implements Initializable {
    private Client client;
    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));

    public void initialize(URL url, ResourceBundle rb) {
        ListView.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        allFriends.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        allFriendRequest.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        try {
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();
    }

    public void setClient(Client client) {
        this.client = client;
        username.setText(client.username);
    }

    @FXML
    private JFXButton GameButton;

    @FXML
    private JFXButton FriendsButton;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label> ListView;

    @FXML
    private JFXTextField toAdd;

    @FXML
    private JFXButton AddFriend;

    @FXML
    private JFXListView<Label> allFriends;

    @FXML
    private Label message;

    @FXML
    private JFXListView<Label> allFriendRequest;

    @FXML
    private JFXButton acceptButton;

    @FXML
    private JFXButton declineButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private JFXButton searchUsers;

    @FXML
    void makeAcceptFriend(ActionEvent event) {
        try {
            String added = allFriendRequest.getSelectionModel().getSelectedItem().getText();
            if (!added.equals("No friend requests")) {
                String result = client.addFriend(added);
                if (result.equals("Success")) {
                    Notifications addFriend = Notifications.create()
                            .title("  CRUNX").text("\n You are now Friends with " + added)
                            .graphic(null).hideAfter(Duration.seconds(10))
                            .darkStyle();
                    addFriend.show();
                    allFriendRequest.getItems().remove(allFriendRequest.getSelectionModel().getSelectedItem());
                }
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    void makeDeclineFriend(ActionEvent event) {
        String declined = allFriendRequest.getSelectionModel().getSelectedItem().getText();
        String result = client.declineFriend(declined);
        if (result.equals("Success")) {
            allFriendRequest.getItems().remove(allFriendRequest.getSelectionModel().getSelectedItem());
        }
    }


    public void makeRefresh() {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
        getRequest();
        getMessageNotif();
        getOnlineFriends();
        getFriends();
        getReqNotif();
    }

    @FXML
    void makeFriendAdd(ActionEvent event) {
        String result = client.sendFriendRequest(toAdd.getText());
        message.setText(result);
    }
    boolean switchingScenes=false;
    @FXML
    void makeMenu(MouseEvent event) throws IOException {
        if(!switchingScenes) {
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
    }

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
    void makeSearchMenu(ActionEvent event) throws IOException{
        switchingScenes=true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SearchMenu/SearchMenu.fxml"));
        Parent SearchMenu_parent = loader.load();
        SearchMenuController SearchMenu = loader.getController();
        SearchMenu.setClient(client);
        Scene SearchMenu_scene = new Scene(SearchMenu_parent);
        Stage SearchMenu_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SearchMenu_stage.setScene(SearchMenu_scene);
        SearchMenu_stage.show();
        BackgroundTask.stop();
        SearchMenu.makeRefresh();

    }
    @FXML
    void makeFriendProfile(MouseEvent event) throws IOException{
        switchingScenes=true;
        FXMLLoader  loader=new FXMLLoader(getClass().getResource("/PersonalInfo/PersonalInfo.fxml"));
        Parent PersonalInfo_parent= loader.load();
        PersonalInfoController PersonalInfo=loader.getController();
        PersonalInfo.setClient(client);
        PersonalInfo.setTosee(allFriends.getSelectionModel().getSelectedItem().getText());
        Scene PersonalInfo_scene= new Scene(PersonalInfo_parent);
        Stage PersonalInfo_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        PersonalInfo_stage.setScene(PersonalInfo_scene);
        PersonalInfo_stage.show();
        BackgroundTask.stop();
        PersonalInfo.makeRefresh();

    }

    void getFriends() {
        String result = client.getFriends();
        String[] friendList = result.split("//");
        Integer friendCount = Integer.valueOf(friendList[0]);
        allFriends.getItems().clear();
        while (friendCount >= 1) {

            Label lbl = new Label(friendList[friendCount]);
            lbl.setMaxSize(120, 200);
            lbl.setMinSize(120, 200);

            try {
                lbl.setStyle("-fx-background-color: #282828; -fx-text-fill: #ffffff; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                ImageView image = new ImageView(new Image(new FileInputStream("./images/userfriend.png")));
                image.setPreserveRatio(true);
                image.setFitWidth(40);
                image.setFitHeight(40);
                lbl.setAlignment(Pos.CENTER);
                lbl.setGraphic(image);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            allFriends.getItems().add(lbl);
            friendCount--;
        }

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

                    lbl.setStyle("-fx-background-color: #282828; -fx-text-fill: #ffffff; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
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
    void getRequest(){
        String result = client.getRequest();
        String[] reqList = result.split("//");
        if(isNumeric(reqList[0])){
            Integer friendCount = Integer.valueOf(reqList[0]);
            allFriendRequest.getItems().clear();
            while (friendCount >= 1) {

                Label lbl = new Label(reqList[friendCount]);
                lbl.setMaxSize(150, 200);
                lbl.setMinSize(100, 200);

                try {
                    lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: #282828; -fx-border-radius: 15; -fx-border-width:3;-fx-font-size: 17");
                    ImageView image = new ImageView(new Image(new FileInputStream("./images/usernoyellow40.png")));
                    image.setPreserveRatio(true);
                    image.setFitWidth(30);
                    image.setFitHeight(30);
                    lbl.setGraphic(image);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                allFriendRequest.getItems().add(lbl);
                friendCount--;
            }
        }
        else{
            allFriendRequest.getItems().clear();
            Label lbl = new Label("No friend requests");
            lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17");
            lbl.setMaxSize(277, 200);
            lbl.setMinSize(277, 200);
            allFriendRequest.getItems().add(lbl);
        }

    }
    void getReqNotif(){
        String result=client.getReqNotif();

        String[] resultString = result.split("//");
        if (isNumeric(resultString[0])) {

            Integer friendCount = Integer.valueOf(resultString[0]);
            while (friendCount >= 1) {
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text(resultString[friendCount] + " sent you a friend request.")
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
    void logout(){
        client.logout();
    }
}

