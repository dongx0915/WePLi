/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Entity.Song;

/**
 *
 * @author kimkyeonghyun
 */

public class SongRepository extends EntityRepository<Song, String> {
    public SongRepository(){ super.setEntity(new Song()); }
    
    // playBdsideTrack 에 Song_id를 찾는 함수
    public String findSongId(String id){
           
        return null;
    }
    
}
