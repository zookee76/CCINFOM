package traineemanagement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ccslearner
 */
import java.util.*;
import java.sql.*;
import sectionprogramgt.section;

public class trainees 
{
    public int trainee_id;
    public String last_name;
    public String first_name;
    public String middle_initial_name;
    public int age;
    public long contact;
    public String training_program_name;
    public int section_id;
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    section Section = new section();
    
    //list of variables
    public ArrayList<Integer> trainee_idList = new ArrayList<>();
    public ArrayList<String> trainee_lastnameList = new ArrayList<>();
    public ArrayList<String> first_nameList = new ArrayList<>();
    public ArrayList<String> middle_initial_nameList = new ArrayList<>();
    public ArrayList<Integer> ageList = new ArrayList<>();
    public ArrayList<Long> contactList = new ArrayList<>();
    public ArrayList<String> training_program_nameList = new ArrayList<>();
    public ArrayList<Integer> section_idList = new ArrayList<>();
    
    public trainees()
    {
        
    }
    
    public int register_trainee(String last_name, String first_name, String middle_initial_name, int age, long contact, String training_program_name, int section_id)
    {
        try 
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            // getting next traineeid
            PreparedStatement pstst = conn.prepareStatement("SELECT MAX(trainee_id) + 1 AS newID FROM trainee;");
            ResultSet rst = pstst.executeQuery();
            
            while (rst.next())
            {
                trainee_id = rst.getInt("newID");
            }
            
            int currentTrainees = Section.getCurrentTraineesInSection(conn, section_id, training_program_name);
            int maxPerSec = Section.getMaxPerSection(conn, section_id, training_program_name);
            
            if (currentTrainees < maxPerSec)
            {
                pstst = conn.prepareStatement("INSERT INTO trainee (trainee_id, last_name, first_name, middle_initial_name, age, contact, training_program, section_id) VALUE (?, ?, ?, ?, ?, ?, ?, ?)");

                pstst.setInt(1, trainee_id);
                pstst.setString(2, last_name);
                pstst.setString(3, first_name);
                pstst.setString(4, middle_initial_name);
                pstst.setInt(5, age);
                pstst.setLong(6, contact);
                pstst.setString(7, training_program_name);
                pstst.setInt(8, section_id);

                pstst.executeUpdate();
                pstst.close();
                conn.close();
                System.out.println("success");
                return 1;
            }
            
            else
            {
                return 0;
            }
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }

    
    public static void main (String args[])
    {
        trainees A = new trainees();
    }
}
