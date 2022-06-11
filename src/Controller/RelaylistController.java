/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.Playlist.PlaylistDto;
import Dto.RelayBsideTrack.RelayBsideTrackDto;
import Dto.RelayUser.RelayUserDto;
import Dto.Relaylist.RelaylistCreateDto;
import Dto.Relaylist.RelaylistDto;
import Service.RelayUser.RelayUserService;
import Service.Relaylist.RelayBsideTrackService;
import Service.Relaylist.RelaylistService;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelaylistController {
    private static RelaylistController relaylistController = new RelaylistController();
    public static RelaylistController getInstance(){ return relaylistController; }
    
    private RelaylistService relaylistService; 
    private RelayBsideTrackService relayBsideTrackService;
    private RelayUserService relayUserService;
    
    private RelaylistController() { 
        this.relaylistService = new RelaylistService(); 
        this.relayBsideTrackService = new RelayBsideTrackService();
        this.relayUserService = new RelayUserService();
    }
    
    // 릴레이리스트 생성 메소드
    public RelaylistDto createRelaylist(RelaylistCreateDto dto){
        return relaylistService.createRelaylist(dto);
    }
    
    // RelaylistId로 릴레이리스트 가져오는 메소드
    public RelaylistDto getRelaylist(String relaylistId){
        return relaylistService.getRelaylist(relaylistId);
    }
    
    // 공유된 릴레이리스트를 다운로드하는 메소드
//    public ArrayList<RelaylistDto> downloadList(String relaylistId, String nickname){
//        RelaylistDto relaylist = relaylistService.getRelaylist(relaylistId);
//        
//        // 작성자를 본인으로 변경
//        relaylist.setAuthor(nickname);
//    }
    
    // 모든 릴레이리스트를 가져오는 메소드
    public ArrayList<RelaylistDto> getRelaylists(){
         return relaylistService.getRelaylists();
    }
    
    // 특정 유저의 플레이리스트 조회
    public ArrayList<RelaylistDto> getUserRelaylists(String userId){
        return relaylistService.getUserRelaylists(userId);
    }
    
    // 릴레이리스트가 완성 되었는지 시간을 체크하는 메소드
    public void checkRelaylistTime(){
        relaylistService.checkRelaylistTime();
    }
    
    // 릴레이리스트의 수록곡을 추가하는 메소드
    public ArrayList<RelayBsideTrackDto> addRelayBsideTrack(ArrayList<RelayBsideTrackDto> bSideTrackDto){
        return relayBsideTrackService.addRelayBsideTrack(bSideTrackDto);
    }
    
    // 사용자가 투표한 곡의 투표 수를 갱신하는 메소드
    public boolean updateVoteCnt(ArrayList<RelayBsideTrackDto> votedSongDto){
        return relayBsideTrackService.updateVoteCnt(votedSongDto);
    }
    
    // 릴레이리스트에 참여한 유저를 등록하는 메소드
    public boolean addRelayUser(RelayUserDto relayUserDto){
        return relayUserService.addRelayUser(relayUserDto);
    }
    
    // 릴레이리스트에 참여한 유저의 정보를 가져오는 메소드
    public RelayUserDto getRelayUser(String relaylistId, String userId){
        return relayUserService.getRelayUser(relaylistId, userId);
    }
}
