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

    public static String convertPlaylistToHtml(String title, String author, String inform) {
        return String.format("<html>\n"
                + "<head>\n"
                + "    <style>\n"
                + "        p {margin-left: 20px;}"
                + "        #author{\n"
                + "            color: #a2a2a2;\n"
                + "            font-size: 10px;\n"
                + "        }\n"
                + "        #inform{\n"
                + "            color: #5D5D5D;\n"
                + "            font-size: 11px;\n"
                + "            margin-top: 10px;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <p id = \"title\">%s</p>\n"
                + "    <p id = \"author\">%s</p>\n"
                + "    <p id = \"inform\">%s</p>\n"
                + "</body>\n"
                + "</html>", title, author, inform);
    }
    
    public static String convertSongToHtml(String title, String album) {
        return String.format("<html>\n"
                + "<head>\n"
                + "    <style> #album{color: #a2a2a2;} </style>"
                + "</head>\n"
                + "<body>\n"
                + "    <p id=\"title\">%s</p>\n"
                + "    <p id=\"album\">%s</p>\n"
                + "</body>\n"
                + "</html>", title, album);
    }
}
