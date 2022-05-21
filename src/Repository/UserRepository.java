/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Entity.User;
import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 *
 * @author Donghyeon <20183188>
 */
public class UserRepository extends EntityRepository<User, String>{
    public UserRepository() { super.setEntity(new User()); }
}
