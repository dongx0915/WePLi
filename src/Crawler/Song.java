/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler;

/**
 *
 * @author joon
 */
public class Song {
    public int rank = 0;
    public String image = null;
    public String title;
    public String singer;
    public String album;

    public Song(int rank, String image, String title, String singer, String album) {
        this.rank = rank;
        this.image = image;
        this.title = title;
        this.singer = singer;
        this.album = album;
    }
    
    public static class SongBuilder{
        private int rank;
        private String image;
        private String title;
        private String singer;
        private String album;
        
        public SongBuilder rank(int rank){
            this.rank = rank;
            return this;
        }
        
        public SongBuilder image(String image){
            this.image = image;
            return this;
        }
        
        public SongBuilder title(String title){
            this.title = title;
            return this;
        }
        
        public SongBuilder singer(String singer){
            this.singer = singer;
            return this;
        }
        
        public SongBuilder album(String album){
            this.album = album;
            return this;
        }
        
        public Song build(){
            return new Song(this);
        }
    }

    public Song(SongBuilder songBuilder) {
        this.rank = songBuilder.rank;
        this.title = songBuilder.title;
        this.singer = songBuilder.singer;
        this.image = songBuilder.image;
        this.album = songBuilder.album;
    }
    
    
    public String toString() {
        return "[ " + Integer.toString(rank) + ", " + image + ", " + title + ", " + singer + ", " + album + " ]";
    }
} 
   
