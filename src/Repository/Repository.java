/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Database.Database;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 *
 * @author Donghyeon <20183188>
 * @param <T>
 * @param <ID>
 */
public class Repository<T, ID> {
    private final Database db;
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    private T entity;             // Entity 
    protected String idFieldName; // id(PK)의 필드 명
    protected String tableName;   // Entity 테이블 명
    protected String className;   // 클래스 명 (패키지 명 포함)
    protected Field[] fieldList;  // T의 필드 리스트
    
    
    // DB 객체 생성 (싱글턴 패턴)
    public Repository() { db = Database.getDbInstance(); }

    public void setEntity(T entity) { 
        this.entity = entity; 
       
        Class<?> ec = this.entity.getClass();
        
        this.className = ec.getName();
        this.tableName = ec.getSimpleName().toLowerCase();
        this.idFieldName = ec.getDeclaredFields()[0].getName();
        this.fieldList = ec.getDeclaredFields();
        
        //private 필드까지 접근 가능하도록 설정
        for (Field field : fieldList) field.setAccessible(true);
    }
    
    public T save(T entity) {
        try {
            con = db.connect();

            String sql = insertQuery();
            pstmt = con.prepareStatement(sql);
                
            for (int i = 0; i < fieldList.length; i++) 
                pstmt.setObject(i + 1, fieldList[i].get(entity));
                
            pstmt.executeUpdate();
            
            // 성공 시 entity 리턴
            return entity;
        } 
        catch (Exception e) { return null; }                                    // Insert 오류 발생 시 null 리턴 
        finally{ db.close(); }
    }
    
    public T findById(ID id) {
        try{
            con = db.connect();
            
            String sql = "SELECT * FROM " + tableName + " WHERE " + idFieldName + " = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setObject(1, id);
            
            rs = pstmt.executeQuery();
            
            // rs를 Entity 리스트로 변환 후 반환
            return resultSetToEntity(rs);
        }
        catch(Exception e){ return null; }
        finally{ db.close();}
    }
    
    public ArrayList<T> findAll(){
        try{
            con = db.connect();
            
            String sql = "SELECT * FROM " + tableName;
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            return resultSetToEntityList(rs);
        }catch(Exception e){ return null; }
        finally { db.close(); }
    }
    
    public T update(T entity){
        try{
            con = db.connect();
            
            String sql = updateQuery();
            pstmt = con.prepareStatement(sql);
            
            for (int i = 0; i < fieldList.length; i++) 
                pstmt.setObject(i+1, fieldList[i].get(entity));
                        
            Class<?> entityClass = entity.getClass();
            Field idField = entityClass.getDeclaredField(idFieldName);
            idField.setAccessible(true);
            
            ID id = (ID) idField.get(entity);
            
            pstmt.setObject(fieldList.length + 1, id);
            
            int result = pstmt.executeUpdate();
            
            // update 된 쿼리가 0개면 null 리턴
            return result > 0 ? entity : null;
        }
        catch(Exception e){  return null; }
        finally { db.close(); }
    }
    
    public boolean deleteById(ID id){
        try{
            con = db.connect();
            
            String sql = "DELETE FROM " + tableName + " WHERE " + idFieldName + " = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setObject(1, id);
            
            pstmt.executeUpdate();
            
            return true;
        }
        catch(Exception e){ return false;}
        finally{ db.close(); }
    }
    
    // Insert 쿼리 생성 메소드
    private String insertQuery() {
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " VALUES");
        StringJoiner sj = new StringJoiner(", ", "(", ")");
        
        for (Field f : fieldList) sj.add("?");                                  
        
        return sql.append(sj).toString();
    }
        
    private String updateQuery(){
        StringBuilder sb = new StringBuilder("UPDATE " + tableName + " SET ");
        StringJoiner sj = new StringJoiner(",");
        
        
        for (Field field : fieldList) {
            sj.add(field.getName() + " = ?");
        }
            
        sb.append(sj).append(" WHERE ").append(idFieldName).append(" = ?;");
        
        return sb.toString();
    }
    
    // ResultSet을 Entity로 변환하는 메소드
    private T resultSetToEntity(ResultSet rs) {
        try {
            Class c = Class.forName(className);                                 // Entity(T)의 객체를 얻어온다.
            if (rs.next()) {
                Object obj = c.newInstance();                                   // c의 객체를 동적으로 생성 
                    
                for (Field field : fieldList)                      // Entity 객체의 필드를 하나씩 가져온다.
                    field.set(obj, rs.getObject(field.getName()));              // rs의 값을 Object로 받아서 동적으로 생성한 obj에 할당

                return (T) obj;
            }
        } catch (Exception e) { e.printStackTrace(); }
        
        return null;
    }
    
    // ResultSet을 ArrayList<Entity>로 변환하는 메소드
    private ArrayList<T> resultSetToEntityList(ResultSet rs) {
        ArrayList<T> list = new ArrayList<>();
        
        try {
            Class c = Class.forName(className);                                 // Entity(T)의 객체를 얻어온다.
            while (rs.next()) {
                Object obj = c.newInstance();                                   // c의 객체를 동적으로 생성 

                for (Field field : fieldList)                                   // Entity 객체의 필드를 하나씩 가져온다.
                    field.set(obj, rs.getObject(field.getName()));              // rs의 값을 Object로 받아서 동적으로 생성한 obj에 할당

                list.add((T) obj); 
            }
        } catch (Exception e) { e.printStackTrace(); }
        
        return list;
    }
}