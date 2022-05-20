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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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
    
    public void musicChart(){
            
        ArrayList<Crawler> CrawlChart ;
        CrawlChart = musicFactory.getChartCrawler(); // 크롤링한 리스트 
            
        HashMap<String, Double> ChartMap = new HashMap<String, Double>();       
        System.out.println("--------------------------- 인기 차트 -------------------------------"); 
        for(int i = 0 ; i < CrawlChart.size(); i++){    //멜론 -> 벅스 -> 지니
        //for(int i = 1 ; i < 2; i++){
            String Charturl = CrawlChart.get(i).getURL();
            ArrayList<SongDto> songList = CrawlChart.get(i).getSongList(Charturl);
            
            for(int j = 0 ; j < songList.size() ; j++){
                    String title_temp = songList.get(j).getTitle();  // 기존 제목 저장
                    String singer_temp = songList.get(j).getSinger();    // 기존 가수 저장
                    double rank_temp = songList.get(j).getRank();   // 기존 순위 저장
                    String name_temp = title_temp + " " + singer_temp;
                    

                    if(ChartMap.containsKey(name_temp)){
                          ChartMap.put(name_temp,(ChartMap.get(name_temp)+rank_temp)/2);             
                          //System.out.println("들어가기 전 겹침 : " + name_temp + " " + ChartMap.get(name_temp));
                    }
                    else{
                        if(i != 0){    // 벅스가 아니라면
                            SongDto songdto = OneMusicSearch(title_temp + " " + singer_temp);   // 기존 제목과 가수명으로 검색

                            if(songdto!=null){
                                songList.get(j).setTitle(songdto.getTitle());
                                songList.get(j).setSinger(songdto.getSinger());
                                name_temp = songList.get(j).getTitle()+ " " + songList.get(j).getSinger();
                            }
                        }
                        
                        if(ChartMap.containsKey(name_temp)){
                            ChartMap.put(name_temp,(ChartMap.get(name_temp)+rank_temp)/2);             
                            //System.out.println("들어가기 후 겹침 : " + name_temp + " " + ChartMap.get(name_temp));
                        }
                        else if(!ChartMap.containsKey(name_temp)){
                            ChartMap.put(name_temp,rank_temp);
                            //System.out.println("안겹침 : " + name_temp + " " + ChartMap.get(name_temp));
                        }
                    }
                }
            }            
            // 해시맵 완성
            List<Map.Entry<String, Double>> entryList = new LinkedList<>(ChartMap.entrySet());
            entryList.sort(HashMap.Entry.comparingByValue());
            int c = 0;
            for(Map.Entry<String, Double> entry : entryList){
                    c++;
                    System.out.println(entry.getValue() + "위 : "+ entry.getKey());
                }
                System.out.println(c);
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

        }    

}
