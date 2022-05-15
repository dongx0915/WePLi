/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.*;
/**
 *
 * @author Donghyeon <20183188>
 */
public class Database {
    private static Database dbInstance = new Database();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    String url = "jdbc:mysql://113.198.236.99:3306/WePLi?serverTimezone=UTC&autoReconnect=true";
    String id = "root";
    String pw = "blue!@795132486";
    
    // 싱글턴 패턴
    private Database() {}
    public static Database getDbInstance(){ return dbInstance; }
    
    public Connection connect() {
        try {
            // 1. 접속할 드라이버를 메모리에 올리기 : 정적 메소드
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. 접속하기 위한 메소드 (1.접속url 2.계정명 3.계정암호)
            con = DriverManager.getConnection(url, id, pw);
            
            System.out.println("접속 : " + con);
            return con;
        } 
        catch(Exception e)  {  System.out.println("DB 접속 오류 : " + e);  }
        
        return null;
    }
    
    public void close(){
        try{
            System.out.println("close 됨");
            if(con != null) con.close();
            if(pstmt != null) pstmt.close();
            if(rs != null) rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Connection getConnection(){ return this.con; }
    public PreparedStatement getPreparedStatement(){ return this.pstmt; }
    public ResultSet getResultSet(){ return this.rs; }
}
