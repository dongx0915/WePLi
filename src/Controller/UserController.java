/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.User.UserDto;
import Entity.User;
import Service.UserService;
import WePLi.LoginJFrame;
import WePLi.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author kimkyeonghyun
 */
public class UserController implements ActionListener{
    
    // view
    private LoginJFrame loginFrame;
    // service
    private UserService userService;

    public UserController(LoginJFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.loginFrame.setLoginListner(this);
        this.userService = new UserService();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("클릭 된 컴포넌트 : " + e.getSource().getClass().getSimpleName());
        
        // id, pw 변수에 저장
        String id = loginFrame.getIdTextField().getText();
        String pw = loginFrame.getPwTextField().getText();
        
        UserDto dto = UserDto.builder().id(id).pw(pw).build();

        User user = userService.login(dto);  
        MainFrame mf;
        if(user != null) mf = new MainFrame();
    }
}
