/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler.SearchCrawler;

import Crawler.Crawler;
import Dto.Song.SongDto;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

/**
 *
 * @author joon
 */
public class GenieSearchCrawler extends Crawler{
    private static GenieSearchCrawler crawler = new GenieSearchCrawler();
    private GenieSearchCrawler() { this.URL = "https://www.genie.co.kr/search/searchSong?pagesize=50&query="; }
    
    public static GenieSearchCrawler getCrawler(){ return crawler; }

    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();
        

        
        for(Element element : chartBody){
            String coverImg = "https://image.genie.co.kr/" + element.select("img").attr("src");
            String title = element.select(".title").text();              // 노래제목 크롤링
            title = title.replaceAll("^TITLE ","");
            title = title.replaceAll("^19금 ","");
            String singer = element.select(".artist").text();              // 가수 크롤링
            String album = element.select(".albumtitle").text();                // 앨범명 크롤링.
            
            SongDto song = SongDto.builder()
                    .coverImg(coverImg)
                    .title(title)
                    .singer(singer)
                    .album(album)
                    .build();
            
            
            songlist.add(song);
        }
        return songlist;
    }
}
    


