package Client;
import java.io.*;
import java.net.*;

public class Client {
    public String username=null;
    public Client() {
    }
    public static void main(String[] args) {
    }
    public String makeLogin(String user, String pass){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("LOGIN//"+user+"//"+pass+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            if(response.equals("Success")){
                this.username=user;
            }
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fail";
        }
    }
    public static String makeSignUp(String user, String pass, String email,String gender,
                                  String age, String country,String question, String answer){
        String response,sentence;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("SIGNUP//"+user+"//"+pass+"//"+email+"//"+gender+"//"+age+"//"+country+"//"+question+"//"+answer+"//"+'\n');
            response=inFromServer.readLine();
//            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //

            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            // close the socket when done //
            clientSocket.close();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fail";
        }
    }
    public String getOnlineFriends(){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETONFR//"+this.username+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fail";
        }
    }
    public String getFriends(){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETFR//"+this.username+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fail";
        }
    }
    public String sendFriendRequest(String friendToAdd){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("REQUEST//"+username+"//"+friendToAdd+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            if(response.equals("Success")){
                return "Friend request sent";
            }
            else if(response.equals("Already")){
                return "You are already friends with this user";
            }
            else if(response.equals("Alreadysent")){
                return "Request already sent";
            }
            else{
                return "User does not exist";
            }
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Cannot add friend right now";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Cannot add friend right now";
        }
    }
    public String getRequest(){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETREQ//"+username+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "No friend requests";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "No friend requests";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "No friend requests";
        }
    }
    public String addFriend(String added){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("ADD//"+username+"//"+added+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fail";
        }
    }
    public String declineFriend(String declined){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("DECLINE//"+username+"//"+declined+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fail";
        }
    }
    public String getReqNotif(){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("REQNOTIF//"+username+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "0";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "0";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "0";
        }
    }
    public void logout(){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("LOGOUT//"+username+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void sendMessage(String friend, String message){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("MESSAGE//"+username+"//"+friend+"//"+message+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getMessages(String friend){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETMSG//"+username+"//"+friend+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "No messages";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "No messages";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "No messages";
        }
    }
    public String search(String user, String mail) {
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("SEARCH//"+user+"//"+mail+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "No result";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "No result";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "No result";
        }
    }

    public String updateInfo(String mailText, String usernameText, String countryText, String ageText, String emailVisible, String ageVisible, String countryVisible) {

        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("UPDATEINFO//"+usernameText+"//"+mailText+"//"+ageText+"//"+countryText
                    +"//"+emailVisible+"//"+ageVisible+"//"+countryVisible+'\n');
            response=inFromServer.readLine();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //

            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fail";
        }
    }
    public String getInfo(String username){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETINFO//"+username+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "No result";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "No result";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "No result";
        }
    }
    public String getMessageNotif(){
        String response;
        Socket clientSocket=null;
        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("MSGNOTIF//"+username+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "0";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "0";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "0";
        }
    }
    public String getUsers(){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETUSERS//"+this.username+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Fail";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fail";
        }
    }
    public void sendReqTic(String friend, String gameid){
        Socket clientSocket=null;
        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("INVTIC//"+username+"//"+friend+"//"+gameid+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getInvites(){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETGAMEREQ//"+username+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "No requests";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "No requests";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "No requests";
        }
    }
    public void accepttic(String gameid, String user2){
        Socket clientSocket=null;
        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("ACCEPTTIC//"+gameid+"//"+this.username+"//"+user2+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void declinetic(String gameid, String user2){
        Socket clientSocket=null;
        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("DECLINETIC//"+gameid+"//"+this.username+"//"+user2+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String refreshTic(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("REFRESHTIC//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "false";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "false";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "false";
        }
    }
    public String starttic(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("STARTTIC//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "false";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "false";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "false";
        }
    }
    public String ticgetturn(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETTURNTIC//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "false";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "false";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "false";
        }
    }
    public String placetic(String photo, String loc, String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("PLACETIC//"+photo+"//"+loc+"//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "false";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "false";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "false";
        }
    }
    public String getboard(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETBOARD//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "1";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "1";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "1";
        }
    }
    public String checkwin(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("WINNERTIC//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Notyet";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Notyet";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Notyet";
        }
    }
    public void sendReqblack(String friend, String gameid){
        Socket clientSocket=null;
        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("INVBLACK//"+username+"//"+friend+"//"+gameid+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void acceptblack(String gameid, String user2){
        Socket clientSocket=null;
        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("ACCEPTBLACK//"+gameid+"//"+this.username+"//"+user2+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void declineblack(String gameid, String user2){
        Socket clientSocket=null;
        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("DECLINEBLACK//"+gameid+"//"+this.username+"//"+user2+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String refreshblack(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("REFRESHBLACK//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "false";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "false";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "false";
        }
    }
    public String startblack(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("STARTBLACK//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "false";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "false";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "false";
        }
    }
    public String blackgetturn(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETTURNBLACK//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "false";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "false";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "false";
        }
    }
    public String getcards(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETCARDS//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "1";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "1";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "1";
        }
    }
    public String checkwinblack(String gameid){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("WINBLACK//"+gameid+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "Notyet";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "Notyet";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Notyet";
        }
    }
    public void sendupdate(String update, String gameid, String photo){
        Socket clientSocket=null;
        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("UPDATEBLACK//"+gameid+"//"+photo+"//"+update+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
    public void sendMessageg(String message){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("MESSAGEG//"+username+"//"+message+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getMessagesg(){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETMSGG//"+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "No messages";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "No messages";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "No messages";
        }
    }
    public void stay(String gameid, String user){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("STAY//"+gameid+"//"+user+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void updatest(String winner){
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("SCORET//"+winner+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void updatesb(String winner){
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("SCOREB//"+winner+'\n');
            clientSocket.close();
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getscoret(String tosee){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETSCORET//"+tosee+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "No messages";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "No messages";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "No messages";
        }
    }
    public String getscoreb(String tosee){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("GETSCOREB//"+tosee+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "No messages";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "No messages";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "No messages";
        }
    }
    public String getquestion(String user){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("FORGOT//"+user+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "FAIL";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "FAIL";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }
    public String sendanswer(String user, String answer){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("ANSWER//"+user+"//"+answer+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "FAIL";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "FAIL";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }
    public String sendpass(String user, String pass){
        String response;
        Socket clientSocket=null;

        try {
            clientSocket = new Socket("localhost",6788);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("NEWPASS//"+user+"//"+pass+'\n');
            response=inFromServer.readLine();
            clientSocket.close();
            return response;
        } catch (UnknownHostException ex) {// catch the exceptions //
            ex.printStackTrace();
            return "FAIL";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
            return "FAIL";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }
}

