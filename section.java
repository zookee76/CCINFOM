/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentmgt;

import java.sql.*;

/**
 *
 * @author ccslearner
 */
public class section 
{
    
    public section()
    {
        
    }
    
    public int getCurrentTraineesInSection(Connection conn, int section_id, String training_program_name) {
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
}
