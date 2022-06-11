/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service.Song;

import Crawler.Crawler;
import Crawler.CrawlerFactory.MusicCrawlerFactory;
import Dto.Relaylist.RelaylistDto;
import Dto.Song.SongCreateDto;
import Dto.Song.SongDto;
import Entity.Song.Song;
import Repository.Song.SongRepository;
import Service.Relaylist.RelaylistService;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.sql.Date;

/**
 *
 * @author joon
 */
public class SongService {
    
    // Repository
    private SongRepository songRepository;
    private RelaylistService relaylistService;
    private MusicCrawlerFactory musicFactory = new MusicCrawlerFactory();
    
    public SongService() {
        this.songRepository = new SongRepository();
        this.relaylistService = new RelaylistService();
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
    public ArrayList<SongDto> getBsideTrack(String listId){
        ArrayList<Song> sideTrack;

        // 릴레이리스트인 경우 완료된 릴레이리스트면 상위 10곡만 가져옴
        if(listId.matches("^R[0-9]{7}$")){
            RelaylistDto relaylist = relaylistService.getRelaylist(listId);
            
            long createTime = relaylist.getCreateTime().getTime();
            long limitTime = new Date(1000 * 60 * 60 * 24).getTime();
        
            long current = System.currentTimeMillis();
        
            // 이미 완성된 리스트인 경우 상위 10개의 수록곡만 가져옴
            if(current >= createTime + limitTime) sideTrack = songRepository.getTop10SideTrack(listId);
            // 완성되지 않았으면(투표가 진행 중이면) 모든 수록곡을 가져옴
            else sideTrack = songRepository.getBsideTrack(listId);
        }
        // 플레이리스트인 경우 그냥 수록곡 조회
        else sideTrack = songRepository.getBsideTrack(listId);

        return (ArrayList) sideTrack.stream().map(song -> SongDto.createSongDto(song))
                                    .collect(Collectors.toList());        
    }
}

