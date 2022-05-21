/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Dto.Song.SongDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author joon
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongChart {
    
    private int id;
    public String title;
    public String singer;
    public String album;
    public String image;
    
     public static SongChart toEntity(SongDto dto){
        return SongChart.builder()
                .title(dto.getTitle())
                .singer(dto.getSinger())
                .album(dto.getAlbum())
                .image(dto.getImage())
                .build();
    }
    
}
