/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dto.User.PwChangeDto;
import Dto.User.SignUpDto;
import Dto.User.UserDto;
import Entity.PwChange;
import Entity.SignUp;
import Entity.User;
import Repository.UserRepository;

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
    public User login(UserDto dto){
        System.out.println(dto);
        
        User user = User.toEntity(dto);
        
        User target = userRepository.findById(user.getId());
        
        // id 가 없을 때 
        if(target == null) {
            
            // 로그인 실패
            System.out.println("로그인 실패");
            return null;
        }
        
        // id 가 있을 때 
        if(target != null){
            
            // pw check
            if(target.getPw().equals(user.getPw())){
                 System.out.println("로그인 성공");
            }
            else{
                System.out.println("로그인 실패");
            }            
            return null;
        }
        
        
        return null;
    }
    
    
    // 회원가입 method
    public SignUp SignUp(SignUpDto dto){
                
        SignUp signUp = SignUp.toEntity(dto);
        
        User target = userRepository.findById(signUp.getId());
        
        // id 중복 아닐 때 
        if(target == null){
            
            if(signUp.getPw().equals(signUp.getPw2())){
                System.out.println("회원가입 완료");
                // save
                User user = userRepository.save(User.builder().id(dto.getId()).pw(dto.getPw()).build());
                if(user == null){
                    System.out.println("Faild");        
                }
                else{
                    System.out.println("Success");
                }
            }
            else{
                System.out.println("비밀번호가 맞지 않습니다.");
            }
            
        }
        // id 중복 일 때
        else{
            System.out.println("중복된 ID가 있습니다.");
        }
        
        return null;
    }
    
    // 비밀번호 변경 method
    public PwChange PwChange(PwChangeDto dto){
        
        PwChange pwChange = PwChange.toEntity(dto);
                
        User target = userRepository.findById(pwChange.getId());        
        
        // pw check
        if(target.getPw().equals(pwChange.getPw())){
            
            // new password check
            if(pwChange.getNewPw().equals(pwChange.getCheckPw())){
                   
            // Update
            System.out.println("비밀번호 성공");
            User user = userRepository.update(User.builder()
                    .id(pwChange.getId())
                    .pw(pwChange.getNewPw())
                    .build());
                    
            }
            else{
                System.out.println("비밀번호가 맞지 않습니다.");
            }
                
        }
        else{
            System.out.println("현재 비밀번호가 틀렸습니다.");
        }
            
        
        return null;
    }
    
}
