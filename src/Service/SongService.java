/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Crawler.Crawler;
import Crawler.CrawlerFactory.MusicCrawlerFactory;
import Dto.SongDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    public void musicChart(){
            
        ArrayList<Crawler> CrawlChart ;
        CrawlChart = musicFactory.getChartCrawler(); // 크롤링한 리스트 
            
        //HashMap<String, Double> ChartMap = new HashMap<String, Double>();       
        HashMap<String, SongDto> ChartMap = new HashMap<String, SongDto>();       
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
                            rank_temp = (ChartMap.get(name_temp).getRank()+rank_temp)/2; //순위
                            ChartMap.get(name_temp).setRank(rank_temp);
                            ChartMap.put(name_temp,ChartMap.get(name_temp));          
                          //System.out.println("들어가기 전 겹침 : " + name_temp + " " + ChartMap.get(name_temp).getRank());
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
                            rank_temp = (ChartMap.get(name_temp).getRank()+rank_temp)/2; //순위
                            ChartMap.get(name_temp).setRank(rank_temp);
                            ChartMap.put(name_temp,ChartMap.get(name_temp));             
                            //System.out.println("들어가기 후 겹침 : " + name_temp + " " + ChartMap.get(name_temp).getRank());
                        }
                        else if(!ChartMap.containsKey(name_temp)){
                            ChartMap.put(name_temp,songList.get(j));
                            //System.out.println("안겹침 : " + name_temp + " " + ChartMap.get(name_temp).getRank());
                        }
                    }
                }
            }            
            // 해시맵 완성
                   
            ArrayList<SongDto> valueList = new ArrayList<>(ChartMap.values());  // 정렬 전 통합 리스트
            
            Comparator<SongDto> cp = new Comparator<SongDto>(){ // 리스트 정렬 
                @Override
                public int compare(SongDto data1, SongDto data2){
                    double a = data1.getRank();
                    double b = data2.getRank();
                    
                    if(a>b){
                        return 1;
                    }else 
                        return -1;
                }
            };
            
            Collections.sort(valueList, cp);    // 리스트 정렬
            
            //System.out.println(valueList);

            int c = 0;            
            for(SongDto d : valueList){
                c++;
                System.out.println(c + "위 : " + d.getTitle());
            }
            //Collections.sort(entryList, (o1, o2) -> (ChartMap.get(o1).getRank().compareTo(ChartMap.get(o2).getRank())));
            //entryList.sort(HashMap.Entry.comparingByValue().ge);
//            int c = 0;
//            for (Entry<String, SongDto> entrySet : ChartMap.entrySet()) {
//                    c++;
//                    System.out.println(entrySet.getValue().getRank() + "위 : "+ entrySet.getKey());
//                }
//                System.out.println(c);
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
