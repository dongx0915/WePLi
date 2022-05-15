/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Crawler;

/**
 *
 * @author joon
 */
public class CrawlingMain {

    public static void main(String[] args) {
        
        // URL
        
        String searchKeyword = "사랑아"; // 검색 키워드
        
        
        String MelonChartUrl = "https://www.melon.com/chart/index.htm"; //멜론 TOP100
        String MelonSearchUrl = "https://www.melon.com/search/song/index.htm?q=" + searchKeyword; // 멜론 검색
        
        
        String GenieChartUrl = "https://genie.co.kr/chart/top200?pgsize=100&rtm=N"; // 지니 TOP100
        String GenieSearchUrl = "https://www.genie.co.kr/search/searchSong?pagesize=50&query=" + searchKeyword; // 지니 검색
        
        
        String BugsChartUrl = "https://music.bugs.co.kr/chart/track/day/total"; // 벅스 TOP100
        String BugsSearchUrl ="https://music.bugs.co.kr/search/track?q=" + searchKeyword; //벅스 검색
        
        // 크롤러 생성
//        
//        Crawler melonChart = new MelonChartCrawler();
//        Crawler melonSearch = new MelonSearchCrawler();
//        
//        
//        
//        Crawler genieChart = new GenieChartCrawler();
//        
//        Crawler genieSearch = new GenieSearchCrawler();
//        
//        Crawler bugsChart = new BugsChartCrawler();
//        Crawler bugsSearch = new BugsSearchCrawler();
        
        
        
        
        

        
//        // 크롤러 호출
//        
//        melonChart.getSongList(MelonChartUrl);
//     
//        melonSearch.getSongList(MelonSearchUrl);
//        
//        genieChart.getSongList(GenieChartUrl);
//        
//        genieSearch.getSongList(GenieSearchUrl);
//        bugsChart.getSongList(BugsChartUrl);
//
//        bugsSearch.getSongList(BugsSearchUrl);
//        
    }
   
}
