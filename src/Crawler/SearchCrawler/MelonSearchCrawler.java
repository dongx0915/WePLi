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
public class MelonSearchCrawler extends Crawler{
    private static MelonSearchCrawler crawler = new MelonSearchCrawler();
    private MelonSearchCrawler() { this.URL = "https://www.melon.com/search/song/index.htm?q="; }
    
    public static MelonSearchCrawler getCrawler(){ return crawler; }
    
    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();

        for(Element element : chartBody){
            String title = element.select(".ellipsis").select(".odd_span").select("b").text();               // 노래제목 크롤링
            String singer = element.select("#artistName").select(".checkEllipsisSongdefaultList").text();                 // 가수 크롤링
            String album = element.select(".t_left").select("div[class='wrap']").text();                  // 앨범명 크롤링
            

            SongDto song = SongDto.builder()
                    .title(title)
                    .singer(singer)
                    .album(album)
                    .build();
            
            songlist.add(song);
        }
        
        return songlist;
    }
}
    


