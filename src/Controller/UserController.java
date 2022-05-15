/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import WePLi.LoginJFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author kimkyeonghyun
 */
public class UserController implements MouseListener{
    
    private LoginJFrame loginFrame;

    
    public UserController(LoginJFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.loginFrame.setLoginListner(this);
    }
    
    private String id;
    private String pw;
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("로그인 버튼 클릭 됨");
        id = loginFrame.getIdTextField().getText();
        pw = loginFrame.getPwTextField().getText();
        System.out.println(id);
        System.out.println(pw);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
    
}
