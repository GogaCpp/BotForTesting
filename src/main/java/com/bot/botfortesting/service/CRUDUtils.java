package com.bot.botfortesting.service;

import com.bot.botfortesting.supportclasses.Student;
import org.springframework.context.annotation.PropertySource;


import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CRUDUtils {

    private static String get_student="SELECT * FROM alldata.students";
    private static String inset_student="INSERT INTO alldata.students(studentName,studentPass) VALUES(?,?);";

    public static void runInitScript() {
        try (Connection connection =WorkWithDB.getDBConnection();
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\User\\IdeaProjects\\BotForTesting\\src\\main\\resources\\init.sql"))) {

            String line;
            StringBuilder sql = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                // Ignore empty lines or lines starting with '--' (comments)
                if (!line.trim().isEmpty() && !line.trim().startsWith("--")) {
                    sql.append(line);

                    // Execute the statement if it ends with a semicolon
                    if (line.trim().endsWith(";")) {
                        // Remove the semicolon
                        sql.deleteCharAt(sql.length() - 1);

                        statement.executeUpdate(sql.toString());

                        // Reset the StringBuilder
                        sql.setLength(0);
                    }
                }
            }
            System.out.println("SQL script executed successfully!");
        } catch (Exception e) {
            System.err.println("Failed to execute the SQL script: " + e.getMessage());
        }
    }
    public static List<Student> getStudentData(){
        List<Student> students =new ArrayList<>();

        try(Connection connection=WorkWithDB.getDBConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(get_student)) {
            ResultSet rs=preparedStatement.executeQuery();

            while (rs.next()){
                int id=rs.getInt("id");
                String name= rs.getString("studentName");
                String pass= rs.getString("studentPass");
                students.add(new Student(id,name,pass));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
    public static void saveStudentData(Student student){
        List<Student> students =new ArrayList<>();

        try(Connection connection=WorkWithDB.getDBConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(inset_student)) {
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getPass());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
