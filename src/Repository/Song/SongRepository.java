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
public class SongRepository extends CrudRepository<Song, Integer> {

    public SongRepository() { super.setEntity(new Song()); }
    
    // 제목과 앨범으로 노래 검색하는 메소드
    public Song findSongByTitleAlbum(String title, String singer){
        String sql = String.format("SELECT * FROM song WHERE title = \"%s\" AND singer = \"%s\";", title, singer);
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
    
    public ArrayList<Song> getTop10SideTrack(String listId){
        String sql = "SELECT * FROM song s JOIN relayBsideTrack bSide ON s.id = bSide.songId "
                   + "WHERE relaylistId = \"" + listId + "\" ORDER BY likes DESC LIMIT 10;";
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
    
    // 수록곡 가져오는 메소드 (BsideTrack의 테이블 이름으로 플레이리스트, 릴레이리스트를 구분함)
    public ArrayList<Song> getBsideTrack(String listId){
                
        String bSideTrack = listId.matches("^P[0-9]{7}$") ? "playBsideTrack" : "relayBsideTrack";
        String listIdField = listId.matches("^P[0-9]{7}$") ? "playlistId" : "relaylistId";
        
        // playBsideTrack과 song 테이블을 조인하여 수록곡을 가져옴
        String sql = String.format("SELECT * FROM song WHERE id IN (SELECT songId FROM %s WHERE %s = \"%s\");", bSideTrack, listIdField, listId);
        
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
