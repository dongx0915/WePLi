/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler.ChartCrawler;

import Crawler.Crawler;
import Dto.Song.SongDto;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

/**
 *
 * @author joon
 */
public class BugsChartCrawler extends Crawler{

    private static BugsChartCrawler crawler = new BugsChartCrawler();
    private BugsChartCrawler() { 
        this.URL = "https://music.bugs.co.kr/chart/track/day/total"; 
    }
    
    public static BugsChartCrawler getCrawler(){ return crawler; }
    
    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();

        // 음악 사이트의 차트 테이블에서 곡 정보 추출
        for(Element element : chartBody){
            int rank = Integer.parseInt(
                        element.select(".ranking").select("strong").text());    // 순위 크롤링
            String coverImg = element.select("img").attr("src");                // 이미지 크롤링
            String title = element.select(".title").select("a").text();         // 노래제목 크롤링
            String singer = element.select(".artist").select("a").get(0).text();// 가수 크롤링
            String album = element.select(".album").attr("title");              // 앨범명 크롤링
            
            // SongDto로 변환
            SongDto song = SongDto.builder()
                    .rank(rank)
                    .image(coverImg)
                    .title(title)
                    .singer(singer)
                    .album(album)
                    .build();
            
            songlist.add(song);
        }
        
        return songlist;
    }
}
    


