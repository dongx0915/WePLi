/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Relaylist;

import Dto.Relaylist.RelaylistCreateDto;
import Dto.Relaylist.RelaylistDto;
import Entity.Relaylist.Relaylist;
import Repository.Relaylist.RelaylistRepository;
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
}
