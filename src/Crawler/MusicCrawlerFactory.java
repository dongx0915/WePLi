/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joon
 */
public class MusicCrawlerFactory {
    
    private final static Map<String, Crawler> map = new HashMap<>();
    
    static {
        map.put("melon", new MelonSearchCrawler());
        map.put("genie", new GenieSearchCrawler());
        map.put("bugs", new BugsSearchCrawler());
    }
    public void crawlSearch(String site ,String keyword){
        
        System.out.println(site);
        Crawler crawler = map.get(site);
        String url=crawler.getURL()+keyword;
        System.out.println(url);
        crawler.getSongList(url);
        
    }

    
    public void crawlChart(){
        Crawler melonChart = new MelonChartCrawler();
        Crawler genieChart = new GenieChartCrawler();
        Crawler bugsChart = new BugsChartCrawler();
        
        
    }
    
    
            
}
