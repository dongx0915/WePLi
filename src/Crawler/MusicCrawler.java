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
            
            ArrayList<Song> musicList = new ArrayList<>();
            
            MusicCrawlerFactory factory = new MusicCrawlerFactory();
            musicList = factory.crawlSearch("bugs", "나는 트로트가 싫어요");
            System.out.println(musicList);
        }
}
