package WePLi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Controller.UserController;
import Dto.User.UserDto;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


/**
 *
 * @author Donghyeon <20183188>
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        //UserRepository ur = new UserRepository();
        
        LoginJFrame lj = new LoginJFrame();
        UserController uc = new UserController(lj);

        
        MainFrame mf = new MainFrame();
    }
}
