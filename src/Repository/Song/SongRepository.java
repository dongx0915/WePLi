/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.Song;

import Dto.Song.SongDto;
import Repository.BaseRepository.CrudRepository;
import Entity.Song.Song;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kimkyeonghyun
 */
public class SongRepository extends CrudRepository<Song, String> {

    public SongRepository() { super.setEntity(new Song()); }

    // playBdsideTrack 과 Song join해서 Song을 찾는 함수
    public ArrayList<Song> findSonglistById(String playlistId) {
        // execueteQuery를 사용하는 경우 db close 필수
        ArrayList<Song> result = null;
        this.rs = executeQuery("SELECT id, title, singer, image, album FROM song "
                             + "WHERE id IN (select songId from playBsideTrack WHERE playlistId = '" + playlistId + "');");
        try {
            while (rs.next()) result = resultSetToEntityList(rs);
            
            return result;
        }
        catch(SQLException e){ 
            e.printStackTrace();
            return null;
        }
        finally{ db.close();}
    }
    
    public Song findSongByTitleAlbum(String title, String album){
        String sql = String.format("SELECT * FROM song WHERE title = \"%s\" AND album = \"%s\";", title, album);
        Song song = null;
        
        try{
            this.rs = this.executeQuery(sql);
            
            while(rs.next()) song = resultSetToEntity(rs);
            
            return song;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        finally{db.close();}
    }
    
    // 수록곡 가져오는 메소드
    public ArrayList<Song> getBsideTrack(String listId){
                
        // playBsideTrack과 song 테이블을 조인하여 수록곡을 가져옴
        String sql = "SELECT * FROM song WHERE id IN (SELECT songId FROM playBsideTrack WHERE playlistId = '" + listId + "');";
        this.rs = this.executeQuery(sql);
        
        ArrayList<Song> sideTrack = new ArrayList<>();
        try {
            while(rs.next()){
                sideTrack.add(Song.builder()
                                  .id(rs.getInt("id"))
                                  .title(rs.getString("title"))
                                  .singer(rs.getString("singer"))
                                  .album(rs.getString("album"))
                                  .image(rs.getString("image"))
                                  .build());
            }
            
            return sideTrack;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
