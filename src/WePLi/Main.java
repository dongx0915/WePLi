package WePLi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Controller.PlaylistController;
import Controller.UserController;
import Entity.Playlist;
import Repository.UserRepository;
import java.util.regex.*;


/**
 *
 * @author Donghyeon <20183188>
 */
public class Main {
    public static void main(String[] args) {
        UserRepository ur = new UserRepository();
                
        PlaylistController pc = new PlaylistController();
        
        for (Playlist allPlaylist : pc.getAllPlaylists()) {
            System.out.println(allPlaylist);
        }
        
        pc.deletePlaylist("P0000000");
        
        for (Playlist allPlaylist : pc.getAllPlaylists()) {
            System.out.println(allPlaylist);
        } 
    }
}
