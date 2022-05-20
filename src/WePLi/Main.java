package WePLi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Controller.PwChangeController;
import Controller.SignUpController;
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

//        LoginJFrame lj = new LoginJFrame();
//        UserController uc = new UserController(lj);
        
//        SignUpFrame sp = new SignUpFrame();
//        SignUpController sc = new SignUpController(sp);
          
//        MainFrame mj = new MainFrame();
          PwChangeJFrame pj = new PwChangeJFrame();
          PwChangeController pc = new PwChangeController(pj);
    }
}
