/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Crawler.Crawler;
import Crawler.CrawlerFactory.MusicCrawlerFactory;
import Dto.Playlist.PlaylistDto;
import Dto.Song.SongDto;
import Entity.Song;
import Entity.SongChart;
import Repository.SongRepository;
import Repository.SongchartRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author joon
 */
public class SongService2 {
    // Repository
    private SongchartRepository songchartRepository;
    private SongRepository songRepository;
    
    MusicCrawlerFactory musicFactory = new MusicCrawlerFactory();
    
    public SongService2()
    {
        this.songchartRepository = new SongchartRepository();
        this.songRepository = new SongRepository();
    }
    
    
    public ArrayList<SongDto> musicSearch(String type, String keyword)
    {
        Crawler crawler ;
        crawler = musicFactory.getSearchCrawler(type);
        String SearchURL=crawler.getURL()+keyword;
        ArrayList<SongDto> SongList = crawler.getSongList(SearchURL);
        
        return SongList;
    }
    
    
    public SongDto OneMusicSearch(String keyword) // 검색 시 나오는 첫 음악만 선택
    {  
        Crawler crawler ;
        crawler = musicFactory.getSearchCrawler("bugs");
        String SearchURL=crawler.getURL()+keyword;  
        ArrayList<SongDto> SongList = crawler.getSongList(SearchURL);
        
        if(SongList.isEmpty())
            return null;

        return SongList.get(0);
    }
    
    public ArrayList<SongDto> musicChart()
    {            
        ArrayList<Crawler> CrawlChart ;
        CrawlChart = musicFactory.getChartCrawler(); // 크롤링한 리스트 
   
        HashMap<String, SongDto> ChartMap = new HashMap<String, SongDto>();       

        for(int i = 0 ; i < CrawlChart.size(); i++)
        {    //멜론 -> 벅스 -> 지니
            String Charturl = CrawlChart.get(i).getURL();
            ArrayList<SongDto> songList = CrawlChart.get(i).getSongList(Charturl);
            
            for(int j = 0 ; j < songList.size() ; j++)
            {
                
                String title_temp = songList.get(j).getTitle();  // 기존 제목 저장
                String singer_temp = songList.get(j).getSinger();    // 기존 가수 저장
                double rank_temp = songList.get(j).getRank();   // 기존 순위 저장
                String name_temp = title_temp + " " + singer_temp;
                    

                if(ChartMap.containsKey(name_temp))
                {
                    rank_temp = (ChartMap.get(name_temp).getRank()+rank_temp)/2; //순위
                    ChartMap.get(name_temp).setRank(rank_temp);
                    ChartMap.put(name_temp,ChartMap.get(name_temp));          
                }
                else
                {
                    if(i != 0)
                    {    // 벅스가 아니라면
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
                    }
                    else if(!ChartMap.containsKey(name_temp))
                    {
                        ChartMap.put(name_temp,songList.get(j));    
                    }
                }
            }
        }            
        
        // 해시맵 완성
        ArrayList<SongDto> SongList = new ArrayList<>(ChartMap.values());  // 정렬 전 통합 리스트
        
        Collections.sort(SongList);    // 리스트 정렬 (정렬된 인기차트)
        
        return SongList;
    }

    public void InsertMusicChart(ArrayList<SongDto> dtoList)    //DB 올리기
    {
        ArrayList<SongChart> songchart = new ArrayList<>() ;
        for(SongDto dto : dtoList){
            songchart.add(SongChart.toEntity(dto));
        }
        songchartRepository.saveAll(songchart);
    }
    
    
    
    // list_id 받아와서 song 찾기
    public ArrayList<SongDto> getSongList(String playlistId){
        ArrayList<Song> songlist = songRepository.findSonglistById(playlistId);
        ArrayList<SongDto> dtolist = new ArrayList<>();
        
        for (Song song : songlist) 
            dtolist.add(SongDto.createSongDto(song));
        
        return dtolist;
    }
    
    
    public static void main(String[] args) {
        SongService2 s = new SongService2();
        
        ArrayList<SongDto> songList = s.getSongList("P0000015");
        for (SongDto songDto : songList) {
            System.out.println(songDto);
        }
            

        }    

}
