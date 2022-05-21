/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.Song.SongDto;
import Service.SongService;
import java.util.ArrayList;

/**
 *
 * @author joon
 */
public class SongController {
    
    private SongService songService ; // 
    
    public SongController(){
        this.songService = new SongService();
    }
    
    public ArrayList<SongDto> SongSearch(String site, String text){
        ArrayList<SongDto> SearchList = songService.musicSearch(site,text);
        
//        if(SearchList.isEmpty())
//            System.out.println("검색된 노래 없음");
//        else
//            System.out.println(SearchList);
    
        return SearchList;
    }
    
    
    
}
    

