/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.Relaylist;

import Repository.BaseRepository.CrudRepository;
import Entity.Relaylist.Relaylist;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelaylistRepository extends CrudRepository<Relaylist, String>{
    public RelaylistRepository() { super.setEntity(new Relaylist());}
}
