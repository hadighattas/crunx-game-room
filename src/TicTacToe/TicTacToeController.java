package TicTacToe;

import Client.Client;
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

public class TicTacToeController implements Initializable {

    private Client client;
    private String user2;
    public String gameid;
    public String photo;
    public Boolean canPlay;
    public String winner;
    public void setClient(Client client){
        this.client=client;
        username.setText(client.username);
    }
    public void setUser2(String user, String gameid){
        this.user2=user;
        opponent.setText(user);
        this.gameid=gameid;
    }
    public void setPhoto(String photo){
        this.photo=photo;
    }
    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event)  {
            try {
                makeRefresh();
            }catch (FileNotFoundException e){

            }
        }
    }));

    public void initialize(URL url, ResourceBundle rb){
        ListView1.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");

        try {
            board.setImage(new Image(new FileInputStream("./images/tic-tac-toe-board.png")));
            home.setImage(new Image(new FileInputStream("./images/home.png")));
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();


    }
    @FXML
    private ImageView board;
    @FXML
    private AnchorPane ap;

    @FXML
    private Label opponent;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label> ListView1;

    @FXML
    private ImageView home;

    @FXML
    private Label message;

    @FXML
    private ImageView case7;

    @FXML
    private ImageView case1;

    @FXML
    private ImageView case3;

    @FXML
    private ImageView case9;

    @FXML
    private ImageView case8;

    @FXML
    private ImageView case4;

    @FXML
    private ImageView case5;

    @FXML
    private ImageView case6;

    @FXML
    private ImageView case2;

    @FXML
    private JFXButton GameButton;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private JFXButton FriendsButton;

    private String[] content={"0","1","2","3","4","5","6","7","8","9"};

    @FXML
    void makeCase1(MouseEvent event) throws FileNotFoundException{
        if(content[1].equals("1") && canPlay && started){
            placetic("0");
        }
    }

    @FXML
    void makeCase2(MouseEvent event) throws FileNotFoundException{
        if(content[2].equals("2") && canPlay && started){
            placetic("1");
        }
    }

    @FXML
    void makeCase3(MouseEvent event) throws FileNotFoundException{
        if(content[3].equals("3") && canPlay && started){
            placetic("2");
        }
    }

    @FXML
    void makeCase4(MouseEvent event) throws FileNotFoundException{
        if(content[4].equals("4") && canPlay && started){
            placetic("3");
        }
    }

    @FXML
    void makeCase5(MouseEvent event) throws FileNotFoundException{
        if(content[5].equals("5") && canPlay && started){
            placetic("4");
        }
    }

    @FXML
    void makeCase6(MouseEvent event) throws FileNotFoundException{
        if(content[6].equals("6") && canPlay && started){
            placetic("5");
        }
    }

    @FXML
    void makeCase7(MouseEvent event) throws FileNotFoundException{
        if(content[7].equals("7") && canPlay && started){
            placetic("6");
        }
    }

    @FXML
    void makeCase8(MouseEvent event) throws FileNotFoundException{
        if(content[8].equals("8") && canPlay && started){
            placetic("7");
        }
    }

    @FXML
    void makeCase9(MouseEvent event) throws FileNotFoundException{
        if(content[9].equals("9") && canPlay && started){
            placetic("8");
        }
    }

    @FXML
    void makeChatMenu(ActionEvent event) {

    }

    @FXML
    void makeContactUsMenu(ActionEvent event) {

    }

    @FXML
    void makeGamesMenu(ActionEvent event) {

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

    public void makeRefresh() throws FileNotFoundException{
        getOnlineFriends();
        getReqNotif();
        getMessageNotif();
        ticgetturn();
        getboard();
        checkWinner();
        Stage stage=(Stage) ap.getScene().getWindow();
        if(!started){
            refreshtic();
            getchar=false;
        }
        if (getchar){
            this.photo=client.starttic(gameid);
            getchar=false;
        }
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
    }
    boolean started=false;
    boolean getchar=false;
    void getOnlineFriends (){
        String result = client.getOnlineFriends();
        String[] onFriendList = result.split("//");
        Integer friendCount = Integer.valueOf(onFriendList[0]);
        ListView1.getItems().clear();
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
                ListView1.getItems().add(lbl);
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
    void refreshtic(){
        String result=client.refreshTic(gameid);
        if(result.equals("true")){
            started=true;
            getchar=true;
            Notifications started = Notifications.create()
                    .title("  CRUNX").text(" "+ user2+" joined.")
                    .graphic(null).hideAfter(Duration.seconds(10))
                    .darkStyle();
            started.show();
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
    public void ticgetturn() {
        String result = client.ticgetturn(gameid);
        if (result.equals("true")) {
            if (photo.equals("X")) {
                canPlay = true;
            } else {
                canPlay = false;
            }

        } else if (result.equals("false")) {
            if (photo.equals("O")) {
                canPlay = true;
            } else {
                canPlay = false;
            }
        }
    }
    public void placetic(String loc) throws FileNotFoundException{
        String result=client.placetic(photo, loc, gameid);
        String[]resultString=result.split("//");
        if(!resultString[0].equals("1")) {
            case1.setImage(new Image(new FileInputStream("./images/t" + resultString[0] + ".png")));
            content[1]="x";

        }
        if(!resultString[1].equals("2")) {
            case2.setImage(new Image(new FileInputStream("./images/t" + resultString[1] + ".png")));
            content[2]="x";
        }

        if(!resultString[2].equals("3")) {
            case3.setImage(new Image(new FileInputStream("./images/t" + resultString[2] + ".png")));
        content[3]="x";
        }
        if(!resultString[3].equals("4")) {
            case4.setImage(new Image(new FileInputStream("./images/t" + resultString[3] + ".png")));
        content[4]="x";
        }
        if(!resultString[4].equals("5")) {
            case5.setImage(new Image(new FileInputStream("./images/t" + resultString[4] + ".png")));
        content[5]="x";
        }
        if(!resultString[5].equals("6")) {
            case6.setImage(new Image(new FileInputStream("./images/t" + resultString[5] + ".png")));
        content[6]="x";
        }
        if(!resultString[6].equals("7")) {
            case7.setImage(new Image(new FileInputStream("./images/t" + resultString[6] + ".png")));
        content[7]="x";
        }
        if(!resultString[7].equals("8")) {
            case8.setImage(new Image(new FileInputStream("./images/t" + resultString[7] + ".png")));
        content[8]="x";
        }
        if(!resultString[8].equals("9")) {
            case9.setImage(new Image(new FileInputStream("./images/t" + resultString[8] + ".png")));
        content[9]="x";
        }
    }
    public void getboard() throws FileNotFoundException{
        String result=client.getboard(gameid);
        try {
            String[] resultString = result.split("//");
            if (!resultString[0].equals("1")) {
                case1.setImage(new Image(new FileInputStream("./images/t" + resultString[0] + ".png")));
                content[1]="x";
            }
            if (!resultString[1].equals("2")) {
                case2.setImage(new Image(new FileInputStream("./images/t" + resultString[1] + ".png")));
                content[2]="x";
            }
            if (!resultString[2].equals("3")) {
                case3.setImage(new Image(new FileInputStream("./images/t" + resultString[2] + ".png")));
                content[3]="x";
            }
            if (!resultString[3].equals("4")) {
                case4.setImage(new Image(new FileInputStream("./images/t" + resultString[3] + ".png")));
                content[4]="x";
            }
            if (!resultString[4].equals("5")) {
                case5.setImage(new Image(new FileInputStream("./images/t" + resultString[4] + ".png")));
                content[5]="x";
            }
            if (!resultString[5].equals("6")) {
                case6.setImage(new Image(new FileInputStream("./images/t" + resultString[5] + ".png")));
                content[6]="x";
            }
            if (!resultString[6].equals("7")) {
                case7.setImage(new Image(new FileInputStream("./images/t" + resultString[6] + ".png")));
                content[7]="x";
            }
            if (!resultString[7].equals("8")) {
                case8.setImage(new Image(new FileInputStream("./images/t" + resultString[7] + ".png")));
                content[8]="x";
            }
            if (!resultString[8].equals("9")) {
                case9.setImage(new Image(new FileInputStream("./images/t" + resultString[8] + ".png")));
                content[9]="x";
            }
        }catch (NullPointerException e){

        }
    }
    public void checkWinner(){
        String result=client.checkwin(gameid);
        try {
            String[] resultString = result.split("//");
            if (resultString[0].equals("WINNER")) {
                if (resultString[1].equals(photo)) {
                    Notifications Request = Notifications.create()
                            .title("  CRUNX").text(client.username+" WON.")
                            .graphic(null).hideAfter(Duration.seconds(10))
                            .darkStyle();
                    Request.show();

                    winner=client.username;
                    stop();
                }
                else {

                    Notifications Request = Notifications.create()
                            .title("  CRUNX").text(client.username+" LOST.")
                            .graphic(null).hideAfter(Duration.seconds(10))
                            .darkStyle();
                    Request.show();
                    winner=user2;
                    stop();

                }
            }
            else if (resultString[0].equals("Nowinner")) {
                    Notifications Request = Notifications.create()
                            .title("  CRUNX").text(" It is a tie.")
                            .graphic(null).hideAfter(Duration.seconds(10))
                            .darkStyle();
                    Request.show();

                    winner="NONE";
                    stop();
            }
        }catch (NullPointerException e){

        }

    }
    void stop() {
        try {
            BackgroundTask.stop();
            Client.updatest(winner);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu/MainMenu.fxml"));
            Parent MainMenu_parent = loader.load();
            MainMenuController mainMenu = loader.getController();
            mainMenu.setClient(client);
            Scene MainMenu_scene = new Scene(MainMenu_parent);
            Stage MainMenu_stage = (Stage) ap.getScene().getWindow();
            MainMenu_stage.setScene(MainMenu_scene);
            MainMenu_stage.show();
            BackgroundTask.stop();
            mainMenu.makeRefresh();
        }catch (IOException e){

        }



    }



}
