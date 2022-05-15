/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dto.UserDto;
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
            
            System.out.println("로그인 성공");
            return null;
            // 메인 페이지로 이동
        }
        
        
        return null;
    }
    
    
}
