/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;
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
    public String firstSongTitle ;
    public String firstSongSinger;
    public String firstSongImage;
    
    public static Playlist toEntity(/* RelaylistDto 필요 */){
        /* RelaylistDto 선언 후 구현 */
        return null;
    }
}
