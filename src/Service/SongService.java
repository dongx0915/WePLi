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
    
    public void musicSearch(String type, String keyword){
            
        System.out.println("--------------------------- 노래 검색 -------------------------------"); 
            
        Crawler crawler ;
        crawler = musicFactory.getSearchCrawler(type);
        String SearchURL=crawler.getURL()+keyword;
        ArrayList<SongDto> SongList = crawler.getSongList(SearchURL);
        
        if(SongList.isEmpty())
            System.out.println("검색된 노래 없음");
        else
            System.out.println(SongList);
    }
    
    
    public SongDto OneMusicSearch(String keyword){  // 검색 음악 하나만 선택 - 음악 이퀄라이저
                    
        Crawler crawler ;
        crawler = musicFactory.getSearchCrawler("bugs");
        String SearchURL=crawler.getURL()+keyword;  
        ArrayList<SongDto> SongList = crawler.getSongList(SearchURL);
        
        if(SongList.isEmpty())
            return null;

        return SongList.get(0);
    
    }
    
    public void EqualizeMusic(){
       
        
        
    }
    
    
       
    public void musicChart(){
            
        ArrayList<Crawler> CrawlChart ;
        CrawlChart = musicFactory.getChartCrawler(); // 크롤링한 리스트 
            
        //chartIntegrate(CrawlChart); //음악 순위 합치기 구현중
            
            
        System.out.println("--------------------------- 인기 차트 -------------------------------"); 
        for(int i = 0 ; i < CrawlChart.size(); i++){
        //for(int i = 0 ; i < 1; i++){
            String Charturl = CrawlChart.get(i).getURL();
            ArrayList<SongDto> songList = CrawlChart.get(i).getSongList(Charturl);
            //System.out.println(CrawlChart.get(i).getSongList(Charturl)); // 전체 리스트 출력
            //System.out.println(CrawlChart.get(i).getSongList(Charturl).get(0).getTitle()); // 음악사 별 1등 노래 출력
                
            for(int j = 0 ; j < songList.size() ; j++){
         //   int j=47;
                String title_temp = songList.get(j).getTitle();  // 기존 제목 저장
                String singer_temp = songList.get(j).getSinger();    // 기존 가수 저장
                //int rank_temp = songList.get(j).getRank();   // 기존 순위 저장
//                System.out.println(title_temp);
//                System.out.println(singer_temp);
                SongDto songdto = OneMusicSearch(title_temp + " " + singer_temp);   // 기존 제목과 가수명으로 검색
                //System.out.println(songdto);
       //          System.out.println("기존 정보 : " + title_temp + ", " + singer_temp + ", " + rank_temp);
         //        System.out.println("검색된 정보 : " + songdto.getTitle() + " - " + songdto.getSinger());
                     
                if(songdto!=null){
                    songList.get(j).setTitle(songdto.getTitle());
                    songList.get(j).setSinger(songdto.getSinger());
                }
         
                System.out.println(songList.get(j).getRank() +"\t" + songList.get(j).getTitle()+ "\t" + songList.get(j).getSinger());

                }
            }            
        }
        
    public void chartIntegrate(ArrayList<Crawler> crawlChart){
        ArrayList<SongDto> songList = new ArrayList<>();
            
        for(int i = 0 ; i< crawlChart.size(); i++){
            String Charturl = crawlChart.get(i).getURL();
            for(int j = 0 ; j < crawlChart.get(i).getSongList(Charturl).size() ; j++)
            {
                String title_temp = crawlChart.get(i).getSongList(Charturl).get(j).getTitle();
                String signer_temp = crawlChart.get(i).getSongList(Charturl).get(j).getSinger();
                //int rank;
                System.out.println(crawlChart.get(i).getSongList(Charturl).get(j).getTitle()+crawlChart.get(i).getSongList(Charturl).get(j).getSinger());
            }
        }
    }

        
        


 
        
        public static void main(String[] args) {
            
            SongService a = new SongService();
            //a.musicSearch("melon","My Universe");
            //a.musicSearch("genie","사건의 지평선");
            //a.OneMusicSearch("my universe");
            //a.musicSearch("bugs","드라마 아이유");
            //a.musicSearch("bugs","my universe 방탄소년단");
            a.musicChart();
            
            //---------------------테스트 ------------------------------
            HashMap<String, Integer> ChartMap = new HashMap();
            ChartMap.put("사과",10);
            ChartMap.put("사과",ChartMap.get("사과")+12);
            
            System.out.println(ChartMap.get("사과"));

        }    
    
    
    
    
}
