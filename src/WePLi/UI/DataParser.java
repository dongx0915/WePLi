package WePLi.UI;

import Dto.Song.SongCreateDto;
import Dto.Song.SongDto;
import static WePLi.UI.ComponentSetting.convertSongToHtml;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
    
    // html에서 곡 정보를 추출
    public static SongCreateDto parseHtmlToSong(Object selectedRow){
        Document doc = Jsoup.parse(selectedRow.toString());
        Element element = doc.selectFirst("body");

        String title = element.select("#title").text();
        String album = element.select("#album").text();
        String singer = element.select("#singer").attr("value");
        String imageUrl = element.select("#image").attr("value").replace("resize/144", "resize/1000").replace("images/50", "images/1000");
        
        
        return SongCreateDto.builder()
                            .title(title)
                            .album(album)
                            .singer(singer)
                            .image(imageUrl)
                            .build();
    }
}
