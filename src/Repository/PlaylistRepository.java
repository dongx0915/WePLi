/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Entity.Playlist;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlaylistRepository extends EntityRepository<Playlist, String>{
    public PlaylistRepository() { super.setEntity(new Playlist());}
    

    // 플레이리스트 담은 곡 삭제문 생성
    public String deleteplayBsideTrack(String playlistId, String songId){
        
        
        return null;
    }
    
}
