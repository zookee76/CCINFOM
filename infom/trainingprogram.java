/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentmgt;

/**
 *
 * @author ccslearner
 */
import java.util.*;
import java.sql.*;

public class trainingprogram 
{
    public String program_name;
    public java.sql.Date start_date;
    public java.sql.Date end_date;
    public float cost;
    public String venue;
    public int class_limit;
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    section Section = new section();
    public ArrayList<trainingprogram> training_programs = new ArrayList<>();
    public ArrayList<String> program_names = new ArrayList<>();
    
    public trainingprogram()
    {
        
    }
    
    public int addTrainingProgram(String program_name, java.sql.Date start_date, java.sql.Date end_date, float cost, String venue, int class_limit)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstst = conn.prepareStatement("INSERT INTO training_program (program_name, start_date, end_date, cost, venue, class_limit) VALUE (?, ?, ?, ?, ?, ?)");

            pstst.setString(1, program_name);
            pstst.setDate(2, start_date);
            pstst.setDate(3, end_date);
            pstst.setFloat(4, cost);
            pstst.setString(5, venue);
            pstst.setInt(6, class_limit);

            pstst.executeUpdate();
            pstst.close();
            conn.close();
            
            Section.addSection(program_name);
            
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
    
    public int modifyTrainingProgram(String program_name, java.sql.Date start_date, java.sql.Date end_date, float cost, String venue, int class_limit) 
    {
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");

            PreparedStatement pstst = conn.prepareStatement("UPDATE enrollment SET finish_date = NULL WHERE training_program = ?");
            pstst.setString(1, program_name);
            pstst.executeUpdate();
            pstst.close();

            pstst = conn.prepareStatement("UPDATE training_program SET start_date = ?, end_date = ?, cost = ?, venue = ?, class_limit = ? WHERE program_name = ?");
            pstst.setDate(1, start_date);
            pstst.setDate(2, end_date);
            pstst.setFloat(3, cost);
            pstst.setString(4, venue);
            pstst.setInt(5, class_limit);
            pstst.setString(6, program_name);
            pstst.executeUpdate();
            pstst.close();

            pstst = conn.prepareStatement("UPDATE enrollment SET finish_date = ? WHERE training_program = ?");
            pstst.setDate(1, end_date);
            pstst.setString(2, program_name);
            pstst.executeUpdate();
            pstst.close();

            conn.close();

            return 1;
        } 
        
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }

    }
    
    public int deleteTrainingProgram(String program_name) 
    {
        int report_id = -1;
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM training_program WHERE program_name = ?");
            pstmt.setString(1, program_name);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) 
            {
                PreparedStatement pstst = conn.prepareStatement("SELECT ts.attendance_report_id AS report_id FROM trainees_present ts LEFT JOIN attendance a ON ts.attendance_report_id = a.attendance_report_id WHERE a.training_program = ?");
                pstst.setString(1, program_name);
                
                ResultSet rstst = pstst.executeQuery();
                
                while (rstst.next())
                {
                    report_id = rstst.getInt("report_id");
                }
                
                PreparedStatement delProg = conn.prepareStatement("DELETE FROM trainees_present WHERE attendance_report_id = ?");
                delProg.setInt(1, report_id);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE trainee SET training_program = NULL, section_id = NULL WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("DELETE FROM sections WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE mentor_trainingprograms SET program_name = NULL WHERE program_name = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE clearance_report SET program_name = NULL WHERE program_name = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE enrollment SET training_program = NULL, finish_date = NULL WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE trainee_assessment SET training_program = NULL WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("DELETE FROM attendance WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("DELETE FROM program_schedule WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM training_program WHERE program_name = ?");
                deleteStmt.setString(1, program_name);
                deleteStmt.executeUpdate();
                
                pstmt.close();
                delProg.close();
                deleteStmt.close();
                conn.close();
                return 1;  
            } 
            
            else 
            {
                pstmt.close();
                conn.close();
                return 0;  
            }
            
        } 
        
        catch (SQLException e)        
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public void listTrainingPrograms()
    {
        try
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM training_program");
            ResultSet rst = pstmt.executeQuery();
            training_programs.clear();
            
            while (rst.next())
            {
                trainingprogram training_program = new trainingprogram();
                training_program.setProgramName(rst.getString("program_name"));
                training_program.setStart(rst.getDate("start_date"));
                training_program.setEnd(rst.getDate("end_date"));
                training_program.setCost(rst.getInt("cost"));
                training_program.setVenue(rst.getString("venue"));
                training_program.setLimit(rst.getInt("class_limit"));

                training_programs.add(training_program);
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

    public void listProgramNames()
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT program_name FROM training_program");
            ResultSet rst = pstmt.executeQuery();
            program_names.clear();
            
            while (rst.next())
            {
                program_name = rst.getString("program_name");
                program_names.add(program_name);
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

    public String getProgramName()
    {
        return program_name;
    }

    public void setProgramName(String program_name)
    {
        this.program_name = program_name;
    }

    public java.sql.Date getStartDate()
    {
        return start_date;
    }
    
    public void setStart(java.sql.Date start_date)
    {
        this.start_date = start_date;
    }

    public java.sql.Date getEndDate()
    {
        return end_date;
    }

    public void setEnd(java.sql.Date end_date)
    {
        this.end_date = end_date;
    }

    public float getCost()
    {
        return cost;
    }

    public void setCost(float cost)
    {
        this.cost = cost;
    }

    public String getVenue()
    {
        return venue;
    }
    
    public void setVenue(String venue)
    {
        this.venue = venue;
    }

    public int getLimit()
    {
        return class_limit;
    }

    public void setLimit(int class_limit)
    {
        this.class_limit = class_limit;
    }

    public List<trainingprogram> getTrainingprograms()
    {
        return training_programs;
    }
    
    public static void main (String args[])
    {
        trainingprogram D = new trainingprogram();
        
        String startDate = "2023-11-07";
        String endDate = "2023-12-29";

        java.sql.Date newStartDate = java.sql.Date.valueOf(startDate);
        java.sql.Date newEndDate = java.sql.Date.valueOf(endDate);
        
        D.listProgramNames();
        
        for (int i = 0; i < D.program_names.size(); i++) 
        {
            System.out.println(D.program_names.get(i));
        }
    }
}