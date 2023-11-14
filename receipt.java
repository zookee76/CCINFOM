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
public class receipt 
{
    public int or_no;
    public java.sql.Date transaction_date;
    public float total_amount;
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    
    public receipt()
    {
        
    }
    
    public int addReceipt(int trainee_id)
    {
       
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            transaction_date = new java.sql.Date(System.currentTimeMillis());
            
            PreparedStatement pstst = conn.prepareStatement("SELECT MAX(or_no) + 1 AS newID FROM receipt;");
            ResultSet rst = pstst.executeQuery();
            
            while (rst.next())
            {
                or_no = rst.getInt("newID");
            }
            
            pstst = conn.prepareStatement("SELECT tp.cost AS total_cost FROM training_program tp JOIN clearance_report cr ON cr.program_name = tp.program_name WHERE trainee_id = ?;");
            pstst.setInt(1, trainee_id);
            rst = pstst.executeQuery();
            
            while (rst.next())
            {
                total_amount = rst.getFloat("total_cost");
            }
            
            pstst = conn.prepareStatement("INSERT INTO receipt VALUE (?, ?, ?)");

            pstst.setInt(1, or_no);
            pstst.setDate(2, transaction_date);
            pstst.setFloat(3, total_amount);

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
    
    public int deleteReceipt(int trainee_id, int or_no)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
                       
            PreparedStatement pstst = conn.prepareStatement("DELETE FROM receipt WHERE or_no = ?");

            pstst.setInt(1, or_no);

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
    
    public static void main (String args[])
    {
        receipt R = new receipt();
    }
}
