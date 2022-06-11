/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Notification;

import Dto.Notification.NotificationDto;
import Dto.Relaylist.RelaylistDto;
import Dto.User.UserDto;
import Observer.Observer;
import Observer.Subject;
import Service.Notification.NotificationService;
import Service.User.UserService;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Objects;
/**
 *
 * @author Donghyeon <20183188>
 */
public class NotificationController implements Subject {
    private static NotificationController notificationController = new NotificationController();
    public static NotificationController getInstance(){ return notificationController; }
    
    private NotificationService notificationService;
    private UserService userService;
    
    private ArrayList<Observer> observers;
    private NotificationController() { 
        this.notificationService = new NotificationService(); 
        this.userService = new UserService();
        this.observers = new ArrayList<>();
    }

    // 옵저버 메소드
    @Override public void registerObserver(Observer o) { this.observers.add(o); }
    @Override public void removeObserver(Observer o) { this.observers.remove(o); }
    @Override public void notifyObservers() { for (Observer observer : observers) observer.update(); }
    
    // 알림을 저장하는 메소드
    /*
    * RelayTimer 클래스에서 릴레이리스트 하나마다 타이머 스레드를 생성함
    * 따라서, 스레드가 호출하는 메소드는 sendNotification에는 동기화가 필요
    */
    public synchronized ArrayList<NotificationDto> sendNotification(RelaylistDto list){
        // 모든 유저 정보를 가져옴
        ArrayList<UserDto> users = userService.getAllUsers();
        
        String listId = list.getId();
        String content = getContent(list);
        
        ArrayList<NotificationDto> notifies = new ArrayList<>();
        
        // 모든 유저에게 전송할 알림을 생성
        for (UserDto user : users) {
            notifies.add(NotificationDto.builder()
                                        .relaylistId(listId)
                                        .userId(user.getId())
                                        .content(content)
                                        .date(new Date(System.currentTimeMillis()))
                                        .build());
        }
        
        // 모든 유저에게 알림을 전송
        ArrayList<NotificationDto> result = notificationService.notifyAllUsers(notifies);
        
        // 알림이 정상적으로 저장되면 옵저버를 호출하여 뷰 화면에 알림 표시
        if(!Objects.isNull(result)) notifyObservers();
        
        return result;
    }
    
    // 해당 유저에게 전송된 알림들을 모두 가져오는 메소드
    public ArrayList<NotificationDto> getNotifications(String userId){
        ArrayList<NotificationDto> notifications = notificationService.getNotifications(userId);
        
        return Objects.isNull(notifications) ? null : notifications;
    }
    // 알림으로 전송할 Content를 생성하는 메소드
    private String getContent(RelaylistDto list){
        return String.format(
                  "<html>\n"
                + "<head>\n"
                + "    <style> #inform{color: #a2a2a2;} </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <p id=\"title\">< %s > 릴레이리스트가 완성 되었습니다.</p>\n"
                + "    <p id=\"inform\">%s</p>\n"
                + "    <input id = \"image\" type = \"text\" value = \"%s\" hidden>\n"         
                + "</body>\n"
                + "</html>", list.getTitle(), list.getInform(), list.getFirstSongImage());
    }
}
