package BlackjackMenu;

import Blackjack.BlackjackController;
import Client.Client;
import Blackjack.BlackjackController;
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
import javafx.geometry.Pos;
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

public class BlackjackMenuController implements Initializable{
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

    public void initialize(URL url, ResourceBundle rb) {
        ListView.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        allFriends.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        allUsers.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");

        try {
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();

    }

    @FXML
    private AnchorPane ap;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private JFXButton FriendsButton;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label> ListView;

    @FXML
    private JFXListView<Label> allFriends;

    @FXML
    private JFXButton sendGlobalRequest;

    @FXML
    private JFXButton sendrequest;

    @FXML
    private JFXListView<Label> allUsers;

    @FXML
    private JFXButton GameButton;

    @FXML
    void makeChatMenu(MouseEvent event) {

    }

    @FXML
    void makeContactUsMenu(MouseEvent event) {

    }

    @FXML
    void makeFriendsMenu(MouseEvent event) {

    }

    @FXML
    void makeGamesMenu(MouseEvent event) {

    }

    @FXML
    void makeGlobalRequest(ActionEvent event) throws IOException{
        switchingscenes=true;
        String gameid=(String.valueOf(Math.ceil(Math.random()*100000)));
        client.sendReqblack(allUsers.getSelectionModel().getSelectedItem().getText(), gameid);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Blackjack/Blackjack.fxml"));
        Parent Blackjack_parent = loader.load();
        BlackjackController Blackjack = loader.getController();
        Blackjack.setClient(client);
        Blackjack.setUser2(allUsers.getSelectionModel().getSelectedItem().getText(), gameid);
        Blackjack.setPhoto(client.startblack(gameid));
        Scene Blackjack_scene = new Scene(Blackjack_parent);
        Stage Blackjack_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Blackjack_stage.setScene(Blackjack_scene);
        Blackjack_stage.show();
        BackgroundTask.stop();
        Blackjack.makeRefresh();

    }

    @FXML
    void makeMenu(MouseEvent event) throws IOException {
        if(!switchingscenes) {
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

    boolean switchingscenes=false;

    @FXML
    void makeRequest(ActionEvent event) throws IOException{
        switchingscenes=true;
        String gameid=(String.valueOf(Math.ceil(Math.random()*100000)));
        client.sendReqblack(allFriends.getSelectionModel().getSelectedItem().getText(), gameid);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Blackjack/Blackjack.fxml"));
        Parent Blackjack_parent = loader.load();
        BlackjackController Blackjack = loader.getController();
        Blackjack.setClient(client);
        Blackjack.setUser2(allFriends.getSelectionModel().getSelectedItem().getText(), gameid);
        Blackjack.setPhoto(client.startblack(gameid));
        Scene Blackjack_scene = new Scene(Blackjack_parent);
        Stage Blackjack_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Blackjack_stage.setScene(Blackjack_scene);
        Blackjack_stage.show();
        BackgroundTask.stop();
        Blackjack.makeRefresh();
    }
    public void makeRefresh() {
        getOnlineFriends();
        getReqNotif();
        getFriends();
        getMessageNotif();
        getUsers();
        Stage stage=(Stage) ap.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
    }
    void getFriends() {
        String result = client.getFriends();
        String[] friendList = result.split("//");
        Integer friendCount = Integer.valueOf(friendList[0]);
        allFriends.getItems().clear();
        while (friendCount >= 1) {

            Label lbl = new Label(friendList[friendCount]);
            lbl.setMaxSize(180, 120);
            lbl.setMinSize(180, 120);

            try {
                lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                ImageView image = new ImageView(new Image(new FileInputStream("./images/usernoyellow40.png")));
                image.setPreserveRatio(true);
                image.setFitWidth(40);
                image.setFitHeight(40);
                lbl.setAlignment(Pos.BASELINE_LEFT);
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
    void getUsers(){
        String result = client.getUsers();
        String[] UsersList = result.split("//");
        try{
            Integer UsersCount = Integer.valueOf(UsersList[0]);
            allUsers.getItems().clear();
            while (UsersCount >= 1) {

                Label lbl = new Label(UsersList[UsersCount]);
                lbl.setMaxSize(180, 120);
                lbl.setMinSize(180, 120);

                try {
                    lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                    ImageView image = new ImageView(new Image(new FileInputStream("./images/usernoyellow40.png")));
                    image.setPreserveRatio(true);
                    image.setFitWidth(40);
                    image.setFitHeight(40);
                    lbl.setAlignment(Pos.BASELINE_LEFT);
                    lbl.setGraphic(image);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                allUsers.getItems().add(lbl);
                UsersCount--;
            }
        }
        catch (NumberFormatException e){
            allUsers.getItems().clear();
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
