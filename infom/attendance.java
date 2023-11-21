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

public class attendance 
{    
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    public int attendance_report_id;
    public java.sql.Date attendance_date;
    public int trainee_id;
    public int mentor_id;
    public boolean present_mentor;
    public String training_program;
    public int section_id;
    public ArrayList<trainees> traineeList = new ArrayList<>();
    public int[] trainee_ids;
    trainees trainee = new trainees();
    public ArrayList<Integer> attendance_idList = new ArrayList<>();
    public ArrayList<attendance> attendance_List = new ArrayList<>();
    public ArrayList<Integer> trainees_presentList = new ArrayList<>();
    
    public attendance()
    {
        
    }
    
    public int logAttendance(java.sql.Date attendance_date, int mentor_id, boolean present_mentor, String training_program, int[] trainee_ids)
    {
        boolean sameSection;
        
        try 
        {
            sameSection = areTraineesInSameSection(trainee_ids, training_program);
            
            if (sameSection == true)
            {
                Connection conn;
                conn = DriverManager.getConnection(URL, USERNAME, PASS);
                System.out.println("Connection Successful!");

                PreparedStatement pstst = conn.prepareStatement("SELECT MAX(attendance_report_id) + 1 AS newID FROM attendance;");
                ResultSet rst = pstst.executeQuery();

                while (rst.next())
                {
                    attendance_report_id = rst.getInt("newID");
                }

                pstst = conn.prepareStatement("INSERT INTO attendance VALUE (?, ?, ?, ?, ?, ?)");

                pstst.setInt(1, attendance_report_id);
                pstst.setDate(2, attendance_date);
                pstst.setInt(3, mentor_id);
                pstst.setBoolean(4, present_mentor);
                pstst.setString(5, training_program);
                pstst.setInt(6, section_id);

                pstst.executeUpdate();
                pstst.close();

                for (int i = 0; i < trainee_ids.length; i++)
                {    
                    pstst = conn.prepareStatement("INSERT INTO trainees_present VALUE (?, ?)");
                    pstst.setInt(1, attendance_report_id);
                    pstst.setInt(2, trainee_ids[i]);
                    pstst.executeUpdate();
                    pstst.close();
                }

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
    
    public int modifyAttendance(int attendance_report_id, java.sql.Date attendance_date, int mentor_id, boolean present_mentor, String training_program, int[] trainee_ids)
    {
        boolean sameSection;
        
        try 
        {
            sameSection = areTraineesInSameSection(trainee_ids, training_program);
            
            if (sameSection == true)
            {
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
                System.out.println("Connection Successful!");

                PreparedStatement pstst = conn.prepareStatement("UPDATE attendance SET attendance_date = ?, mentor_id = ?, present_mentor = ?, training_program = ? WHERE attendance_report_id = ?");
                pstst.setDate(1, attendance_date);
                pstst.setInt(2, mentor_id);
                pstst.setBoolean(3, present_mentor);
                pstst.setString(4, training_program);
                pstst.setInt(5, attendance_report_id);
                pstst.executeUpdate();
                pstst.close();
                
                pstst = conn.prepareStatement("DELETE FROM trainees_present WHERE attendance_report_id = ?");
                pstst.executeUpdate();
                pstst.close();
                
                for (int i = 0; i < trainee_ids.length; i++)
                {    
                    pstst = conn.prepareStatement("INSERT INTO trainees_present VALUE (?, ?)");
                    pstst.setInt(1, attendance_report_id);
                    pstst.setInt(2, trainee_ids[i]);
                    pstst.executeUpdate();
                    pstst.close();
                }

                pstst.close();
                conn.close();

                return 1;
            }
            
            else
            {
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
    
    public boolean areTraineesInSameSection(int[] trainee_ids, String training_proram) 
    {
        if (trainee_ids == null || trainee_ids.length == 0) 
        {
            return false;
        }

        int firstTraineeSectionId = getSectionId(trainee_ids[0]);

        for (int i = 1; i < trainee_ids.length; i++) 
        {
            int currentTraineeSectionId = getSectionId(trainee_ids[i]);
            
            if (firstTraineeSectionId != currentTraineeSectionId)
            {
                return false;
            }
        }
        
        for (int i = 1; i < trainee_ids.length; i++) 
        {
            String currentTraineeProgram = getProgram(trainee_ids[i]);
            
            if (!training_program.equals(currentTraineeProgram))
            {
                return false;
            }
        }

        return true;
    }
    
    public String getProgram(int trainee_id) 
    {
        String trainee_program = null;
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");

            PreparedStatement pstst = conn.prepareStatement("SELECT training_program FROM trainee WHERE trainee_id = ?");
            pstst.setInt(1, trainee_id);
            ResultSet rst = pstst.executeQuery();

            while (rst.next()) 
            {
                trainee_program = rst.getString("training_program");
            }

            pstst.close();
            conn.close();

            return trainee_program;
        } 
        
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    
    public int getSectionId(int trainee_id) 
    {
        int sectionID = 0;
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");

            PreparedStatement pstst = conn.prepareStatement("SELECT s.section_id FROM sections s JOIN trainee t ON s.training_program = t.training_program WHERE trainee_id = ?");
            pstst.setInt(1, trainee_id);
            ResultSet rst = pstst.executeQuery();

            while (rst.next()) 
            {
                sectionID = rst.getInt("section_id");
            }

            pstst.close();
            conn.close();

            return sectionID;
        } 
        
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int deleteAttendance(int attendance_report_id)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            PreparedStatement pstst = conn.prepareStatement("DELETE FROM trainees_present WHERE attendance_report_id = ?");
            pstst.setInt(1, attendance_report_id);
            pstst.executeUpdate();
            pstst.close();
            
            pstst = conn.prepareStatement("DELETE FROM attendance WHERE attendance_report_id = ?");
            pstst.setInt(1, attendance_report_id);
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
    
    public void listAttendance()
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT attendance_report_id FROM attendance");
            ResultSet rst = pstmt.executeQuery();
            attendance_idList.clear();
            
            while (rst.next())
            {
                attendance_report_id = rst.getInt("attendance_report_id");
                attendance_idList.add(attendance_report_id);
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
    
    public int searchAttendanceReportID(int attendance_report_id)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM attendance WHERE attendance_report_id = ?");
            pstmt.setInt(1, attendance_report_id);
            ResultSet rst = pstmt.executeQuery();
            attendance_List.clear();

            while (rst.next()) 
            {
                    attendance attendance = new attendance();
                    attendance.setAttendanceReportId(rst.getInt("attendance_report_id"));
                    attendance.setAttendanceDate(rst.getDate("attendance_date"));
                    attendance.setMentorId(rst.getInt("mentor_id"));
                    attendance.setPresentMentor(rst.getBoolean("present_mentor"));
                    attendance.setTrainingProgram(rst.getString("training_program"));
                    attendance.setSectionId(rst.getInt("section_id"));
                    attendance_List.add(attendance);
            }

            pstmt.close();
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
    
    public int searchTraineePresent(int attendance_report_id)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM trainees_present WHERE attendance_report_id = ?");
            pstmt.setInt(1, attendance_report_id);
            ResultSet rst = pstmt.executeQuery();
            trainees_presentList.clear();

            while (rst.next()) 
            {
                    int trainee_id = rst.getInt("trainee_id");
                    trainees_presentList.add(trainee_id);
            }

            pstmt.close();
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
    
    public int searchAttendanceReportProgram(String training_program)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM attendance WHERE training_program = ?");
            pstmt.setString(1, training_program);
            ResultSet rst = pstmt.executeQuery();
            attendance_List.clear();

            while (rst.next()) 
            {
                    attendance attendance = new attendance();
                    attendance.setAttendanceReportId(rst.getInt("attendance_report_id"));
                    attendance.setAttendanceDate(rst.getDate("attendance_date"));
                    attendance.setMentorId(rst.getInt("mentor_id"));
                    attendance.setPresentMentor(rst.getBoolean("present_mentor"));
                    attendance.setTrainingProgram(rst.getString("training_program"));
                    attendance.setSectionId(rst.getInt("section_id"));
                    attendance_List.add(attendance);
            }

            pstmt.close();
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
    
    public int getAttendanceReportId() 
    {
        return attendance_report_id;
    }

    public void setAttendanceReportId(int attendance_report_id) 
    {
        this.attendance_report_id = attendance_report_id;
    }

    public java.sql.Date getAttendanceDate() 
    {
        return attendance_date;
    }

    public void setAttendanceDate(java.sql.Date attendance_date) 
    {
        this.attendance_date = attendance_date;
    }

    public int getTraineeId() 
    {
        return trainee_id;
    }

    public void setTraineeId(int trainee_id) 
    {
        this.trainee_id = trainee_id;
    }

    public int getMentorId() 
    {
        return mentor_id;
    }

    public void setMentorId(int mentor_id) 
    {
        this.mentor_id = mentor_id;
    }

    public boolean isPresentMentor() 
    {
        return present_mentor;
    }

    public void setPresentMentor(boolean present_mentor) 
    {
        this.present_mentor = present_mentor;
    }

    public String getTrainingProgram() 
    {
        return training_program;
    }

    public void setTrainingProgram(String training_program) 
    {
        this.training_program = training_program;
    }

    public int getSectionId() 
    {
        return section_id;
    }

    public void setSectionId(int section_id) 
    {
        this.section_id = section_id;
    }
    
    public List<attendance> getAttendanceList() 
    {
        return attendance_List;
    }
    
    public List<Integer> getTraineeList()
    {
        return trainees_presentList;
    }
    
    public static void main (String args[])
    {
       attendance R = new attendance();
       
       String startDate = "2023-11-24";
       java.sql.Date newStartDate = java.sql.Date.valueOf(startDate);
       int[] trainee_ids = {231967, 219812};

       R.searchAttendanceReportProgram("greek_cuisine");
    }
}
