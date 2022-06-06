/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.RelayBsideTrack;

import Entity.RelayBsideTrack.RelayBsideTrack;
import Repository.BaseRepository.CrudRepository;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelayBsideTrackRepository extends CrudRepository<RelayBsideTrack, String>{
    public RelayBsideTrackRepository() { this.setEntity(new RelayBsideTrack()); }
    
    public boolean updateVoteCnt(ArrayList<RelayBsideTrack> votedSong) {
        for (RelayBsideTrack song : votedSong) {
            String sql = String.format("UPDATE relayBsideTrack SET likes = likes + 1 WHERE relaylistId = \"%s\" AND songId = \"%d\";", 
                                        song.getRelaylistId(), song.getSongId());
        
            boolean result = this.executeUpdate(sql);
            
            if(!result){        // 업데이트 과정에서 오류 발생시 false 리턴
                db.close();
                return false;
            }
        }
        
        db.close();
        return true;
    }
    
    public RelayBsideTrack findById(String relaylistId, int songId){
        String sql = String.format("SELECT * FROM relayBsideTrack WHERE relaylistId = \"%s\" AND songId = \"%d\";",
                                    relaylistId, songId);
        
        this.rs = this.executeQuery(sql);
        RelayBsideTrack result = resultSetToEntity(rs);
        
        db.close();
        
        return result;
    }
}
