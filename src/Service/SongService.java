/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Crawler.Crawler;
import Crawler.CrawlerFactory.MusicCrawlerFactory;
import Dto.SongDto;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author joon
 */
public class SongService {
    
    MusicCrawlerFactory musicFactory = new MusicCrawlerFactory();
       
    public ArrayList<Crawler> musicChart(){
            
        ArrayList<Crawler> crawlChart ;
        crawlChart = musicFactory.getChartCrawler(); // 크롤링한 리스트 
            
        //chartIntegrate(CrawlChart); 음악 순위 합치기 구현중
        
        System.out.println("--------------------------- 인기 차트 -------------------------------"); 
        ;
        for(int i = 0 ; i < crawlChart.size(); i++){
            String Charturl = crawlChart.get(i).getURL();
            System.out.println(Charturl);
            
            for (SongDto dto : crawlChart.get(i).getSongList(Charturl)) {
                System.out.println("Title = " + dto.getTitle() + " Singer = " + dto.getSinger() + " Album = " + dto.getAlbum());
            }
            //System.out.println("전체 리스트 : " + crawlChart.get(i).getSongList(Charturl)); // 전체 리스트 출력
            
           // System.out.println(CrawlChart.get(i).getSongList(Charturl).get(1).getTitle()); // 음악사 별 1등 노래 출력
            }            
        
            return null;
        }
        
    public ArrayList<SongDto> chartIntegrate(ArrayList<Crawler> crawlChart){
        ArrayList<SongDto> songList = new ArrayList<>();
            
        for(int i = 0 ; i< crawlChart.size(); i++){
            String Charturl = crawlChart.get(i).getURL();
            for(int j = 0 ; j < crawlChart.get(i).getSongList(Charturl).size() ; j++)
            {
                    
            }
        }
        return null;
    }

        
        

        public ArrayList<SongDto> musicSearch(String type, String keyword){
            
            System.out.println("--------------------------- 노래 검색 -------------------------------"); 
            
            Crawler crawler ;
            crawler = musicFactory.getSearchCrawler(type);
            String SearchURL=crawler.getURL()+keyword;
            System.out.println(crawler.getSongList(SearchURL));
            
            return crawler.getSongList(SearchURL);
        }
        
 
        
        public static void main(String[] args) {
            
            SongService a = new SongService();
            
            a.musicSearch("melon","My Universe");
            a.musicSearch("genie","My Universe");
            a.musicSearch("bugs","My Universe");
            a.musicChart();
            
            //---------------------테스트 ------------------------------
            HashMap<String, Integer> ChartMap = new HashMap();
            
            ChartMap.put("사과",10);
            ChartMap.put("사과",ChartMap.get("사과")+12);
            
            //System.out.println(ChartMap.get("사과"));
            
            
        }    
    
    
    
    
}
