/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.User;

import Repository.BaseRepository.CrudRepository;
import Entity.User.User;
import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 *
 * @author Donghyeon <20183188>
 */
public class UserRepository extends CrudRepository<User, String>{
    public UserRepository() { super.setEntity(new User()); }
}
