/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler.ChartCrawler;

import Crawler.Crawler;
import Dto.SongDto;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

/**
 *
 * @author joon
 */
public class MelonChartCrawler extends Crawler{
    private static MelonChartCrawler crawler = new MelonChartCrawler();
    private MelonChartCrawler() { this.URL = "https://www.melon.com/chart/index.htm"; }
    
    public static MelonChartCrawler getCrawler(){ return crawler; }
    
    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();
        
        //Element element = chartBody.get(0);
        
        //select(img) 태그를 가져온다.
        // .이 붙으면 클래스를 찾는다
        //attr 속성 정보 값을 가져온다.
        for(Element element : chartBody){
            
            String coverImg = element.select("img").attr("src");                 // 이미지 크롤링
            String title = element.select(".rank01").text();                  // 노래제목 크롤링
            String singer = element.select("div.rank02 > a").text();                  // 가수 크롤링
            
            String album = element.select(".rank03").text();                  // 앨범명 크롤링
            

            //System.out.println(singer);
            
            
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
    


