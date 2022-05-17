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
public class GenieSearchCrawler extends Crawler{
    
    private static GenieSearchCrawler Crawler = new GenieSearchCrawler();
 
    public static GenieSearchCrawler getCrawler(){
        return Crawler;
    }
    
    
    private GenieSearchCrawler() {
         this.URL = "https://www.genie.co.kr/search/searchSong?pagesize=50&query=";
    }
    
    
    public String getURL() {
        return URL;
    }
    

    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();
        
        //Element element = chartBody.get(0);
        
        //System.out.println(element);
//        System.out.println("image.genie.co.kr/" + element.select("img").attr("src"));           // 제목 크롤링 // 지니는 경로밖에 없어서 url붙여야함 // 이미지크기 600X600임 개큼
//        System.out.println(element.select(".t_point").text());                                  // 제목 크롤링
//        System.out.println(element.select(".artist").text());                                   // 가수 크롤림
//        System.out.println(element.select(".albumtitle").text());                                   // 앨범명 크롤링
//        
        //select(img) 태그를 가져온다.
        // .이 붙으면 클래스를 찾는다
        //attr 속성 정보 값을 가져온다.
        // #은 아이디를 가져온다
        
        
        for(Element element : chartBody){
            String coverImg = "image.genie.co.kr/" + element.select("img").attr("src");
            String title = element.select(".t_point").text();              // 노래제목 크롤링
            String singer = element.select(".artist").text();              // 가수 크롤링
            String album = element.select(".albumtitle").text();                // 앨범명 크롤링.
            
   
            
            SongDto song = SongDto.builder()
                    .coverImg(coverImg)
                    .title(title)
                    .singer(singer)
                    .album(album)
                    .build();
            
            
            songlist.add(song);
            //System.out.println(song.toString());
        }
        
        return songlist;
    }

}
    


