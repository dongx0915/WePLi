/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dto.Playlist.PlaylistCreateDto;
import Dto.Playlist.PlaylistUpdateDto;
import Entity.Playlist;
import Repository.PlaylistRepository;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlaylistService {
    private PlaylistRepository playlistRepository;

    public PlaylistService() { this.playlistRepository = new PlaylistRepository(); }
    
    public boolean createPlaylist(PlaylistCreateDto dto){
        // Dto를 Entity로 변환
        Playlist playlist = Playlist.toEntity(dto);
        
        Playlist result = playlistRepository.save(playlist);
        
        if(result != null) return true;
        
        return false;
    }
    
    public Playlist updatePlaylist(PlaylistUpdateDto dto){
        // 업데이트할 플레이리스트를 가져옴
        Playlist target = playlistRepository.findById(dto.getId());
        
        if(target == null) return null;
        
        // 변경 부분 반영
        target.patch(dto);
        
        // 업데이트 결과 값 받아옴
        Playlist result = playlistRepository.update(target);
        
        return result;
    }
    
    public boolean deletePlaylist(String id){
        // 엮여있는 Comment 테이블과 PlaylistArticle 테이블까지 삭제해야함
        
        return playlistRepository.deleteById(id);
    }
    
    public Playlist getPlaylist(String id){
        return playlistRepository.findById(id);
    }
    
    public ArrayList<Playlist> getAllPlaylists(){
        return playlistRepository.findAll();
    }
    
    
    // playList 내용 출력
    public Playlist PlaylistList(String id){
            
        // playBsideTrack
        
        
        // Song
        
        // song 만들어 놓고
        // id로 playBsidTrack에 있는 리스트를 가져온다
        /*
            레파지토리에서 리스트 가져오는 코드
        
        */
        
        // 가져온 리스트로 리스트에 있는 id와 일치하는 song테이블의 값 가져오기 
        
        
       
                
        
        
        
        
        return null;
    }
    

}
