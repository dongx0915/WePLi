/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity.Notification;

import Dto.Notification.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Date;
/**
 *
 * @author Donghyeon <20183188>
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    
    private int id;             //PK
    private String userId;      // FK >- User.id
    private String content;
    private Date date;
    private String relaylistId; // FK >- Relaylist.id
    
    public static Notification toEntity(NotificationDto dto){
        return Notification.builder()
                           .userId(dto.getUserId())
                           .content(dto.getContent())
                           .date(dto.getDate())
                           .relaylistId(dto.getRelaylistId())
                           .build();
    }
    
}
