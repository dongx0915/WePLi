/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.Playlist.PlaylistCreateDto;
import Dto.Playlist.PlaylistDto;
import Dto.Playlist.PlaylistUpdateDto;
import Entity.Playlist.Playlist;
import Entity.Song.Song;
import Service.SongService2;
import Service.Playlist.PlaylistService;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlaylistController{
    private static PlaylistController playlistController = new PlaylistController();
    
    private PlaylistService playlistService;

    private PlaylistController() { this.playlistService = new PlaylistService(); }
    
    public static PlaylistController getInstance(){ return playlistController; }
    
    // 플레이리스트 등록 메소드
    public PlaylistDto createPlaylist(PlaylistCreateDto playlistDto) {
        PlaylistDto result = playlistService.createPlaylist(playlistDto);
        
        return Objects.isNull(result) ? null : result;
    }
    
    public Playlist updatePlaylist(/* PlaylistUpdateDto 필요 */){
        PlaylistUpdateDto dto = PlaylistUpdateDto.builder()
                                                    .id("P0000005")
                                                    .title("수정 된 플레이리스트")
                                                    .inform("수정 된 설명")
                                                    .createTime(new Date(new java.util.Date().getTime()))
                                                    .build();
        
        Playlist result = playlistService.updatePlaylist(dto);
        
        if(result == null) {
            System.out.println("업데이트 실패");
            return null;
        }
        
        return result;
    }
    
    // 플레이리스트 삭제
    public boolean deletePlaylist(String listId){
        return playlistService.deletePlaylist(listId);
    }
    
    // PlaylistId로 플레이리스트 가져오는 메소드
    public PlaylistDto getPlaylist(String id){
        PlaylistDto playlist = playlistService.getPlaylist(id);
        
        if(Objects.isNull(playlist)) {
            System.err.println("조회 실패");
            return null;
        }
        
        return playlist;
    }
    
    public ArrayList<PlaylistDto> getAllPlaylists(){
        return playlistService.getAllPlaylists();
    }

}
