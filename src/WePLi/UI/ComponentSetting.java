/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WePLi.UI;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Donghyeon <20183188>
 */
public class ComponentSetting {

    /* 웹 이미지 url을 ImageIcon으로 변경하는 메소드 */
    public static ImageIcon imageToIcon(String url, int width, int height) {
        try {
            /* 웹 이미지 가져오기 */
            ImageIcon icon = new ImageIcon(new URL(url));

            /* 이미지 사이즈 조정 */
            return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
