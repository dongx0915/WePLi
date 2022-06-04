/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.PlayBsideTrack.PlayBsideTrackDto;
import Service.PlayBsideTrack.PlayBsideTrackService;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlayBsideTrackController {
    private static PlayBsideTrackController playBsideTrackController = new PlayBsideTrackController();
    public static PlayBsideTrackController getInstance(){ return playBsideTrackController; }
    
    private PlayBsideTrackService playBsideTrackService;
    private PlayBsideTrackController() { this.playBsideTrackService = new PlayBsideTrackService(); }
    
    public ArrayList<PlayBsideTrackDto> addPlayBsideTrack(ArrayList<PlayBsideTrackDto> bSideTrackDto){
        return playBsideTrackService.addPlayBsideTrack(bSideTrackDto);
    }
    
}
