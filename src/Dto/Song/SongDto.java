
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.Song;

import Entity.Song.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author joon
 */
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongDto implements Comparable<SongDto> {

    private int id;
    private int rank;
    private String image; // 커버 이미지 없을 경우 대체 이미지
    private String title;
    private String singer;
    private String album;

    public static SongDto createSongDto(Song entity){
        return SongDto.builder()
                    .id(entity.getId())
                    .image(entity.getImage())
                    .title(entity.getTitle())
                    .singer(entity.getSinger())
                    .album(entity.getAlbum())
                    .build();
    }
    
    @Override
    public int compareTo(SongDto dto) {
        return this.getRank() >= dto.getRank() ? -1 : 1;
    }
    
}
