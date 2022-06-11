/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.Playlist;


import Repository.BaseRepository.CrudRepository;
import Entity.PlayBsideTrack.PlayBsideTrack;
/**
 *
 * @author Donghyeon <20183188>
 */

public class PlayBsideTrackRepository extends CrudRepository<PlayBsideTrack, String>{
    public PlayBsideTrackRepository() { this.setEntity(new PlayBsideTrack()); }
    
    public boolean deleteByListId(String listId){
        String sql = "DELETE FROM playBsideTrack WHERE playlistId = \"" + listId + "\"";
        
        boolean result =  this.executeUpdate(sql);
        db.close();
        
        return result;
    }
}
