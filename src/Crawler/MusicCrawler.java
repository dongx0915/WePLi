/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler;

import java.util.ArrayList;

/**
 *
 * @author joon
 */
public class MusicCrawler {
        public static void main(String[] args) {
            

            
            MusicCrawlerFactory musicFactory = new MusicCrawlerFactory();
            
            
            System.out.println("--------------------- 노래 검색 (키워드 : 사랑아) ------------------------"); 
            
            Crawler crawler ;
            String keyword="사랑아";
            
            crawler = musicFactory.MusicSearch("genie");
            String SearchURL=crawler.getURL()+keyword;
            System.out.println(crawler.getSongList(SearchURL));
            
            
            System.out.println("\n----------------------------- 인기차트 ----------------------------------"); 
            
            
            ArrayList<Crawler> CrawlChart ;
            
            CrawlChart = musicFactory.MusicChart();
            
            for(int i = 0 ; i < 3 ; i++){
                String Charturl = CrawlChart.get(i).getURL();
                System.out.println(CrawlChart.get(i).getSongList(Charturl));
            }
            
            
            
            
            
            
        }
}
