/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity.RelayUser;

import Dto.RelayUser.RelayUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Donghyeon <20183188>
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelayUser {
    private String relaylistId; // PK
    private String userId;      // PK
    private int voteCnt;
    
    public static RelayUser toEntity(RelayUserDto dto){
        return RelayUser.builder()
                        .relaylistId(dto.getRelaylistId())
                        .userId(dto.getUserId())
                        .voteCnt(dto.getVoteCnt())
                        .build();
    }
}
