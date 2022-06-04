/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.Relaylist;

import Entity.Relaylist.Relaylist;
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
public class RelaylistDto {
    public String id;       // PK
    public String title;
    public String author;   // FK >- User.Id
    public String inform;
    public int likes;
    public Date createTime;
    public String firstSongTitle;
    public String firstSongSinger;
    public String firstSongImage;
    public String firstSongAlbum;
    
    public static RelaylistDto createDto(Relaylist entity){
        return RelaylistDto.builder()
                            .id(entity.getId())
                            .title(entity.getTitle())
                            .author(entity.getAuthor())
                            .inform(entity.getInform())
                            .likes(entity.getLikes())
                            .createTime(entity.getCreateTime())
                            .firstSongTitle(entity.getFirstSongTitle())
                            .firstSongSinger(entity.getFirstSongSinger())
                            .firstSongImage(entity.getFirstSongImage())
                            .firstSongAlbum(entity.getFirstSongAlbum())
                            .build();
    }
}
