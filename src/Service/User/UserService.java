/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.User;

import Dto.User.PwChangeDto;
import Dto.User.UserSignUpDto;
import Dto.User.UserDto;
import Entity.User.User;
import Repository.User.UserRepository;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author kimkyeonghyun
 */
public class UserService {
    // Repository
    private UserRepository userRepository;

    public UserService() { this.userRepository = new UserRepository(); }

    // 로그인 성공 여부 확인
    public UserDto login(UserDto dto) {
        // Dto를 Entity로 변경
        User user = User.toEntity(dto);
        
        // 해당 ID의 유저 정보를 가져옴
        User target = userRepository.findById(user.getId());
        
        // id 가 없을 때 
        if (Objects.isNull(target)) return null;

        // 패스워드 비교
        return target.getPw().equals(user.getPw()) ? UserDto.createDto(user) : null;
    }

    // 회원가입 method
    public UserDto SignUp(UserSignUpDto dto) {
        User target = userRepository.findById(dto.getId());

        // 이미 아이디가 존재하는 경우
        if (!Objects.isNull(target)) return null;
            
        // User를 DB에 저장하고 리턴
        User user = new User(dto.getId(), dto.getNewPw());
        userRepository.save(user);
        
        // 회원가입 결과를 조회하여 리턴 (회원가입 실패 시 null)
        User result = userRepository.findById(user.getId());
        
        return Objects.isNull(result) ? null : UserDto.createDto(result);
    }

    // 모든 유저를 받아오는 메소드
    public ArrayList<UserDto> getAllUsers(){
        return (ArrayList) userRepository.findAll()
                                         .stream().map(user -> UserDto.createDto(user))
                                         .collect(Collectors.toList());
    }
}
