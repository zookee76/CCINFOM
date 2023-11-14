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
public class clearance 
{
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    public int clearance_report_id;
    public String clearance_status;
    public int trainee_id;
    public String training_program_name;
    public java.sql.Date clearanceDate;
    public int receipt_or_no;
    receipt Receipt = new receipt();
    
    public clearance()
    {
        
    }
    
    public int addClearance(int trainee_id, String training_program)
    {    
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            clearanceDate = new java.sql.Date(System.currentTimeMillis());
            
            PreparedStatement pstst = conn.prepareStatement("SELECT MAX(clearance_report_id) + 1 AS newID FROM clearance_report;");
            ResultSet rst = pstst.executeQuery();
            
            while (rst.next())
            {
                clearance_report_id = rst.getInt("newID");
            }
            
            pstst = conn.prepareStatement("INSERT INTO clearance_report VALUE (?, ?, ?, ?, ?, ?)");

            pstst.setInt(1, clearance_report_id);
            pstst.setString(2, "unpaid");
            pstst.setInt(3, trainee_id);
            pstst.setString(4, training_program);
            pstst.setNull(5, java.sql.Types.DATE);
            pstst.setNull(6, java.sql.Types.INTEGER);

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
    
    public int modClearance(int trainee_id, String paymentstatus)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            if (paymentstatus.equals("paid"))
            {
                clearanceDate = new java.sql.Date(System.currentTimeMillis());
                Receipt.addReceipt(trainee_id);
                
                PreparedStatement pstst = conn.prepareStatement("SELECT MAX(or_no) AS orNum FROM receipt;");
                ResultSet rst = pstst.executeQuery();
            
                while (rst.next())
                {
                    receipt_or_no = rst.getInt("orNum");
                }

                pstst = conn.prepareStatement("UPDATE clearance_report SET clearance_status = ?, clearance_date = ?,  receipt_or_no = ? WHERE trainee_id = ?");

                pstst.setString(1, paymentstatus);
                pstst.setDate(2, clearanceDate);
                pstst.setInt(3, receipt_or_no);
                pstst.setInt(4, trainee_id);
                

                pstst.executeUpdate();
                pstst.close();
                conn.close();
            }
            
            else
            {
                PreparedStatement pstst = conn.prepareStatement("UPDATE clearance_report SET clearance_status = ?, clearance_date = ? WHERE trainee_id = ?");

                pstst.setString(1, paymentstatus);
                pstst.setNull(2, java.sql.Types.DATE);
                pstst.setInt(3, trainee_id);

                pstst.executeUpdate();
                pstst.close();
                conn.close();
            }
                
            return 1;
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int deleteClearance(int trainee_id)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            int or_no = -1;
            PreparedStatement pstst = conn.prepareStatement("SELECT cr.receipt_or_no AS orNum FROM clearance_report cr JOIN receipt r ON cr.receipt_or_no = r.or_no WHERE trainee_id = ?;");
            pstst.setInt(1, trainee_id);
            ResultSet rst = pstst.executeQuery();
            
            while (rst.next())
            {
                or_no = rst.getInt("orNum");
            }
            
            pstst = conn.prepareStatement("DELETE FROM clearance_report WHERE trainee_id = ?");

            pstst.setInt(1, trainee_id);

            pstst.executeUpdate();
            pstst.close();
            conn.close();
            
            Receipt.deleteReceipt(trainee_id, or_no);
                
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
        clearance C = new clearance();
        C.deleteClearance(234679);
    }
}
