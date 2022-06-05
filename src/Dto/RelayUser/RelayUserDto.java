/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.RelayUser;

import Entity.RelayUser.RelayUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Donghyeon <20183188>
 */

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelayUserDto {
    private String relaylistId; // PK
    private String userId;      // PK
    private int voteCnt;
    
    public static RelayUserDto createDto(RelayUser entity) {
        return RelayUserDto.builder()
                           .relaylistId(entity.getRelaylistId())
                           .userId(entity.getUserId())
                           .voteCnt(entity.getVoteCnt())
                           .build();
    }
}
