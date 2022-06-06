/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.Playlist;

import Dto.Song.SongDto;
import Entity.Playlist.Playlist;
import java.sql.Date;
import java.util.ArrayList;
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
public class PlaylistDto {
    public String id;       // PK
    public String title;
    public String author;   // FK >- User.Id
    public String inform;
    public int likes;
    public int downloads;
    public Date createTime;
    public String image;
    public ArrayList<SongDto> sideTrack;
    
    public static PlaylistDto createDto(Playlist entity){
        return PlaylistDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .inform(entity.getInform())
                .likes(entity.getLikes())
                .downloads(entity.getDownloads())
                .createTime(entity.getCreateTime())
                .image(entity.getImage())
                .build();
    }
}
