/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;


import Dto.User.PwChangeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 *
 * @author kimkyeonghyun
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PwChange {
    private String id;
    private String pw;
    private String newPw;
    private String checkPw;
    
    public static PwChange toEntity(PwChangeDto dto){
        return PwChange.builder()
                .id(dto.getId())
                .pw(dto.getPw())
                .newPw(dto.getNewPw())
                .checkPw(dto.getCheckPw())
                .build();
    }
    
}
