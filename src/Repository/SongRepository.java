/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Entity.Song;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kimkyeonghyun
 */
public class SongRepository extends EntityRepository<Song, String> {

    public SongRepository() { super.setEntity(new Song()); }

    // playBdsideTrack 과 Song join해서 Song을 찾는 함수
    public ArrayList<Song> findSonglistById(String playlistId) {
        // execueteQuery를 사용하는 경우 db close 필수
        ArrayList<Song> result = null;
        this.rs = executeQuery("select id, title, singer, image, album FROM song "
                             + "WHERE id IN (select songId from playBsideTrack WHERE playlistId = '" + playlistId + "');");
        try {
            while (rs.next()) result = resultSetToEntityList(rs);
            
            return result;
        }
        catch(Exception e){ 
            e.printStackTrace();
            return null;
        }
        finally{ db.close();}
    }
}
