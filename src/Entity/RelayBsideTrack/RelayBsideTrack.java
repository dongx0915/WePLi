/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity.RelayBsideTrack;

import Dto.RelayBsideTrack.RelayBsideTrackDto;
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
public class RelayBsideTrack {
    private String relaylistId;
    private int songId;
    private int likes;
    
    public static RelayBsideTrack toEntity(RelayBsideTrackDto dto){
        return RelayBsideTrack.builder()
                              .relaylistId(dto.getRelaylistId())
                              .songId(dto.getSongId())
                              .likes(dto.getLikes())
                              .build();
    }
}
