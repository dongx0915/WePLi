/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Entity.SongChart;
import java.sql.SQLException;
import java.sql.Date;

/**
 *
 * @author joon
 */
public class SongChartRepository2 extends EntityRepository<SongChart, String>{
    
    
    
    public SongChartRepository2() { super.setEntity(new SongChart()); }
    
    public Date getDate(){

        Date dbdate = null;
        
        rs = executeQuery("select * from chartUpdatedDate;");
        try{
            rs.next();
            dbdate = rs.getDate("date");
            return dbdate;
        } catch(Exception e)
        {
            e.printStackTrace();
            return null;
        } finally{
            db.close();
        }
    }

    public void updateDate(Date today){
        
        executeUpdate("update chartUpdatedDate set date = '" + today + "';");
        db.close();
    }    
    
    public void resetAutoIncrement(){
        
        executeUpdate("ALTER TABLE songChart AUTO_INCREMENT = 1;");
        db.close();
    }    
    
}
