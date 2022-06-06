/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.User.UserSignUpDto;
import Dto.User.UserDto;
import Entity.User.User;
import Service.User.UserService;
import WePLi.UserFrame.LoginJFrame;
import WePLi.MainFrame;
import WePLi.UI.JPanelSetting;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author kimkyeonghyun
 */
public class UserController {
    private static UserController userController = new UserController();
    public static UserController getInstance(){ return userController; }
    
    private UserService userService;
    
    private UserController() { this.userService = new UserService(); }
    
    public UserDto login(String id, String pw) {
        UserDto dto = new UserDto(id, pw);

        UserDto user = userService.login(dto);

        // 로그인 실패 시 null 리턴
        if (Objects.isNull(user)) return null;
        
        return user;
    }

    public UserDto signUp(String id, String newPw, String checkPw) {
        UserSignUpDto dto = new UserSignUpDto(id, newPw, checkPw);

        UserDto user = userService.SignUp(dto);
        
        return user;
    }
}
