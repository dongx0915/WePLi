/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Notification;

import Dto.Notification.NotificationDto;
import Entity.Notification.Notification;
import Repository.Notification.NotificationRepository;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Donghyeon <20183188>
 */
public class NotificationService {
    private NotificationRepository notificationRepository;

    public NotificationService() { this.notificationRepository = new NotificationRepository(); }
    
    // 알림 저장하는 메소드
    public NotificationDto addNotification(NotificationDto notifyDto){
        Notification notify = Notification.toEntity(notifyDto);
        
        Notification result = notificationRepository.save(notify);
        
        return NotificationDto.createDto(result);
    }
    
    // 모든 유저에게 알림을 보내는 메소드
    public ArrayList<NotificationDto> notifyAllUsers(ArrayList<NotificationDto> notifyDto){
        // Dto를 Entity 리스트로 변환
        ArrayList<Notification> notifies = (ArrayList) notifyDto
                                                        .stream().map(notify -> Notification.toEntity(notify))
                                                        .collect(Collectors.toList());
        
        // 모두 DB에 저장
        ArrayList<Notification> result =  notificationRepository.saveAll(notifies);
        
        // Dto로 다시 변환하여 리턴
        return Objects.isNull(result) ? null : (ArrayList) result
                                                            .stream().map(notify -> NotificationDto.createDto(notify))
                                                            .collect(Collectors.toList());
    }
}
