/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.RelayBsideTrack.RelayBsideTrackDto;
import Service.RelayBsideTrack.RelayBsideTrackService;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelayBsideTrackController {
    private static RelayBsideTrackController relayBsideTrackController = new RelayBsideTrackController();
    public static RelayBsideTrackController getInstance(){ return relayBsideTrackController; }
    
    private RelayBsideTrackService relayBsideTrackService;
    private RelayBsideTrackController() { this.relayBsideTrackService = new RelayBsideTrackService(); }
    
    public ArrayList<RelayBsideTrackDto> addRelayBsideTrack(ArrayList<RelayBsideTrackDto> bSideTrackDto){
        return relayBsideTrackService.addRelayBsideTrack(bSideTrackDto);
    }
    
    public boolean updateVoteCnt(ArrayList<RelayBsideTrackDto> votedSongDto){
        return relayBsideTrackService.updateVoteCnt(votedSongDto);
    }
}
