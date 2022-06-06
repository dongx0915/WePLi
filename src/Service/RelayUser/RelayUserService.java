/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.RelayUser;

import Dto.RelayUser.RelayUserDto;
import Entity.RelayUser.RelayUser;
import Repository.RelayUser.RelayUserRepository;
import java.util.Objects;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelayUserService {
    private RelayUserRepository relayUserRepository;

    public RelayUserService() { this.relayUserRepository = new RelayUserRepository(); }
    
    // RelayUser 테이블에 사용자의 투표 정보를 저장
    public boolean addRelayUser(RelayUserDto relayUserDto){
        RelayUser relayUser = RelayUser.toEntity(relayUserDto);
        boolean result = true;
        
        // 기존 데이터를 검색
        RelayUser target = relayUserRepository.findById(relayUser.getRelaylistId(), relayUser.getUserId());
        
        // 기존 데이터가 없는 경우 삽입
        if(Objects.isNull(target)) relayUserRepository.save(relayUser);
        else result = relayUserRepository.updateRelayUser(relayUser);
        
        return result;
    }
    
    public RelayUserDto getRelayUser(String relaylistId, String userId){
        System.out.println("listid = " + relaylistId + " userId " + userId);
        RelayUser relayUser = relayUserRepository.findById(relaylistId, userId);
        
        // 존재하지 않는 유저이면 먼저 저장
        
        
        if(Objects.isNull(relayUser)) {
            relayUser = RelayUser.builder()
                             .relaylistId(relaylistId)
                             .userId(userId)
                             .voteCnt(0)
                             .build();
            
            relayUserRepository.save(relayUser);
        }
        
        // RelayUser 테이블은 복합키를 사용하므로 save에서 엔티티를 그대로 리턴해주지 않음
        // 다시 검색하여 결과를 가져옴
        relayUser = relayUserRepository.findById(relaylistId, userId);
        
        return RelayUserDto.createDto(relayUser);
    }
}
