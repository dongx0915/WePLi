/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;
import Dto.Playlist.PlaylistCreateDto;
import Dto.Playlist.PlaylistUpdateDto;
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
public class Playlist {
    public String id;       // PK
    public String title;
    public String author;   // FK >- User.Id
    public String inform;
    public int likes;
    public int downloads;
    public Date createTime;
    
    public void patch(PlaylistUpdateDto dto){
        // Title은 비어있을 수 없음
        String title = dto.getTitle();
        if(!"".equals(title)) this.title = title;
        
        // 설명은 비어있어도 되므로 그냥 업데이트
        this.inform = dto.getInform();
        
        // 시간은 수정 날짜로 변경
        this.createTime = dto.getCreateTime();
    }
    
    public static Playlist toEntity(PlaylistCreateDto dto){
        return Playlist.builder()
                .id(null)
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .inform(dto.getInform())
                .createTime(dto.getCreateTime())
                .likes(0)
                .downloads(0)
                .build();
    }
}
