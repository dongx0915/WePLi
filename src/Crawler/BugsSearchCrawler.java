/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler;

import java.util.ArrayList;
import org.jsoup.nodes.Element;

/**
 *
 * @author joon
 */
public class BugsSearchCrawler extends Crawler{

    public BugsSearchCrawler() {
         this.URL = "https://music.bugs.co.kr/search/track?q=";
    }
    
    
    public String getURL() {
        return URL;
    }

    
    
    
    @Override
    ArrayList<Song> parseSongChart(ArrayList<Element> chartBody) {
        ArrayList<Song> songlist = new ArrayList<>();

        

        
        //select(img) 태그를 가져온다.
        // .이 붙으면 클래스를 찾는다
        //attr 속성 정보 값을 가져온다.
        for(Element element : chartBody){
            
            String image = element.select("img").attr("src");                 // 이미지 크롤링
            String title = element.select(".title").text();                  // 노래제목 크롤링
            String singer = element.select(".artist").text();                  // 가수 크롤링
            String album = element.select(".left").text();                  // 앨범명 크롤링
            
            Song song = new Song.SongBuilder()
                    .image(image)
                    .title(title)
                    .singer(singer)
                    .album(album)
                    .build();
            
            songlist.add(song);
            //System.out.println(song.toString());
        }
        
        return songlist;
    }

}
    


