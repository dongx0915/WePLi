/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler.SearchCrawler;

import Crawler.Crawler;
import Dto.SongDto;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

/**
 *
 * @author joon
 */
public class BugsSearchCrawler extends Crawler{
    private static BugsSearchCrawler Crawler = new BugsSearchCrawler();

    private BugsSearchCrawler() { this.URL = "https://music.bugs.co.kr/search/track?q="; }
    
    // 싱글턴 패턴
    public static BugsSearchCrawler getCrawler(){ return Crawler; }
    
    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();
        
        //select(img) 태그를 가져온다.
        // .이 붙으면 클래스를 찾는다
        //attr 속성 정보 값을 가져온다.
        for(Element element : chartBody){
            String coverImg = element.select("img").attr("src");                 // 이미지 크롤링
            String title = element.select(".title").text();                  // 노래제목 크롤링
            String singer = element.select(".artist").select("a").get(0).text();                 // 가수 크롤링
            String album = element.select(".left").text();                  // 앨범명 크롤링
            
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
    


