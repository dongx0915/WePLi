/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler.CrawlerFactory;

import Crawler.ChartCrawler.BugsChartCrawler;
import Crawler.SearchCrawler.BugsSearchCrawler;
import Crawler.Crawler;
import Crawler.ChartCrawler.GenieChartCrawler;
import Crawler.SearchCrawler.GenieSearchCrawler;
import Crawler.ChartCrawler.MelonChartCrawler;
import Crawler.SearchCrawler.MelonSearchCrawler;
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
    
    public Crawler MusicSearch(String site){
        Crawler crawler = map.get(site);
        return crawler;

    }

    
    public ArrayList<Crawler> MusicChart(){
        
        ArrayList<Crawler> CrawlChart = new ArrayList<>();
        
        CrawlChart.add(MelonChartCrawler.getCrawler());
        CrawlChart.add(GenieChartCrawler.getCrawler());
        CrawlChart.add(BugsChartCrawler.getCrawler());
     
        return CrawlChart;
    }
    
    
            
}
