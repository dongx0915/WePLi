package WePLi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Controller.UserController;
import Repository.UserRepository;

/**
 *
 * @author Donghyeon <20183188>
 */
public class Main {
    public static void main(String[] args) {
        UserRepository ur = new UserRepository();
        
        LoginJFrame login = new LoginJFrame();
        
        UserController uc = new UserController(login);
    }
}
