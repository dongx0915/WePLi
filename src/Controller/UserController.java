/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.User.UserDto;
import Service.UserService;
import WePLi.LoginJFrame;
import WePLi.SignUpFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author kimkyeonghyun
 */
public class UserController implements MouseListener{
    
    // Login view
    private LoginJFrame loginFrame;
    
    // Sign view
    private SignUpFrame signupFrame;
    
    // service
    private UserService userService;

    public UserController(LoginJFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.loginFrame.setLoginListner(this);
        this.userService = new UserService();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getSource());
        
        System.out.println("로그인 버튼 클릭 됨");
        
        // id, pw 변수에 저장
        String id = loginFrame.getIdTextField().getText();
        String pw = loginFrame.getPwTextField().getText();
        
        UserDto dto = UserDto.builder().id(id).pw(pw).build();

        userService.login(dto);        
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
