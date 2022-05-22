/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.Song.SongDto;
import Entity.SongChart;
import Service.SongChartService;
import Service.SongService;
import java.util.ArrayList;

/**
 *
 * @author joon
 */
public class SongController {

    private SongService songService; // 
    private SongChartService songchartService;

    public SongController() {
        this.songService = new SongService();
        this.songchartService = new SongChartService();
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
        ArrayList<SongChart> ChartList = songchartService.ShowMusicChart();
        return ChartList;
    }

}
