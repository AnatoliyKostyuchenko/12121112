package com.company;

import java.io.FileNotFoundException;
import java.sql.*;

public class Base implements AuthService {
    public static void main(String[] args) {
        try{
            connect();
            createTable();
            readData();
            insertStudents();
            insertOneStudent("login4","pass4","nickname4");
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            disconnect();
        }
    }
   public static Connection connection = null;
   public static Statement statement = null;

    // write your code here
    public void start(){
        System.out.println("Auth service is running");
    }

    private static void connect() throws SQLException {

        connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
        connection.setAutoCommit(true);
        statement = connection.createStatement();
    }
    private static void disconnect(){
        try{
            if(connection != null){
                connection.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            if(statement != null){
                statement.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private static void insertOneStudent(String login,String pass, String nickname) throws SQLException {
       try (PreparedStatement ps = connection.prepareStatement("insert into users(login,pass, nickname)" +
                "values (?,?,?)");) {
           //передать запрос вместо name
           ps.setString(1, login);
           ps.setString(2, pass);
           ps.setString(3, nickname);
           ps.execute();
       }catch (Exception ex){
           ex.printStackTrace();
       }

    }
    private static void createTable() throws SQLException {
        statement.executeUpdate("create table if not exists users("+
                "pass text not null,"+
                "login text not null,"+
                "nickname text not null");
    }
    private static void insertStudents() throws SQLException {
        for(int i=0; i<10; i++){
            statement.executeUpdate("insert into users(login,pass, nickname)"+
                    "values ('login','pass','nickname')");

        }
    }
    private static void readData() throws SQLException {

    }
    public void stop(){
        System.out.println("Auth service is shutting down");
    }

    @Override
    public String authorization(String login, String pass) throws FileNotFoundException {
        try(ResultSet rs = statement.executeQuery("select*from users")){
    if(login.equals(statement.executeQuery("select*from login"))&&(pass.equals(statement.executeQuery("select*from pass ")) )){
      statement.executeQuery("select*from nickname");
    }else{
        System.out.println("Пароль или логин неверный");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
