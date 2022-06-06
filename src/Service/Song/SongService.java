/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Song;

import Crawler.Crawler;
import Crawler.CrawlerFactory.MusicCrawlerFactory;
import Dto.Song.SongCreateDto;
import Dto.Song.SongDto;
import Entity.Song.Song;
import Repository.Song.SongRepository;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author joon
 */
public class SongService {
    
    // Repository
    private SongRepository songRepository;
    private MusicCrawlerFactory musicFactory = new MusicCrawlerFactory();

    public SongService() {
        this.songRepository = new SongRepository();
    }
    
    public ArrayList<SongDto> musicSearch(String type, String keyword) {
        Crawler crawler;
        crawler = musicFactory.getSearchCrawler(type);
        String SearchURL = crawler.getURL() + keyword;
        ArrayList<SongDto> SongList = crawler.getSongList(SearchURL);

        return SongList;
    }
    
    // 수록곡을 Song 테이블에 저장하는 메소드
    public ArrayList<SongDto> addSongList(ArrayList<SongCreateDto> songlist){
        ArrayList<SongDto> bSideTrack = new ArrayList<>();
        
        for (SongCreateDto songCreateDto : songlist) {
            Song song = Song.toEntity(songCreateDto);
            // 제목과 가수로 Song 검색
            Song result = songRepository.findSongByTitleAlbum(song.getTitle(), song.getAlbum());
            
            // 저장된 노래가 없다면 Save
            if(Objects.isNull(result)) result = songRepository.save(song);
            
            // 수록곡 리스트에 추가
            bSideTrack.add(SongDto.createSongDto(result));
        }
        
        return bSideTrack;
    }
    
    // 수록곡 가져오는 메소드 (BsideTrack의 테이블 이름으로 플레이리스트, 릴레이리스트를 구분함)
    public ArrayList<SongDto> getBsideTrack(String bSideTable, String listId){
        ArrayList<Song> sideTrack = songRepository.getBsideTrack(bSideTable, listId);

        return (ArrayList) sideTrack.stream().map(song -> SongDto.createSongDto(song))
                                    .collect(Collectors.toList());        
    }
}
    
//  검색 테스트용
//    public static void main(String[] args) {
////
////        // 인기차트 컨트롤러에 있어야함
//        SongService a = new SongService();
//////        ArrayList<SongDto> ChartList = a.musicChart();  // 인기차트 리스트
//////        ArrayList<SongDto> subList = new ArrayList<>(ChartList.subList(0,100)); // 100위까지 짜르기
//////        a.InsertMusicChart(subList);    // DB 올리기
////
//////        ArrayList<SongChart> Chart = a.ShowMusicChart();
//////        System.out.println(Chart);
////        // 검색
//////           ArrayList<SongDto> SearchList = a.musicSearch("melon","싸이");   // 검색 리스트
//////           if(SearchList.isEmpty())
//////                System.out.println("검색된 노래 없음");
//////           else{
//////               System.out.println(SearchList);
//////           }
//            ArrayList<SongDto> SearchList = a.musicSearch("bugs","빅뱅");
//            System.out.println(SearchList.get(0).getAlbum());        
////        //a.musicSearch("bugs","드라마 아이유");
////        //a.musicSearch("bugs","my universe 방탄소년단");
//    }
//
//}
