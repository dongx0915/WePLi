/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler.SearchCrawler;


import Crawler.Crawler;
import Dto.Song.SongDto;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author joon
 */
public class MelonSearchCrawler extends Crawler{
    private static MelonSearchCrawler crawler = new MelonSearchCrawler();
//    private MelonSearchCrawler() { this.URL = "https://www.melon.com/search/song/index.htm?q="; }
    private MelonSearchCrawler() { this.URL = "https://search.melon.com/search/mobile4web/searchsong_list.htm?pageSize=50&keyword="; }
    
    public static MelonSearchCrawler getCrawler(){ return crawler; }

    @Override
    public ArrayList<Element> getChartBody(String url) {
        ArrayList<Element> crawlResult = new ArrayList<>();
        Document doc = null;

        try { doc = (Document) Jsoup.connect(url).get(); } 
        catch (IOException e) { e.printStackTrace(); }
        
        Elements elements = doc.select("li");
        
        for (Element el : elements) crawlResult.add(el);
        
        return crawlResult;
    }
    
    @Override
    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<SongDto> songlist = new ArrayList<>();
        
        for (Element element : chartBody) {
            String title = element.attr("d-songname");
            String singer = element.attr("d-artistname");
            String album = element.attr("d-albumname");
            String image = "https:" + element.attr("d-albumimage");
            
            SongDto song = SongDto.builder()
                                    .title(title)
                                    .singer(singer)
                                    .album(album)
                                    .image(image)
                                    .build();
            
            songlist.add(song);
        }
          
        return songlist;
    }
    
    
//    @Override
//    protected ArrayList<SongDto> parseSongChart(ArrayList<Element> chartBody) {
//        ArrayList<SongDto> songlist = new ArrayList<>();
//
//        for(Element element : chartBody){
//            String title = element.select(".ellipsis").select("a").select(".fc_gray").text();               // 노래제목 크롤링
//            String singer = element.select("#artistName").select(".checkEllipsisSongdefaultList").text();                 // 가수 크롤링
//            String album = element.select(".t_left").select("div[class='wrap']").text();                  // 앨범명 크롤링
//            String coverImg = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/ITunes_logo.svg/438px-ITunes_logo.svg.png";
//
//            SongDto song = SongDto.builder()
//                    .title(title)
//                    .singer(singer)
//                    .image(coverImg)
//                    .album(album)
//                    .build();
//            
//            songlist.add(song);
//        }
//        
//        return songlist;
//    }


}
    


