/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.RelayUser;

import Entity.Playlist.Playlist;
import Entity.RelayUser.RelayUser;
import Repository.BaseRepository.CrudRepository;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelayUserRepository extends CrudRepository<RelayUser, String>{
    public RelayUserRepository() { this.setEntity(new RelayUser()); }
    
    public RelayUser findById(String relaylistId, String userId){
        String sql = String.format("SELECT * FROM relayUser WHERE relaylistId = \"%s\" AND userId = \"%s\";", relaylistId, userId);
        
        this.rs = this.executeQuery(sql);
        RelayUser result = this.resultSetToEntity(rs);
        
        db.close();
        
        return result;
    }
    
    public boolean updateRelayUser(RelayUser relayUser){
        String sql = String.format("UPDATE relayUser SET voteCnt = voteCnt + %d WHERE relaylistId = \"%s\" AND userId =\"%s\";", 
                                    relayUser.getVoteCnt(), relayUser.getRelaylistId(), relayUser.getUserId());
        
        boolean result = this.executeUpdate(sql);
        
        return result;
    }
    
}
