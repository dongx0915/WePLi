/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.Song.SongDto;
import Entity.Song;
import Entity.SongChart;
import Service.SongService;
import java.util.ArrayList;

/**
 *
 * @author joon
 */
public class SongController {

    private SongService songService; // 

    public SongController() {
        this.songService = new SongService();
    }

    public ArrayList<SongDto> SongSearch(String site, String text) {
        ArrayList<SongDto> SearchList = songService.musicSearch(site, text);

//        if(SearchList.isEmpty())
//            System.out.println("검색된 노래 없음");
//        else
//            System.out.println(SearchList);
        return SearchList;
    }

    public void updateSongChart() {

        if(!songService.dateCheck()){
            ArrayList<SongDto> ChartList = songService.musicChart();  // 인기차트 리스트
            ArrayList<SongDto> subList = new ArrayList<>(ChartList.subList(0, 100)); // 100위까지 짜르기
            songService.InsertMusicChart(subList);    // DB 올리기
        }
        
    }

    public ArrayList<SongChart> getSongChart() {

        ArrayList<SongChart> ChartList = songService.ShowMusicChart();
        System.out.println(ChartList);
        return ChartList;
    }

}
