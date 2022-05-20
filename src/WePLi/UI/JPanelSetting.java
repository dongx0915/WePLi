/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WePLi.UI;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Donghyeon <20183188>
 */
public class JPanelSetting {
    
    public static void changePanel(ArrayList<JPanel> panelList, JPanel clickPanel){
        clickPanel.setVisible(true);
        clickPanel.setEnabled(true);
        
        for (JPanel jPanel : panelList) {
            if(jPanel != clickPanel){
                jPanel.setVisible(false);
                jPanel.setEnabled(false);
            }
        }
    }
}
