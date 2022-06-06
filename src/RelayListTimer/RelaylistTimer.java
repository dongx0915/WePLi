/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RelayListTimer;

import Controller.Notification.NotificationController;
import Dto.Notification.NotificationDto;
import Dto.Relaylist.RelaylistDto;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelaylistTimer {
    private NotificationController notificationController = NotificationController.getInstance();
    
    public RelaylistTimer(RelaylistDto list, long time) {
        Timer timer = new Timer();
        System.out.println(list.getId() + " 생성 됨");
        
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                notificationController.sendNotification(list);
                timer.cancel();
            }
        };
        
        timer.schedule(task, time);
    }
}
