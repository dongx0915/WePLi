/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Playlist;

import Dto.PlayBsideTrack.PlayBsideTrackDto;
import Entity.PlayBsideTrack.PlayBsideTrack;
import Repository.Playlist.PlayBsideTrackRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlayBsideTrackService {
    private PlayBsideTrackRepository playBsideTrackRepository;

    public PlayBsideTrackService() {
        this.playBsideTrackRepository = new PlayBsideTrackRepository();
    }
    
    
    public ArrayList<PlayBsideTrackDto> addPlayBsideTrack(ArrayList<PlayBsideTrackDto> bSideTrackDto){
        // bSideTrackDto 리스트를 PlayBsideTrack(엔티티) 리스트로 변환
        ArrayList<PlayBsideTrack> bSideTrack = (ArrayList) bSideTrackDto
                                                            .stream().map(dto -> PlayBsideTrack.toEntity(dto))
                                                            .collect(Collectors.toList());
        ArrayList<PlayBsideTrack> result = new ArrayList<>();
        
        for (PlayBsideTrack pbt : bSideTrack) {
            
            PlayBsideTrack target = playBsideTrackRepository.findPlayBsideTrack(pbt.getPlaylistId(), pbt.getSongId());
            
            // 존재하지 않으면 삽입
            if(Objects.isNull(target)) playBsideTrackRepository.save(pbt);
            else result.add(target);             

        }
        
        // PlayBsideTrack(엔티티) 리스트를 DTO 리스트로 변환
        return (ArrayList) result
                            .stream().map(playBsideTrack -> PlayBsideTrackDto.createPlayBsideTrackDto(playBsideTrack))
                            .collect(Collectors.toList());
    }
    
    // 특정 플레이리스트의 수록곡을 모두 삭제하는 메소드
    public boolean deleteBsideTrack(String playlistId){
        return playBsideTrackRepository.deleteByListId(playlistId);
    }
}
