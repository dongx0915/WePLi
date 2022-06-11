/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.Relaylist;

import Entity.Playlist.Playlist;
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
    
    // 내 릴레이리스트에 저장
    public Relaylist saveMyRelaylist(Relaylist entity){
        return super.save(entity);
    }
    
    @Override // 릴레이리스트 저장 + 게시글 등록(공유)
    public Relaylist save(Relaylist entity) {
        Relaylist relaylist = super.save(entity);
        String sql = "INSERT INTO relaylist_article VALUES('" + relaylist.getId() + "');";
        
        this.executeUpdate(sql);
        db.close();
        
        return relaylist;
    }
    
    @Override // 게시글로 등록된 릴레이리스트만 조회
    public ArrayList<Relaylist> findAll() {
        String sql = "SELECT * FROM relaylist WHERE id IN (SELECT * FROM relaylist_article);";
        this.rs = this.executeQuery(sql);
        
        ArrayList<Relaylist> result = resultSetToEntityList(rs);
        db.close();
        
        return result;
    }
}
