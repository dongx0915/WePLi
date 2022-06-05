/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dto.RelayUser.RelayUserDto;
import Service.RelayUser.RelayUserService;

/**
 *
 * @author Donghyeon <20183188>
 */
public class RelayUserController {
    private static RelayUserController relayUserController = new RelayUserController();
    public static RelayUserController getInstance() { return relayUserController; };

    private RelayUserService relayUserService;
    
    private RelayUserController() { this.relayUserService = new RelayUserService(); }
    
    public boolean addRelayUser(RelayUserDto relayUserDto){
        return relayUserService.addRelayUser(relayUserDto);
    }
    
    public RelayUserDto getRelayUser(String relaylistId, String userId){
        return relayUserService.getRelayUser(relaylistId, userId);
    }
    
}
