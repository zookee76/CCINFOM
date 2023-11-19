/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentmgt;

import java.sql.*;
import java.util.*;

/**
 *
 * @author ccslearner
 */
public class section 
{
    public int section_id;
    public String training_program;
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    public ArrayList<Integer> section_idList = new ArrayList<>();
    
    public section()
    {
        
    }
    
    public void listSections()
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT section_id FROM sections");
            ResultSet rst = pstmt.executeQuery();
            section_idList.clear();
            
            while (rst.next())
            {
                section_id = rst.getInt("section_id");
                section_idList.add(section_id);
            }
            
            pstmt.close();
            conn.close();
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    public int getCurrentTraineesInSection(Connection conn, int section_id, String training_program_name) 
    {
        String countcurrQuery = "SELECT COUNT(*) AS cntTrainees FROM trainee WHERE section_id = " + section_id + ";";

        try 
        {
            PreparedStatement cntStatement = conn.prepareStatement(countcurrQuery);
            ResultSet rstCNT = cntStatement.executeQuery();

            int cntTrainees = -1;

            while (rstCNT.next()) 
            {
                cntTrainees = rstCNT.getInt("cntTrainees");
            }

            return cntTrainees;
        } 

        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int getMaxPerSection(Connection conn, int section_id, String training_program_name)
    {
        String countQuery = "SELECT class_limit FROM training_program WHERE program_name = '" + training_program_name + "';";
        try
        {
            PreparedStatement cntStatement = conn.prepareStatement(countQuery);
            ResultSet rstCNT = cntStatement.executeQuery();
            int class_limit = -1;
                    
            while (rstCNT.next())
            {
                class_limit = rstCNT.getInt("class_limit");
            }
            
            return class_limit;
        }
        
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int addSection(String training_program)
    {
        try 
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstst = conn.prepareStatement("SELECT MAX(section_id) + 1 AS newID FROM sections;");
            ResultSet rst = pstst.executeQuery();
            
            while (rst.next())
            {
                section_id = rst.getInt("newID");
            }
            
            pstst = conn.prepareStatement("INSERT INTO sections VALUE (?, ?)");

            pstst.setInt(1, section_id);
            pstst.setString(2, training_program);

            pstst.executeUpdate();
            pstst.close();
            conn.close();
            System.out.println("success");
            
            return 1;
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int putToSection(String training_program_name)
    {
        try 
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstst = conn.prepareStatement("SELECT section_id AS newID FROM sections WHERE training_program = ?;");
            pstst.setString(1, training_program_name);
            ResultSet rst = pstst.executeQuery();
            
            while (rst.next())
            {
                section_id = rst.getInt("newID");
            }
            
            System.out.println("Section ID: " + section_id);
            
            return section_id;
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
        section S = new section();
        S.putToSection("indian_cuisine");
    }
}
