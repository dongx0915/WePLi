/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.User.PwChangeDto;
import Entity.PwChange;
import Service.User.UserService;
import WePLi.MainFrame;
import WePLi.UserFrame.PwChangeJFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author kimkyeonghyun
 */
public class PwChangeController implements ActionListener{
    
    // PwChange view
    private PwChangeJFrame pwChangeFrame;
    
    // Service
    private UserService userService;
    
    public PwChangeController(PwChangeJFrame pwChangeFrame){
        this.pwChangeFrame = pwChangeFrame;
        this.pwChangeFrame.setPwChangeListner(this);
        this.userService = new UserService();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // 로그인을 admin 으로 일단 지정하고 시작
        String id = "admin";
        
        String pw  = pwChangeFrame.getPwTextField().getText();
        String newPw = pwChangeFrame.getNewPwTextField().getText();
        String chekcPw = pwChangeFrame.getCheckPwTextField().getText();
        
        PwChangeDto dto = PwChangeDto.builder()
                .id(id)
                .pw(pw)
                .newPw(newPw)
                .checkPw(chekcPw)
                .build();        
        
        PwChange pwChange = userService.PwChange(dto);        
        MainFrame mf;
        if(pwChange != null) mf = new MainFrame();
    }
    
}
