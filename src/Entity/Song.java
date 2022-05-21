/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author joon
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    
    private int id;
    public String title;
    public String singer;
    public String album;
    public String image;
    
}
