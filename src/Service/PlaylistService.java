/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dto.Playlist.PlaylistCreateDto;
import Dto.Playlist.PlaylistDto;
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
    
    
    // playlist id 리턴 해주기
    public Playlist findListId(PlaylistDto dto){
            
        Playlist target = playlistRepository.findById(dto.getId());
        
        if(target == null){
            System.out.println("저장된 곡이 없습니다.");
        }
        // target return
        
        return target;
    }
    
    
    // playlistcontroller 받은 값 받아서 playlistRepository불러서 해당 레코드 삭제
    public Playlist playBsideTrack(String playlistid, String songid){
        
        // PlaylistRepository 에 값 넘겨서 delete 실행
        playlistRepository.deleteplayBsideTrack(playlistid, songid);
        
        return null;
    }
    
    public static void main(String[] args) {
        
        String pid = "P0000002";
        String sid = "22";

        
        PlaylistRepository a = new PlaylistRepository();
        a.deleteplayBsideTrack(pid, sid);
    }
}
