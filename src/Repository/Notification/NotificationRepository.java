/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.Notification;

import Entity.Notification.Notification;
import Repository.BaseRepository.CrudRepository;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class NotificationRepository extends CrudRepository<Notification, Integer>{
    public NotificationRepository() { this.setEntity(new Notification());}
    
    public boolean isExist(String userId, String relaylistId){
        try{
            String sql = "select EXISTS (SELECT * FROM notification WHERE userId = \"" + userId + "\" AND relaylistId = \"" + relaylistId + "\" LIMIT 1) AS SUCCESS;";
            this.rs = this.executeQuery(sql);
            
            int result = 0;
            
            if(rs.next()) result = rs.getInt("success");
            db.close();
            
            return result > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    // 사용자 id로 해당 사용자에게 전송 된 알림을 받아오는 메소드
    public ArrayList<Notification> findAllByUserId(String userId){
        String sql = "SELECT * FROM notification WHERE userId = \"" + userId + "\";";
        
        this.rs = this.executeQuery(sql);
        ArrayList<Notification> notifies = this.resultSetToEntityList(rs);
        
        db.close();
        
        return notifies;
    }
}
