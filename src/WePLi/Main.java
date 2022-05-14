package WePLi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Entity.User;
import Repository.UserRepository;
import java.sql.Date;

/**
 *
 * @author Donghyeon <20183188>
 */
public class Main {
    public static void main(String[] args) {
        UserRepository ur = new UserRepository();

        System.out.println(ur.update(User.builder().id("id5").pw("pw").cnt(100).date(new Date(new java.util.Date().getTime())).build()));
            
        for (User u : ur.findAll()) System.out.println(u);
    }
}
