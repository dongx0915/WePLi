/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.BaseRepository;

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
 * 
 * !! Repository 사용시 주의 점 !!
 * Entity 클래스 생성 시 ID에 해당하는 필드는 제일 첫 번쨰에 선언 해야함
 * Repository 상속 받아서 사용 시 하위 Repository 생성자에서 사용할 Entity 타입을 지정 해줘야함
 */

public class CrudRepository<T, ID> extends Repository {
    private T entity;             // Entity 
    protected String idFieldName; // id(PK)의 필드 명
    protected String tableName;   // Entity 테이블 명
    protected String className;   // 클래스 명 (패키지 명 포함)
    protected Field[] fieldList;  // T의 필드 리스트
    
    
    // DB 객체 생성 (싱글턴 패턴)
    // public EntityRepository() { db = Database.getDbInstance(); }

    public void setEntity(T entity) { 
        this.entity = entity; 
       
        Class<?> ec = this.entity.getClass();
        
        this.className = ec.getName();
        this.tableName = ec.getSimpleName();
        this.tableName = this.tableName.substring(0, 1).toLowerCase() + this.tableName.substring(1);
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
                
            sql = "SELECT * FROM " + tableName + " ORDER BY " + idFieldName + " DESC LIMIT 1;";
            
            pstmt = con.prepareStatement(sql);
            System.out.println(pstmt);
            // 성공 시 entity 리턴
            return resultSetToEntity(pstmt.executeQuery());
        } 
        catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            return null; 
        }                                    // Insert 오류 발생 시 null 리턴 
        finally{ db.close(); }
    }
    
    public ArrayList<T> saveAll(ArrayList<T> entityList) {
        try {
            con = db.connect();

            for (T entity : entityList) {
                String sql = insertQuery();
                pstmt = con.prepareStatement(sql);
                    
                for (int i = 0; i < fieldList.length; i++) 
                    pstmt.setObject(i + 1, fieldList[i].get(entity));
                
                pstmt.executeUpdate();
            }
            
            // 성공 시 entity 리턴
            return entityList;
        } 
        catch (IllegalAccessException | IllegalArgumentException | SQLException e) { 
            e.printStackTrace();
            return null;            // Insert 오류 발생 시 null 리턴 
        }                                    
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
        catch(SQLException e){ 
            e.printStackTrace();
            return null; 
        }
        finally{ db.close();}
    }
    
    public ArrayList<T> findAll(){
        try{
            con = db.connect();
            
            String sql = "SELECT * FROM " + tableName;
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            return resultSetToEntityList(rs);
        }catch(SQLException e){
            e.printStackTrace();
            return null; 
        }
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
        catch(IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException | SQLException e){
            e.printStackTrace();
            return null; 
        }
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
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        finally{ db.close(); }
    }
    
    public boolean deleteAll(){
        try{
            con = db.connect();
            
            String sql = "DELETE FROM " + tableName;
            
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
            
            return true;
        }
        catch(SQLException e){ 
            e.printStackTrace();
            return false;
        }
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
    protected T resultSetToEntity(ResultSet rs) {
        Object obj = null;                                   // c의 객체를 동적으로 생성 
        try {
            Class c = Class.forName(className);                                 // Entity(T)의 객체를 얻어온다.
            while (rs.next()) {
                obj = c.newInstance();                                   // c의 객체를 동적으로 생성 
                    
                for (Field field : fieldList)                      // Entity 객체의 필드를 하나씩 가져온다.
                    field.set(obj, rs.getObject(field.getName()));              // rs의 값을 Object로 받아서 동적으로 생성한 obj에 할당
            }
        } 
        catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | SQLException e) {  e.printStackTrace(); }
        
        return (T) obj;
    }
    
    // ResultSet을 ArrayList<Entity>로 변환하는 메소드
    protected ArrayList<T> resultSetToEntityList(ResultSet rs) {
        ArrayList<T> list = new ArrayList<>();
        
        try {
            Class c = Class.forName(className);                                 // Entity(T)의 객체를 얻어온다.
            while (rs.next()) {
                Object obj = c.newInstance();                                   // c의 객체를 동적으로 생성 

                for (Field field : fieldList)                                   // Entity 객체의 필드를 하나씩 가져온다.
                    field.set(obj, rs.getObject(field.getName()));              // rs의 값을 Object로 받아서 동적으로 생성한 obj에 할당

                list.add((T) obj); 
            }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | SQLException e) { e.printStackTrace(); }
        
        return list;
    }
}