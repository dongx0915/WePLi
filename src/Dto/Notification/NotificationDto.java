/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.Notification;

import Entity.Notification.Notification;
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
public class NotificationDto {
    private String userId;      // FK >- User.id
    private String content;
    private Date date;
    private String relaylistId; // FK >- Relaylist.id
    
    public static NotificationDto createDto(Notification entity){
        return NotificationDto.builder()    
                              .userId(entity.getUserId())
                              .content(entity.getContent())
                              .date(entity.getDate())
                              .relaylistId(entity.getRelaylistId())
                              .build();
    }
}
