/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Relaylist;

import Dto.Playlist.PlaylistDto;
import Dto.Relaylist.RelaylistCreateDto;
import Dto.Relaylist.RelaylistDto;
import Entity.Playlist.Playlist;
import Entity.Relaylist.Relaylist;
import RelayListTimer.RelaylistTimer;
import Repository.Relaylist.RelaylistRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelaylistService {
    private RelaylistRepository relaylistRepository;

    public RelaylistService() { this.relaylistRepository = new RelaylistRepository(); }
    
    // 릴레이리스트 등록 메소드
    public RelaylistDto createRelaylist(RelaylistCreateDto dto){
        // 1. 엔티티 변환
        Relaylist relaylist = Relaylist.toEntity(dto);
        
        // 2. DB에 저장
        Relaylist result = relaylistRepository.save(relaylist);
        
        return Objects.isNull(result) ? null : RelaylistDto.createDto(result);
    }
    
    // RelaylistId로 릴레이리스트를 가져오는 메소드
    public RelaylistDto getRelaylist(String relaylistId){
        Relaylist relaylist = relaylistRepository.findById(relaylistId);
        
        return Objects.isNull(relaylist) ? null : RelaylistDto.createDto(relaylist);
    }
    
    // 모든 릴레이리스트를 가져오는 메소드
    public ArrayList<RelaylistDto> getRelaylists(){
        return (ArrayList) relaylistRepository.findAll()
                                              .stream().map(relaylist -> RelaylistDto.createDto(relaylist))
                                              .collect(Collectors.toList());
    }

    // 특정 유저의 릴레이리스트 조회
    public ArrayList<RelaylistDto> getUserRelaylists(String userId){
        ArrayList<Relaylist> playlist = relaylistRepository.findAllByUserId(userId);
        
        if(Objects.isNull(playlist)) return null;
        
        // Dto 리스트로 변환 후 리턴
        return (ArrayList) playlist.stream()
                                   .map(r -> RelaylistDto.createDto(r))
                                   .collect(Collectors.toList());
    }
    
    // 종료되지 않은 릴레이리스트를 체크하는 메소드
    public void checkRelaylistTime(){
        // 모든 릴레이리스트를 가져옴
        ArrayList<RelaylistDto> relaylist = getRelaylists();
        
        long current = System.currentTimeMillis();
        long limitTime = new Date(1000 * 60 * 60 * 24 * 2).getTime();     // 릴레이리스트 기간은 1일로 지정  
        
        // 종료시간 체크 후 진행 중인 릴레이리스트가 있으면 타이머 생성
        for (RelaylistDto list : relaylist) {
            long listTime = list.getCreateTime().getTime();

            // 현재 시간 < (생성 시간 + 릴레이 기간) 이면 아직 종료되지 않은 리스트
            if(current < listTime + limitTime){
                // 타이머 생성 필요 (listTime + limitTime) - current
                new RelaylistTimer(list, 10000);
            }
                // 이미 종료된 리스트면 알림이 보내졌는지 확인하고 전송
                // NotifyController한테 요청
        }
    }
}
