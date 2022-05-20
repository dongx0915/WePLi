/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto.User;

import Entity.User;
import lombok.*;

/**
 *
 * @author kimkyeonghyun
 */

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDto {
    private String id;
    private String newPw;
    private String checkPw;
}
