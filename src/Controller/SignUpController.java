/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Controller;
import Dto.User.SignUpDto;
import Entity.SignUp;
import Service.UserService;
import WePLi.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import WePLi.SignUpFrame;


/**
 *
 * @author kimkyeonghyun
 */
public class SignUpController implements ActionListener{

    // SignUp view
    private SignUpFrame signUpFrame;
    
    // service
    private UserService userService;
    
    public SignUpController(SignUpFrame signUpFrame) {
     this.signUpFrame = signUpFrame;
     this.signUpFrame.setSignUpListner(this);
     this.userService = new UserService();
    }


    @Override
    public void actionPerformed(ActionEvent e){
        
        String id = signUpFrame.getidTextField().getText();
        String pw = signUpFrame.getpwTextField().getText();
        String pw2 = signUpFrame.getpw2TextField().getText();
        
        
        SignUpDto dto = SignUpDto.builder()
                .id(id)
                .pw(pw)
                .pw2(pw2).build();

        SignUp signUp = userService.SignUp(dto);
        MainFrame mf;
        if(signUp != null) mf = new MainFrame();
        
    }
    
}
