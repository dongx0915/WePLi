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
import Repository.SongChartRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author joon
 */
public class SongService {

    // Repository
    MusicCrawlerFactory musicFactory = new MusicCrawlerFactory();

    public ArrayList<SongDto> musicSearch(String type, String keyword) {
        Crawler crawler;
        crawler = musicFactory.getSearchCrawler(type);
        String SearchURL = crawler.getURL() + keyword;
        ArrayList<SongDto> SongList = crawler.getSongList(SearchURL);

        return SongList;
    }
    

    
//  검색 테스트용
//    public static void main(String[] args) {
//
//        // 인기차트 컨트롤러에 있어야함
//        SongService a = new SongService();
////        ArrayList<SongDto> ChartList = a.musicChart();  // 인기차트 리스트
////        ArrayList<SongDto> subList = new ArrayList<>(ChartList.subList(0,100)); // 100위까지 짜르기
////        a.InsertMusicChart(subList);    // DB 올리기
//
////        ArrayList<SongChart> Chart = a.ShowMusicChart();
////        System.out.println(Chart);
//        // 검색
////           ArrayList<SongDto> SearchList = a.musicSearch("melon","싸이");   // 검색 리스트
////           if(SearchList.isEmpty())
////                System.out.println("검색된 노래 없음");
////           else{
////               System.out.println(SearchList);
////           }
//        //a.musicSearch("genie","사건의 지평선");
//        //a.musicSearch("bugs","드라마 아이유");
//        //a.musicSearch("bugs","my universe 방탄소년단");
//    }

}
