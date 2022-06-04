/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.Playlist;


import Dto.Playlist.PlaylistDto;
import Dto.Song.SongDto;
import Repository.BaseRepository.CrudRepository;
import Entity.Playlist.Playlist;
import Repository.Song.SongRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlaylistRepository extends CrudRepository<Playlist, String>{
    public PlaylistRepository() { super.setEntity(new Playlist());}
    
    
    // 플레이리스트 저장 + 게시글 등록(공유)
    public Playlist sharePlaylist(Playlist entity) {
        Playlist playlist = super.save(entity);
        String sql = "INSERT INTO playlist_article VALUES('" + playlist.getId() + "');";
        
        this.executeUpdate(sql);
        db.close();
        
        return playlist;
    }
    
    // 플레이리스트 담은 곡 삭제문 생성
    public void deletePlayBsideTrack(String playlistId, String songId){
        // 삭제 sql 구문
        executeUpdate("DELETE FROM playBsideTrack "
                + "WHERE playlistid = '" + playlistId + "' and songid = '" + songId + "';");
      
        System.out.println("DELETE FROM playBsideTrack "
                + "WHERE playlistid = '" + playlistId + "' and songid = '" + songId + "';");
        db.close();
    }

    @Override // 게시글로 등록된 플레이리스트 조회
    public ArrayList<Playlist> findAll() {
        String sql = "SELECT * FROM playlist WHERE id IN (SELECT * FROM playlist_article);";
        this.rs = this.executeQuery(sql);
        
        return resultSetToEntityList(rs);
    }
}
