/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Entity.Playlist;

/**
 *
 * @author Donghyeon <20183188>
 */
public class PlaylistRepository extends EntityRepository<Playlist, String>{
    public PlaylistRepository() { super.setEntity(new Playlist());}
}
