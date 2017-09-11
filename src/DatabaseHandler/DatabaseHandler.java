package DatabaseHandler;

import java.sql.*;


/**
 * Created by hadi on 4/28/2017.
 */
public class DatabaseHandler {

    public static boolean executeUpdate(Statement statement, String query){
        try{
            statement.executeUpdate(query);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static ResultSet executeQuery(Statement statement,String query){
        try{
            ResultSet Rs=statement.executeQuery(query);
            return Rs;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
