/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

/**
 *
 * @author joon
 */

// 어노테이션 가능?
public class SongDto2 {
    public int rank = 0;
    public String image = null;
    public String title;
    public String singer;
    public String album;

    public SongDto2(int rank, String image, String title, String singer, String album) {
        this.rank = rank;
        this.image = image;
        this.title = title;
        this.singer = singer;
        this.album = album;
    }
    
    public static class SongBuilder2{
        private int rank;
        private String image;
        private String title;
        private String singer;
        private String album;
        
        public SongBuilder2 rank(int rank){
            this.rank = rank;
            return this;
        }
        
        public SongBuilder2 image(String image){
            this.image = image;
            return this;
        }
        
        public SongBuilder2 title(String title){
            this.title = title;
            return this;
        }
        
        public SongBuilder2 singer(String singer){
            this.singer = singer;
            return this;
        }
        
        public SongBuilder2 album(String album){
            this.album = album;
            return this;
        }
        
        public SongDto2 build(){
            return new SongDto2(this);
        }
    }

    public SongDto2(SongBuilder2 songBuilder) {
        this.rank = songBuilder.rank;
        this.title = songBuilder.title;
        this.singer = songBuilder.singer;
        this.image = songBuilder.image;
        this.album = songBuilder.album;
    }
    
    
    public String toString() {
        return "[ " + Integer.toString(rank) + ", " + image + ", " + title + ", " + singer + ", " + album + " ]";
    }
    
    public String getIntegrateString() {
        return this.title + "\\\\" + this.singer;
    }
    
    public int getRank(){
        return this.rank;
    }
    
} 
   
