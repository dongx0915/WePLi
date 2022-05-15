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
            
            Crawler crawler ;
            String keyword="사랑아";
            
            MusicCrawlerFactory musicFactory = new MusicCrawlerFactory();
            
            crawler = musicFactory.MusicSearch("genie");
            String SearchURL=crawler.getURL()+keyword;
            //System.out.println(crawler.getSongList(SearchURL));
            
            
            // --------------------------------------------
            
            
            ArrayList<Crawler> CrawlChart ;
            
            CrawlChart = musicFactory.MusicChart();
            
            for(int i = 0 ; i < 3 ; i++){
                String Charturl = CrawlChart.get(i).getURL();
                System.out.println(CrawlChart.get(i).getSongList(Charturl));
            }
            
            
            
            
            
            
        }
}
