/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Song;

import Crawler.Crawler;
import Crawler.CrawlerFactory.MusicCrawlerFactory;
import Dto.Song.SongDto;
import Entity.SongChart.SongChart;
import Repository.SongChart.SongChartRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joon
 */
public class SongChartService {

    // Repository
    private SongChartRepository songchartRepository;

    MusicCrawlerFactory musicFactory = new MusicCrawlerFactory();

    public SongChartService() {
        this.songchartRepository = new SongChartRepository();
    }

    public SongDto OneMusicSearch(String keyword) // 검색 시 나오는 첫 음악만 선택
    {
        Crawler crawler;
        crawler = musicFactory.getSearchCrawler("melon");
        
        String SearchURL = crawler.getURL() + keyword;
        ArrayList<SongDto> SongList = crawler.getSongList(SearchURL);

        if (SongList.isEmpty()) return null;

        return SongList.get(0);
    }

    public ArrayList<SongDto> musicChart() {
        ArrayList<Crawler> crawler = musicFactory.getChartCrawler(); // 크롤러 클래스
        
        HashMap<String, SongDto> chartMap = new HashMap<String, SongDto>();

        for (int i = 0; i < crawler.size(); i++) {    //멜론 -> 벅스 -> 지니
            String url = crawler.get(i).getURL();
            ArrayList<SongDto> songList = crawler.get(i).getSongList(url);
            
            for (int j = 0; j < songList.size(); j++) {
                SongDto song = songList.get(j);
                song.setRank(1000 + (100 - song.getRank()));
                
                String title = song.getTitle();
                String singer = song.getSinger();
                String key =  singer + " " + title;       // 가수 + 제목를 키로 지정 (제목 + 가수로 했을 때 검색이 제대로 안되는 경우 발생 (ex: 고백 멜로망스)

                // 해시에 들어있지 않고 멜론(첫 리스트)이 아닌 경우
                if(!chartMap.containsKey(key) && i != 0){
                    // 기존 제목과 가수명으로 검색 (가수명에선 괄호를 제거 후 검색)
                    SongDto searchResult = OneMusicSearch(singer.split("\\(")[0] + " " + title);   

                    if (searchResult != null) {
                        song.setTitle(searchResult.getTitle());
                        song.setSinger(searchResult.getSinger());
                        // 바뀐 가수와 제목으로 키 세팅
                        key = song.getSinger() + " " + song.getTitle();
                    }
                }
                    
                if (chartMap.containsKey(key)) {
                    int preRank = chartMap.get(key).getRank();

                    chartMap.get(key).setRank(preRank + song.getRank());
                } 
                else chartMap.put(key, song);
            }
        }

        // 해시맵 완성
        ArrayList<SongDto> SongList = new ArrayList<>(chartMap.values());  // 정렬 전 통합 리스트

        Collections.sort(SongList);    // 리스트 정렬 (정렬된 인기차트)

        return SongList;
    }

    public void InsertMusicChart(ArrayList<SongDto> dtoList) //DB 올리기
    {
        songchartRepository.deleteAll();
        songchartRepository.resetAutoIncrement();
        ArrayList<SongChart> songChart = new ArrayList<>();
        for (SongDto dto : dtoList) {
            songChart.add(SongChart.toEntity(dto));
        }
        songchartRepository.saveAll(songChart);

    }

    public ArrayList<SongChart> getMusicChart() {   //DB에서 가져오기

        ArrayList<SongChart> songChart;
        songChart = songchartRepository.findAll();
        
        return songChart;
    }
    
    public boolean dateCheck()
    {

        java.sql.Date today = new java.sql.Date(new java.util.Date().getTime());        
        java.sql.Date dbdate = songchartRepository.getDate();

        if(!today.toString().equals(dbdate.toString()))
        {
            songchartRepository.updateDate(today);
            System.out.println("인기차트 업데이트 완료");
        }
        return today.toString().equals(dbdate.toString());
        
    }
}
