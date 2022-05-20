/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler.ChartCrawler;

import Crawler.Crawler;
import Dto.SongDto;
import java.util.ArrayList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;

/**
 *
 * @author joon
 */
public class GenieChartCrawler extends Crawler{
    
    private static GenieChartCrawler crawler = new GenieChartCrawler();
    private GenieChartCrawler() { this.URL = "https://genie.co.kr/chart/top200?pgsize=100&rtm=N"; }
    
    public static GenieChartCrawler getCrawler(){ return crawler; }
    
    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();
        

        //select(img) 태그를 가져온다.
        // .이 붙으면 클래스를 찾는다
        //attr 속성 정보 값을 가져온다.
        // #은 아이디를 가져온다
     
        for(Element element : chartBody){
            
            int rank = Integer.parseInt(element.select(".number").text().split(" ")[0]);
            String coverImg = element.select("img").attr("src");                 // 이미지 크롤링 - url ???,0으로 끝남
            String title = element.select(".info").select(".title").text();                  // 노래제목 크롤링
            title = title.replaceAll("\\(Prod. by","\\(Prod.");
            title = title.replaceAll("^19금 ","");
            
            String singer = element.select(".info").select(".artist").text();                  // 가수 크롤링
            String album = element.select(".info").select(".albumtitle").text();                  // 앨범명 크롤링    
            
            //System.out.println(singer);
            
            SongDto song = SongDto.builder()
                    .rank(rank)
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
    


