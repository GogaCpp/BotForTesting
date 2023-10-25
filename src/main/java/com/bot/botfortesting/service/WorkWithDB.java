package com.bot.botfortesting.service;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class WorkWithDB {

    public static String url="jdbc:postgresql://localhost:5432/testsdb";
    public static String name="postgres";
    public static String pass="123";
    //;INIT=RUNSCRIPT FROM 'classpath:init.sql'
    public static Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {

            dbConnection = DriverManager.getConnection(url,name ,pass);
            return dbConnection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

}

