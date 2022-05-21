/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Entity.Song;
import java.sql.SQLException;

/**
 *
 * @author kimkyeonghyun
 */

public class SongRepository extends EntityRepository<Song, String> {
    public SongRepository(){ super.setEntity(new Song()); }
    
    
    // playBdsideTrack 과 Song join해서 Song을 찾는 함수
    public ArrayList<Song> findSong(String playListID){
        
        try{
            con = db.connect();
            
            //String sql = "SELECT * FROM " + tableName + " WHERE " + idFieldName + " = ?;";
            String sql = " SELECT id, title, singer, image, album FROM playBsideTrack t JOIN song s on t.songid = s.id WHERE " + idFieldName + " = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setObject(1, playListID);
            
            rs = pstmt.executeQuery();
            
            
            
            // rs를 Entity 리스트로 변환 후 반환
            return resultSetToEntity(rs);
        }
        catch(SQLException e){ return null; }
        finally{ db.close();}
        
        return null;
    }
    
}
