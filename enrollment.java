/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentmgt;

import java.util.*;
import java.sql.*;

/**
 *
 * @author ccslearner
 */
public class enrollment 
{
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    public int enrollment_id;
    public int trainee_id;
    public String training_program_name;
    
    public enrollment()
    {
      
    }
    
    public int addEnrollment(int trainee_id, String training_program)
    {
       
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            java.sql.Date enrollmentDate = new java.sql.Date(System.currentTimeMillis());
            
            PreparedStatement pstst = conn.prepareStatement("SELECT MAX(enrollment_id) + 1 AS newID FROM enrollment;");
            ResultSet rst = pstst.executeQuery();
            
            while (rst.next())
            {
                enrollment_id = rst.getInt("newID");
            }
            
            pstst = conn.prepareStatement("INSERT INTO enrollment VALUE (?, ?, ?, ?)");

            pstst.setInt(1, enrollment_id);
            pstst.setInt(2, trainee_id);
            pstst.setString(3, training_program);
            pstst.setDate(4, enrollmentDate);

            pstst.executeUpdate();
            pstst.close();
            conn.close();
                
            return 1;
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
