/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.Relaylist.RelaylistCreateDto;
import Dto.Relaylist.RelaylistDto;
import Service.Relaylist.RelaylistService;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelaylistController {
    private static RelaylistController relaylistController = new RelaylistController();
    private RelaylistService relaylistService; 
    
    private RelaylistController() { this.relaylistService = new RelaylistService(); }
    
    public static RelaylistController getInstance(){ return relaylistController; }
    
    // 릴레이리스트 생성 메소드
    public RelaylistDto createRelaylist(RelaylistCreateDto dto){
        return relaylistService.createRelaylist(dto);
    }
    
    // RelaylistId로 릴레이리스트 가져오는 메소드
    public RelaylistDto getRelaylist(String relaylistId){
        return relaylistService.getRelaylist(relaylistId);
    }
    
    // 모든 릴레이리스트를 가져오는 메소드
    public ArrayList<RelaylistDto> getRelaylists(){
         return relaylistService.getRelaylists();
    }
    
    public void checkRelaylistTime(){
        relaylistService.checkRelaylistTime();
    }
}
