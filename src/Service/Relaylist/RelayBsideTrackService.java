/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Relaylist;

import Dto.RelayBsideTrack.RelayBsideTrackDto;
import Entity.RelayBsideTrack.RelayBsideTrack;
import Repository.Relaylist.RelayBsideTrackRepository;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelayBsideTrackService {
    private RelayBsideTrackRepository relayBsideTrackRepository;

    public RelayBsideTrackService() { this.relayBsideTrackRepository = new RelayBsideTrackRepository(); }
    
    public ArrayList<RelayBsideTrackDto> addRelayBsideTrack(ArrayList<RelayBsideTrackDto> bSideTrackDto){
        // Entity 리스트로 변경
        ArrayList<RelayBsideTrack> bSideTrack = (ArrayList) bSideTrackDto
                                                            .stream().map(sideTrack -> RelayBsideTrack.toEntity(sideTrack))
                                                            .collect(Collectors.toList());
        
        // DB에 수록곡 저장
        
        for (RelayBsideTrack song : bSideTrack) {
            RelayBsideTrack result = relayBsideTrackRepository.findById(song.getRelaylistId(), song.getSongId());
            
            // 기존에 없는 노래만 저장
            if(Objects.isNull(result)) song = relayBsideTrackRepository.save(song);
            else song = result;
        }
        
        // Dto 리스트로 변환 후 반환
        return (ArrayList) bSideTrack
                            .stream().map(sideTrack -> RelayBsideTrackDto.createDto(sideTrack))
                            .collect(Collectors.toList());
    }
    
    public boolean updateVoteCnt(ArrayList<RelayBsideTrackDto> votedSongDtoList){
        ArrayList<RelayBsideTrack> votedList = (ArrayList) votedSongDtoList
                                                            .stream().map(votedSong -> RelayBsideTrack.toEntity(votedSong))
                                                            .collect(Collectors.toList());
        
        return relayBsideTrackRepository.updateVoteCnt(votedList);
    }
}
