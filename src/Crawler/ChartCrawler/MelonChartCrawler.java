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


    private static MelonChartCrawler Crawler = new MelonChartCrawler();
    

    
    public MelonChartCrawler() { this.URL = "https://www.melon.com/chart/day/index.htm"; }
    public static MelonChartCrawler getCrawler(){ return Crawler; }
    
    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();
        
        //Element element = chartBody.get(0);
        
        //select(img) 태그를 가져온다.
        // .이 붙으면 클래스를 찾는다
        //attr 속성 정보 값을 가져온다.
        for(Element element : chartBody){
            
            int rank = Integer.parseInt(element.select(".rank").text());   // 순위 크롤링
            String coverImg = element.select("img").attr("src");                 // 이미지 크롤링
            String title = element.select(".rank01").select("a").text();                  // 노래제목 크롤링
            String singer = element.select(".rank02").select("a").get(0).text();                  // 가수 크롤링
            String album = element.select(".rank03").text();                  // 앨범명 크롤링
            

            //System.out.println(singer);
            
            
            SongDto song = SongDto.builder()
                    .rank(rank)
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
    


