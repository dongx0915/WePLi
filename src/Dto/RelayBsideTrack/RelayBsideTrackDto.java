/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.RelayBsideTrack;

import Entity.RelayBsideTrack.RelayBsideTrack;
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
public class RelayBsideTrackDto {
    private String relaylistId;
    private int songId;
    private int likes;
    
    public static RelayBsideTrackDto createDto(RelayBsideTrack entity){        
        return RelayBsideTrackDto.builder()
                                 .relaylistId(entity.getRelaylistId())
                                 .songId(entity.getSongId())
                                 .likes(entity.getLikes())
                                 .build();
    }

    @Override
    public boolean equals(Object obj) {
        RelayBsideTrackDto target = (RelayBsideTrackDto) obj;
        
        return (this.relaylistId.equals(target.relaylistId) && this.songId == target.songId);
    }
    
    
}
