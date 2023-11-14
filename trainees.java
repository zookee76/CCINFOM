package enrollmentmgt;

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
    public String paymentstatus;
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    section Section = new section();
    enrollment Enrollment = new enrollment();
    clearance Clearance = new clearance();
    
    //list of variables
    public ArrayList<Integer> trainee_idList = new ArrayList<>();
    public ArrayList<String> trainee_lastnameList = new ArrayList<>();
    public ArrayList<String> first_nameList = new ArrayList<>();
    public ArrayList<String> middle_initial_nameList = new ArrayList<>();
    public ArrayList<Integer> ageList = new ArrayList<>();
    public ArrayList<Long> contactList = new ArrayList<>();
    public ArrayList<String> training_program_nameList = new ArrayList<>();
    public ArrayList<Integer> section_idList = new ArrayList<>();
    public ArrayList<trainees> traineeList = new ArrayList<>();
    
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
                Enrollment.addEnrollment(trainee_id, training_program_name);
                Clearance.addClearance(trainee_id, training_program_name);
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
    
    public int mod_trainee(int trainee_id, String last_name, String first_name, String middle_initial_name, int age, long contact, String payment_status)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            boolean existingTrainee = doesTraineeExist(trainee_id);
            
            if (existingTrainee)
            {
                Enrollment.modEnrollment(trainee_id);
                Clearance.modClearance(trainee_id, paymentstatus);
                
                PreparedStatement pstst = conn.prepareStatement("UPDATE trainee SET last_name = ?, first_name = ?, middle_initial_name = ?, age = ?, contact = ? WHERE trainee_id = ?");

                pstst.setString(1, last_name);
                pstst.setString(2, first_name);
                pstst.setString(3, middle_initial_name);
                pstst.setInt(4, age);
                pstst.setLong(5, contact);
                pstst.setInt(6, trainee_id);

                pstst.executeUpdate();
                pstst.close();
                conn.close();

                return 1;
            }
            
            else
            {
                conn.close();
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
    
    public int deleteTrainee(int trainee_id)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            boolean existingTrainee = doesTraineeExist(trainee_id);
  
            if (existingTrainee)
            {
                Enrollment.deleteEnrollment(trainee_id);
                Clearance.deleteClearance(trainee_id);
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM trainee WHERE trainee_id = ?");
                pstmt.setInt(1, trainee_id);
                pstmt.executeUpdate();   
                pstmt.close();
                conn.close();

                return 1;
            }
            
            else
            {
                conn.close();
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
    
    public int searchTraineeID(int trainee_id)
    {
       try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            boolean existingTrainee = doesTraineeExist(trainee_id);
            
            if (existingTrainee)
            {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM trainee WHERE trainee_id = ?");
                pstmt.setInt(1, trainee_id);
                ResultSet rst = pstmt.executeQuery();
                traineeList.clear();
                
                while (rst.next()) 
                {
                        trainees trainee = new trainees();
                        trainee.setTraineeId(rst.getInt("trainee_id"));
                        trainee.setLastName(rst.getString("last_name"));
                        trainee.setFirstName(rst.getString("first_name"));
                        trainee.setMiddleInitialName(rst.getString("middle_initial_name"));
                        trainee.setAge(rst.getInt("age"));
                        trainee.setContact(rst.getLong("contact"));
                        trainee.setTrainingProgramName(rst.getString("training_program"));
                        trainee.setSectionId(rst.getInt("section_id"));

                        traineeList.add(trainee);
                }
                
                pstmt.close();
                conn.close();
                return 1;
            }
            
            else
            {
                conn.close();
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
    
    public void listTrainees()
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT trainee_id FROM trainee");
            ResultSet rst = pstmt.executeQuery();
            trainee_idList.clear();
            
            while (rst.next())
            {
                trainee_id = rst.getInt("trainee_id");
                trainee_idList.add(trainee_id);
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
    
    public boolean doesTraineeExist(int trainee_id)
    {
        try
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT trainee_id FROM trainee WHERE trainee_id = ?");
            pstmt.setInt(1, trainee_id);
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) 
            {
                pstmt.close();
                conn.close();
                return true;
            } 
            
            else 
            {
                pstmt.close();
                conn.close();
                return false;
            }
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public void setTraineeId(int traineeId) 
    {
        this.trainee_id = traineeId;
    }
    
    public void setLastName(String lastName) 
    {
        this.last_name = lastName;
    }

    public void setFirstName(String firstName) 
    {
        this.first_name = firstName;
    }

    public void setMiddleInitialName(String middleInitialName) 
    {
        this.middle_initial_name = middleInitialName;
    }

    public void setAge(int age) 
    {
        this.age = age;
    }

    public void setContact(long contact) 
    {
        this.contact = contact;
    }

    public void setTrainingProgramName(String trainingProgramName) 
    {
        this.training_program_name = trainingProgramName;
    }

    public void setSectionId(int sectionId) 
    {
        this.section_id = sectionId;
    }
    
    public int getTrainee_id() 
    {
        return trainee_id;
    }

    public String getLast_name() 
    {
        return last_name;
    }

    public String getFirst_name() 
    {
        return first_name;
    }

    public String getMiddle_initial_name() 
    {
        return middle_initial_name;
    }

    public int getAge() 
    {
        return age;
    }

    public long getContact() 
    {
        return contact;
    }

    public String getTraining_program_name() 
    {
        return training_program_name;
    }

    public int getSection_id() 
    {
        return section_id;
    }

    public List<trainees> getTraineeList() 
    {
        return traineeList;
    }
    
    public static void main (String args[])
    {
        trainees A = new trainees();
        A.listTrainees();
        for (int i = 0; i < A.trainee_idList.size(); i++) 
        {
            System.out.println(A.trainee_idList.get(i));
        }
        
        A.searchTraineeID(219812);
    }
}
