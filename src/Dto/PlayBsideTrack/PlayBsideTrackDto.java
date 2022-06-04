/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.PlayBsideTrack;

import Entity.PlayBsideTrack.PlayBsideTrack;
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
public class PlayBsideTrackDto {
    private String playlistId;
    private int songId;
    
    public static PlayBsideTrackDto createPlayBsideTrackDto(PlayBsideTrack entity){
        return PlayBsideTrackDto.builder()
                                .playlistId(entity.getPlaylistId())
                                .songId(entity.getSongId())
                                .build();
    }
}
