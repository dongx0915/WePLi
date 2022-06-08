/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.Relaylist;

import Repository.BaseRepository.CrudRepository;
import Entity.Relaylist.Relaylist;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelaylistRepository extends CrudRepository<Relaylist, String>{
    public RelaylistRepository() { super.setEntity(new Relaylist());}
    
    // 해당 유저의 릴레이리스트만 가져오는 메소드
    public ArrayList<Relaylist> findAllByUserId(String userId){
        String sql = "SELECT * FROM relaylist WHERE author = \"" + userId + "\";";
        this.rs = this.executeQuery(sql);
        
        ArrayList<Relaylist> result = resultSetToEntityList(rs);
        db.close();
        
        return result;
    }
}
