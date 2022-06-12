/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.BaseRepository;

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
    
    // 반환 값이 없는 쿼리 실행 메소드
    protected boolean executeUpdate(String sql) {
        try {
            con = db.connect();
            pstmt = con.prepareStatement(sql);
                
            pstmt.executeUpdate();
            
            return true; // 성공 시 true 리턴
        } 
        // 쿼리 오류 발생 시 false 리턴 
        catch (IllegalArgumentException | SQLException e) { return false; }                                    
        finally{ db.close(); }
    }
    
    // 반환 값이 있는 쿼리 실행 메소드
    protected ResultSet executeQuery(String sql){
        try {
            con = db.connect();
            pstmt = con.prepareStatement(sql);
               
            // 성공 시 ResultSet 리턴
            return pstmt.executeQuery();
        } 
        catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            // 쿼리 오류 발생 시 null 리턴 
            return null; 
        }                                    
    }
}
