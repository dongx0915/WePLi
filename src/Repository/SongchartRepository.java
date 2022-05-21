/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Entity.SongChart;

/**
 *
 * @author joon
 */
public class SongchartRepository extends EntityRepository<SongChart, String>{
    public SongchartRepository() { super.setEntity(new SongChart()); }
    
}
