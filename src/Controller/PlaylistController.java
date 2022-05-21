/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.Playlist.PlaylistCreateDto;
import Dto.Playlist.PlaylistDto;
import Dto.Playlist.PlaylistUpdateDto;
import Entity.Playlist;
import Entity.Song;
import Service.SongService;
import Service.PlaylistService;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlaylistController{
    private PlaylistService playlistService;
    private SongService songService;

    
    public PlaylistController() { this.playlistService = new PlaylistService(); }

    public boolean createPlaylist(/* PlaylistCreateDto 필요 */) {
        PlaylistCreateDto dto = PlaylistCreateDto.builder()
                                                    .title("테스트 플레이리스트임")
                                                    .author("asdas")
                                                    .inform("테스트 플레이리스트 설명")
                                                    .createTime(new Date(new java.util.Date().getTime()))
                                                    .build();
                
        boolean result = playlistService.createPlaylist(dto);
        
        if(result){
            System.out.println("등록 성공");
            return true;
        }
        else{
            System.out.println("등록 실패");
            return false;
        }
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
    
    public void deletePlaylist(String id){
        boolean result =  playlistService.deletePlaylist(id);
        
        if(result){
            System.out.println("삭제 성공");
        }
        else{
            System.out.println("삭제 실패");
        }
    }
    
    public Playlist getPlaylist(String id){
        Playlist playlist = playlistService.getPlaylist(id);
        
        if(playlist == null) {
            System.out.println("조회 실패");
            return null;
        }
        
        return playlist;
    }
    
    public ArrayList<Playlist> getAllPlaylists(){
        ArrayList<Playlist> playlists = playlistService.getAllPlaylists();
        
        return playlists;
    }
    
    // playlist id -> SongService에 반환하기
    public Playlist findList(){
        
        String playListID = "ddd";
        
        
//        
//        PlaylistDto dto = PlaylistDto.builder()
//        
//        Song song = songService.findSongid(dto.getId());
//        
        
        return null;
    }
    

}
