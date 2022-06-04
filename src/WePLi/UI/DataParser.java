package WePLi.UI;

import Dto.Song.SongDto;
import static WePLi.UI.ComponentSetting.convertSongToHtml;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Donghyeon <20183188>
 */
public class DataParser {
    public static Object[][] songDtoToObject(ArrayList<SongDto> songArray) {
        Object[][] values = new Object[songArray.size()][];
        
        for (int i = 0; i < songArray.size(); i++) {
            SongDto song = songArray.get(i);

            values[i] = new Object[]{i + 1, 
                                    ComponentSetting.imageToIcon(song.getImage(), 60, 60), 
                                    convertSongToHtml(song.getTitle(), song.getAlbum(), song.getImage(), song.getSinger()),
                                    song.getSinger() };
        }

        return values;
    }
}
