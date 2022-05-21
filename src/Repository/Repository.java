/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Donghyeon <20183188>
 */
public class Repository {
    protected final Database db;
    protected Connection con;
    protected PreparedStatement pstmt;
    protected ResultSet rs;

    public Repository() { db = Database.getDbInstance(); }
    
    public boolean executeUpdate(String sql) {
        try {
            con = db.connect();
            pstmt = con.prepareStatement(sql);
                
            pstmt.executeUpdate();
            
            // 성공 시 entity 리턴
            return true;
        } 
        catch (IllegalArgumentException | SQLException e) { return false; }                                    // Insert 오류 발생 시 null 리턴 
        finally{ db.close(); }
    }
}
