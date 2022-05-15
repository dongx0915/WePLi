/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crawler;

/**
 *
 * @author joon
 */
public class MusicCrawler {
        public static void main(String[] args) {
            
            MusicCrawlerFactory factory = new MusicCrawlerFactory();
            factory.crawlSearch("genie", "사랑아");
            
        }
}
