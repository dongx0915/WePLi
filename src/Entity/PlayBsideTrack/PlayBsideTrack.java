/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity.PlayBsideTrack;

import Dto.PlayBsideTrack.PlayBsideTrackDto;
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
public class PlayBsideTrack {
    private String playlistId;  // PK, FK >- Playlist(id)
    private int songId;         // PK, FK >- Song(id)    
    
    public static PlayBsideTrack toEntity(PlayBsideTrackDto dto){
        return PlayBsideTrack.builder()
                             .playlistId(dto.getPlaylistId())
                             .songId(dto.getSongId())
                             .build();
    }
}
