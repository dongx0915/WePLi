/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WePLi.UI;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Donghyeon <20183188>
 */
public class JTableSetting {
    /* 테이블 값 추가 메소드 */
    public static void insertTableRow(DefaultTableModel tableModel, Object[][] values) {
        for (Object[] value : values) {
            tableModel.insertRow(tableModel.getRowCount(), value);
        }
    }

    /* 테이블 배경 색상 변경 메소드 */
    public static void setTableBackground(JTable jTable) {
        jTable.setFillsViewportHeight(true);
        jTable.setBackground(new Color(255, 255, 255));
        jTable.setOpaque(false);
    }

    /* 테이블 셀 크기 변경 메소드 */
    public static void setTableCellSize(JTable jTable, String[] column, int[] width) {
        for (int i = 0; i < column.length; i++) {
            jTable.getColumn(column[i]).setPreferredWidth(width[i]);
        }
    }
}
