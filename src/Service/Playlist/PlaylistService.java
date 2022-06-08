/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Playlist;

import Dto.Playlist.PlaylistCreateDto;
import Dto.Playlist.PlaylistDto;
import Dto.Playlist.PlaylistUpdateDto;
import Entity.Playlist.Playlist;
import Repository.Playlist.PlaylistRepository;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlaylistService {
    private PlaylistRepository playlistRepository;

    public PlaylistService() { this.playlistRepository = new PlaylistRepository(); }
    
    // 플레이리스트 생성
    public PlaylistDto createPlaylist(PlaylistCreateDto dto){
        // Dto를 Entity로 변환
        Playlist playlist = Playlist.toEntity(dto);
        
        Playlist result = playlistRepository.sharePlaylist(playlist);
        
        return Objects.isNull(result) ? null : PlaylistDto.createDto(result);
    }
    
    // 플레이리스트 수정
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
    
    // 플레이리스트 삭제
    public boolean deletePlaylist(String id){
        // 엮여있는 Comment 테이블과 PlaylistArticle 테이블까지 삭제해야함
        
        return playlistRepository.deleteById(id);
    }
    
    // 플레이리스트 아이디로 플레이리스트를 가져옴
    public PlaylistDto getPlaylist(String id){
        Playlist playlist = playlistRepository.findById(id);
        
        return Objects.isNull(playlist) 
                ? null 
                : PlaylistDto.createDto(playlist);
    }
    
    // 게시글에 등록된 플레이리스트 조회
    public ArrayList<PlaylistDto> getAllPlaylists(){
       // Dto 리스트로 변환 후 리턴
       return (ArrayList) playlistRepository.findAll()
                                            .stream().map(playlist -> PlaylistDto.createDto(playlist))
                                            .collect(Collectors.toList());
    }
    
    // 특정 유저의 플레이리스트 조회
    public ArrayList<PlaylistDto> getUserPlaylists(String userId){
        ArrayList<Playlist> playlist = playlistRepository.findAllByUserId(userId);
        
        if(Objects.isNull(playlist)) return null;
        
        // Dto 리스트로 변환 후 리턴
        return (ArrayList) playlist.stream()
                                   .map(p -> PlaylistDto.createDto(p))
                                   .collect(Collectors.toList());
    }
//    // playlist id 리턴 해주기
//    public Playlist findListId(PlaylistDto dto){
//            
//        Playlist target = playlistRepository.findById(dto.getId());
//        
//        if(target == null){
//            System.out.println("저장된 곡이 없습니다.");
//        }
//        // target return
//        
//        return target;
//    }
    
    
    // playlistcontroller 받은 값 받아서 playlistRepository불러서 해당 레코드 삭제
    public Playlist playBsideTrack(String playlistid, String songid){
        
        // PlaylistRepository 에 값 넘겨서 delete 실행
        playlistRepository.deletePlayBsideTrack(playlistid, songid);
        
        return null;
    }
}
