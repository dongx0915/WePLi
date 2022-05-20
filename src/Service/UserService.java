/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dto.User.PwChangeDto;
import Dto.User.UserSignUpDto;
import Dto.User.UserDto;
import Entity.PwChange;
import Entity.User;
import Repository.UserRepository;
import javax.swing.JOptionPane;

/**
 *
 * @author kimkyeonghyun
 */
public class UserService {

    // Repository
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    // 로그인 성공 여부 확인
    public User login(UserDto dto) {
        User user = User.toEntity(dto);
        // 해당 ID의 유저 정보를 가져옴
        User target = userRepository.findById(user.getId());

        // id 가 없을 때 
        if (target == null) return null;

        return target.getPw().equals(user.getPw()) ? user : null;
    }

    // 회원가입 method
    public User SignUp(UserSignUpDto dto) {
        User target = userRepository.findById(dto.getId());

        // id 중복 아닐 때 
        if (target != null) return null;
            
        if (dto.getNewPw().equals(dto.getCheckPw())) {
            User user = new User(dto.getId(), dto.getNewPw());
            userRepository.save(user);
            
            return user;
        }
        
        return null;
    }

    // 비밀번호 변경 method
    public PwChange PwChange(PwChangeDto dto) {

        PwChange pwChange = PwChange.toEntity(dto);

        User target = userRepository.findById(pwChange.getId());

        // pw check
        if (!target.getPw().equals(pwChange.getPw())) {
            System.out.println("현재 비밀번호가 틀렸습니다.");
            return null;
        }

        // new password check
        if (pwChange.getNewPw().equals(pwChange.getCheckPw())) {

            // Update
            System.out.println("비밀번호 성공");
            User user = userRepository.update(User.builder()
                                                    .id(pwChange.getId())
                                                    .pw(pwChange.getNewPw())
                                                    .build());
            
        } else System.out.println("비밀번호가 맞지 않습니다.");
        
        return null;
    }
}
