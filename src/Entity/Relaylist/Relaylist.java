/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity.Relaylist;
import Dto.Relaylist.RelaylistCreateDto;
import Entity.Playlist.Playlist;
import java.sql.Date;
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
public class Relaylist {
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
    
    public static Relaylist toEntity(RelaylistCreateDto dto){
        return Relaylist.builder()
                        .title(dto.getTitle())
                        .author(dto.getAuthor())
                        .inform(dto.getInform())
                        .likes(0)
                        .createTime(dto.getCreateTime())
                        .firstSongTitle(dto.getFirstSongTitle())
                        .firstSongSinger(dto.getFirstSongSinger())
                        .firstSongImage(dto.getFirstSongImage())
                        .firstSongAlbum(dto.getFirstSongAlbum())
                        .build();
    }
}
