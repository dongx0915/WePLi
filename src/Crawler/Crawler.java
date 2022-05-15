/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author joon
 */

public abstract class Crawler {
   
    protected String URL;
    
    ArrayList<Element> getChartBody(String url){
        
        ArrayList<Element> crawl_Result = new ArrayList<>();  
        Document doc = null;
              
        try {
            doc = (Document) Jsoup.connect(url).get();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        
       Element element = null;
       
        if(doc.select("tbody").size()==3) //bugs라면
            element = doc.select("tbody").get(1);
        else
            element = doc.select("tbody").get(0);
        
            
            
            
        for(Element el : element.select("tr")) {
            crawl_Result.add(el);
            
        }
        return crawl_Result;
        
    }
    
    abstract ArrayList<Song> parseSongChart(ArrayList<Element> chartBody);
    
    ArrayList<Song> getSongList(String url){  
        ArrayList<Element> chartBody = this.getChartBody(url);
        ArrayList<Song> songList = new ArrayList<>();
        return this.parseSongChart(chartBody); // 리스트 리턴됨 쓸거면 쓰셈
       
        
    }

    abstract String getURL();
        
    


    
}
