/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.Song.SongCreateDto;
import Dto.Song.SongDto;
import Entity.SongChart.SongChart;
import Service.SongChart.SongChartService;
import Service.Song.SongService;
import java.util.ArrayList;

/**
 *
 * @author joon
 */
public class SongController {
    private static SongController songController = new SongController();
    private SongController(){
        this.songService = new SongService();
        this.songchartService = new SongChartService();
    };
    
    // 싱글턴 패턴
    public static SongController getInstance(){ return songController; }
    
    private SongService songService; // 
    private SongChartService songchartService;

    // 노래 리스트 저장(플레이리스트에서 선택 된 노래)
    public ArrayList<SongDto> addSongList(ArrayList<SongCreateDto> songlist){
        return songService.addSongList(songlist);
    }
    
    // 수록곡 가져오는 메소드 (BsideTrack의 테이블 이름으로 플레이리스트, 릴레이리스트를 구분함)
    public ArrayList<SongDto> getBsideTrack(String bSideTable, String listId){
        ArrayList<SongDto> sideTrack = songService.getBsideTrack(bSideTable, listId);
        
        return sideTrack;
    }
    
    public ArrayList<SongDto> SongSearch(String site, String text) {
        ArrayList<SongDto> SearchList = songService.musicSearch(site, text);

        return SearchList;
    }

    public void updateSongChart() {

        if (!songchartService.dateCheck()) {
            ArrayList<SongDto> ChartList = songchartService.musicChart();  // 인기차트 리스트
            ArrayList<SongDto> subList = new ArrayList<>(ChartList.subList(0, 100)); // 100위까지 짜르기
            songchartService.InsertMusicChart(subList);    // DB 올리기
        }

    }

    public ArrayList<SongChart> getSongChart() {
        ArrayList<SongChart> ChartList = songchartService.getMusicChart();
        return ChartList;
    }

}
