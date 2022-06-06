package WePLi.UI;

import Dto.Song.SongCreateDto;
import Dto.Song.SongDto;
import Entity.SongChart.SongChart;
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
    public static Object[][] songDtoToObject(ArrayList<SongDto> songArray, int startIndex) {
        Object[][] values = new Object[songArray.size()][];
        
        for (int i = 0; i < songArray.size(); i++) {
            SongDto song = songArray.get(i);
            
            values[i] = new Object[]{
                                    startIndex + (i + 1), 
                                    ComponentSetting.imageToIcon(song.getImage(), 60, 60), 
                                    convertSongToHtml(song.getId(), song.getTitle(), song.getAlbum(), song.getImage(), song.getSinger()),
                                    song.getSinger() };
        }

        return values;
    }
    
    
    public static Object[][] songChartToObject(ArrayList<SongChart> songArray) {
        Object[][] values = new Object[songArray.size()][];

        for (int i = 0; i < songArray.size(); i++) {
            SongChart song = songArray.get(i);

            values[i] = new Object[]{song.getId(), 
                                    ComponentSetting.imageToIcon(song.getImage(), 60, 60), 
                                    convertSongToHtml(song.getId(), song.getTitle(), song.getAlbum(), song.getImage(), song.getSinger()),
                                    song.getSinger() };
        }

        return values;
    }
    
    public static String convertSongToHtml(int songId, String title, String album, String image, String singer) {
        return String.format("<html>\n"
                + "<head>\n"
                + "    <style> #album{color: #a2a2a2;} </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <input id = \"songId\" tpye = \"text\" value = \"%d\" hidden>\n"
                + "    <p id=\"title\">%s</p>\n"
                + "    <p id=\"album\">%s</p>\n"
                + "    <input id = \"image\" type = \"text\" value = \"%s\" hidden>\n"                                
                + "    <input id = \"singer\" type = \"text\" value = \"%s\" hidden>\n"         
                + "</body>\n"
                + "</html>", songId, title, album, image, singer);
    }
        
    public static String convertListToHtml(String listId, String title, String author, String inform) {
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
                + "    <input id = \"listId\"type=\"text\" value = \"%s\" hidden>\n"
                + "    <p id = \"title\">%s</p>\n"
                + "    <p id = \"author\">%s</p>\n"
                + "    <p id = \"inform\">%s</p>\n"
                + "</body>\n"
                + "</html>", listId, title, author, inform);
    }
        
    public static SongDto parseHtmlToSongDto(Object value){
        Document doc = Jsoup.parse(value.toString());
        Element element = doc.selectFirst("body");
        
        int songId = Integer.parseInt(element.select("#songId").attr("value"));
        String title = element.select("#title").text();
        String album = element.select("#album").text();
        String singer = element.select("#singer").attr("value");
        String imageUrl = element.select("#image").attr("value").replace("resize/144", "resize/1000").replace("images/50", "images/1000");
        
        return SongDto.builder()
                      .id(songId)
                      .title(title)
                      .album(album)
                      .singer(singer)
                      .image(imageUrl)
                      .build();
    }
    
    // html에서 곡 정보를 추출
    public static SongCreateDto parseHtmlToSongCreateDto(Object value){
        Document doc = Jsoup.parse(value.toString());
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
