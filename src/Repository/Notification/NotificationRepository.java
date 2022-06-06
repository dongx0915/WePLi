/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.Notification;

import Entity.Notification.Notification;
import Repository.BaseRepository.CrudRepository;

/**
 *
 * @author Donghyeon <20183188>
 */
public class NotificationRepository extends CrudRepository<Notification, Integer>{
    public NotificationRepository() { this.setEntity(new Notification());}
}
