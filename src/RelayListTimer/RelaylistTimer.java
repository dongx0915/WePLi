/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RelayListTimer;

import Controller.NotificationController;
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
    // 릴레이리스트의 완료 시간을 체크하는 타이머
    public RelaylistTimer(RelaylistDto list, long time) {
        Timer timer = new Timer();
        
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                // 시간이 다 되면 알림을 보내고 타이머 종료
                notificationController.sendNotification(list);
                timer.cancel();
            }
        };
     
        timer.schedule(task, time);
    }
}
