/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joon
 */
public class MusicCrawlerFactory {
    
    private final static Map<String, Crawler> map = new HashMap<>();
    
    static {
        map.put("melon", MelonSearchCrawler.getCrawler());
        map.put("genie", GenieSearchCrawler.getCrawler());
        map.put("bugs",  BugsSearchCrawler.getCrawler());
    }
    public ArrayList<Song> crawlSearch(String site ,String keyword){
        
        Crawler crawler = map.get(site);
        String url=crawler.getURL()+keyword;
        return crawler.getSongList(url);
        
    }

    
    public void crawlChart(){
        Crawler melonChart = new MelonChartCrawler();
        Crawler genieChart = new GenieChartCrawler();
        Crawler bugsChart = new BugsChartCrawler();
        
        
    }
    
    
            
}
