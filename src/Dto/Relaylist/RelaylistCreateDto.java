/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.Relaylist;

import java.sql.Date;
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
public class RelaylistCreateDto {
    public String title;
    public String author;   // FK >- User.Id
    public String inform;
    public Date createTime;
    public String firstSongTitle;
    public String firstSongSinger;
    public String firstSongImage;
    public String firstSongAlbum;
    
    public static RelaylistCreateDto createDto(RelaylistDto dto){
        return RelaylistCreateDto.builder()
                                 .title(dto.getTitle())
                                 .author(dto.getAuthor())
                                 .inform(dto.getInform())
                                 .createTime(dto.getCreateTime())
                                 .firstSongTitle(dto.getFirstSongTitle())
                                 .firstSongSinger(dto.getFirstSongSinger())
                                 .firstSongImage(dto.getFirstSongImage())
                                 .firstSongAlbum(dto.getFirstSongAlbum())
                                 .build();
    }
}
