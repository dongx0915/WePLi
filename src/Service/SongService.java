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
       
    public void musicChart(){
            
        ArrayList<Crawler> CrawlChart ;
        CrawlChart = musicFactory.MusicChart(); // 크롤ㄹ링한 리스트 
            
        //chartIntegrate(CrawlChart); 음악 순위 합치기 구현중
            
            
        System.out.println("--------------------------- 인기 차트 -------------------------------"); 
        for(int i = 0 ; i < CrawlChart.size(); i++){
            String Charturl = CrawlChart.get(i).getURL();
           // System.out.println(CrawlChart.get(i).getSongList(Charturl)); // 전체 리스트 출력
            System.out.println(CrawlChart.get(i).getSongList(Charturl).get(1).getTitle()); // 음악사 별 1등 노래 출력
            }            
        }
        
    public void chartIntegrate(ArrayList<Crawler> crawlChart){
        ArrayList<SongDto> songList = new ArrayList<>();
            
        for(int i = 0 ; i< crawlChart.size(); i++){
            String Charturl = crawlChart.get(i).getURL();
            for(int j = 0 ; j < crawlChart.get(i).getSongList(Charturl).size() ; j++)
            {
                    
            }
        }
    }

        
        

        public void musicSearch(String type, String keyword){
            
            System.out.println("--------------------------- 노래 검색 -------------------------------"); 
            
            Crawler crawler ;
            crawler = musicFactory.MusicSearch(type);
            String SearchURL=crawler.getURL()+keyword;
            System.out.println(crawler.getSongList(SearchURL));
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
