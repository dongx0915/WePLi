/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.User.UserSignUpDto;
import Dto.User.UserDto;
import Entity.User;
import Service.UserService;
import WePLi.UserFrame.LoginJFrame;
import WePLi.MainFrame;
import WePLi.UI.JPanelSetting;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author kimkyeonghyun
 */
public class UserController implements ActionListener {
    private static UserController userController = new UserController();
    private UserController() {}
    
    public static UserController getInstance(){ return userController; }
    
    // Login view
    private LoginJFrame loginFrame;

    // service
    private UserService userService;

    public UserController(LoginJFrame loginFrame) {
        this.loginFrame = loginFrame;

        this.loginFrame.setLoginListner(this);
        this.loginFrame.setSignUpListner(this);

        this.userService = new UserService();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 로그인 이벤트
        if (e.getSource() == loginFrame.getLoginBtn()) {
            login();
        } else if (e.getSource() == loginFrame.getSignUpBtn()) {
            signUp();
        }
    }

    private User login() {
        // id, pw 변수에 저장
        JLabel errorLabel = loginFrame.getErrorLabel();
        errorLabel.setText("");

        String id = loginFrame.getIdTextField().getText();
        String pw = loginFrame.getPwTextField().getText();

        UserDto dto = UserDto.builder()
                .id(id)
                .pw(pw)
                .build();

        User user = userService.login(dto);

        if (user != null) {
            loginFrame.dispose();
            new MainFrame();
        }

        errorLabel.setText("아이디 또는 비밀번호를 확인하세요");
        return null;
    }

    private User signUp() {
        String id = loginFrame.getSignUpIdTextField().getText();
        String newPw = loginFrame.getSignUpPwTextField().getText();
        String checkPw = loginFrame.getCheckPwField().getText();

        UserSignUpDto dto = new UserSignUpDto(id, newPw, checkPw);

        User user = userService.SignUp(dto);
        if(user == null){
            JOptionPane.showMessageDialog(null, "입력 내용을 확인해주세요.", "WePLi.", JOptionPane.PLAIN_MESSAGE);
            return null;
        }
        
        JOptionPane.showMessageDialog(null, "회원가입이 완료 되었습니다.", "WePLi.", JOptionPane.PLAIN_MESSAGE);
        JPanelSetting.changePanel(loginFrame.getPanelList(), loginFrame.getLoginPanel());
        
        return user;
    }
}
