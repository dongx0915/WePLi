/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WePLi.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Donghyeon <20183188>
 */
/* 테이블에 이미지 출력을 위한 테이블 셀 렌더러 클래스 */
class ImageRenderer extends DefaultTableCellRenderer {

    JLabel label = new JLabel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        label.setIcon((ImageIcon) value);
        /* 이미지 라벨에 정렬을 적용해줘야 함 */
        label.setHorizontalAlignment(CENTER);
        label.setVerticalAlignment(CENTER);

        return label;
    }

    @Override
    public void setBackground(Color c) {
        super.setBackground(c);
    }
}

class PanelRenderer extends DefaultTableCellRenderer {
    JPanel panel = new JPanel();
    JLabel label = new JLabel();
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        
        label.setIcon((ImageIcon) value);
        /* 이미지 라벨에 정렬을 적용해줘야 함 */
        label.setHorizontalAlignment(CENTER);
        label.setVerticalAlignment(CENTER);
        
        panel.add(label);
        if(isSelected) panel.setBackground(new Color(216,229,255,255));
        else panel.setBackground(new Color(255,255,255,255));
        
        return panel;
    }

    @Override
    public void setBackground(Color c) {
        super.setBackground(c);
    }
}

public class JTableSetting {

    /* 테이블 기본 디자인 세팅 */
    public static void tableInit(JScrollPane jScrollPane, JTable jTable) {
        /* 스크롤 패널 배경 색상 변경 */
        jScrollPane.setBackground(new Color(255, 255, 255, 0));
        jScrollPane.getViewport().setOpaque(false);

        /* 테이블 UI, 테이블 헤더 UI 변경 */
        jTable.setOpaque(false);
        jTable.setUI(new BasicTableUI());

        /* 테이블 배경 색상 변경 */
        JTableSetting.setTableBackground(jTable, new Color(255, 255, 255, 255));
    }

    /* 테이블 헤더 기본 디자인 세팅 */
    public static void tableHeaderInit(JTable jTable, int width, int tableHeight) {
        /* 테이블 헤더 색상, UI, 글자색 변경 */
        JTableHeader tbh = jTable.getTableHeader();

        tbh.setUI(new BasicTableHeaderUI());
        tbh.setOpaque(false);                             // Opaque(투명도) 를 false로 해줘야 색상 적용됨
        tbh.setFont(new Font("AppleSDGothicNeoR00", Font.PLAIN, 14));

        tbh.setPreferredSize(new Dimension(width, tableHeight));
        tbh.setBackground(new Color(255, 255, 255));            // 테이블 헤더의 배경색 설정
        tbh.setForeground(new Color(150, 150, 150));            // 테이블 헤더의 글자색 설정

        /* 테이블 헤더 테두리 설정 */
        MatteBorder border = new MatteBorder(2, 0, 2, 0, new Color(248, 248, 248));
        tbh.setBorder(border);
    }

    public static void setImageCell(JTable jTable, int imageCell) {
        /* 매개변수로 전달받은 컬럼은 이미지를 출력 */
        jTable.getColumnModel().getColumn(imageCell).setCellRenderer(new ImageRenderer());
    }

    /* 테이블 값 추가 메소드 */
    public static void insertTableRow(DefaultTableModel tableModel, Object[][] values) {
        for (Object[] value : values) {
            tableModel.insertRow(tableModel.getRowCount(), value);
        }
    }

    /* 테이블 배경 색상 변경 메소드 */
    public static void setTableBackground(JTable jTable, Color color) {
        jTable.setFillsViewportHeight(true);
        jTable.setBackground(color);
        jTable.setOpaque(false);
    }

    /* 테이블 셀 크기 변경 메소드 */
    public static void setTableCellSize(JTable jTable, int[] width) {
        TableColumnModel model = jTable.getColumnModel();

        for (int i = 0; i < model.getColumnCount(); i++) {
            model.getColumn(i).setPreferredWidth(width[i]);
        }
    }

    public static void tableScroll(JTable jTable, JScrollPane scrollPanel, java.awt.event.MouseWheelEvent evt) {
        int TableRowHeight = jTable.getRowHeight();
        if (evt.getUnitsToScroll() > 0) {
            scrollPanel.getVerticalScrollBar().setValue(scrollPanel.getVerticalScrollBar().getValue() + TableRowHeight); // 각 행의 Height 만큼씩 Scroll이 움직인다.
            //TableRowHeight 자리에 원하는 숫자를 넣어 스크롤 속도를 조절할 수 있다.
        } else {
            scrollPanel.getVerticalScrollBar().setValue(scrollPanel.getVerticalScrollBar().getValue() - TableRowHeight);
        }
    }
    
    public static void songTableSetting(JTable jTable) {
        /* 테이블 셀 사이즈 변경 */
        setTableCellSize(jTable, new int[]{50, 80, 604, 160});

        /* 테이블 컬럼 중앙 정렬 */
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트 테이블 셀 렌더러 생성
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로
        
        TableColumnModel tableColumnModel = jTable.getColumnModel();
        tableColumnModel.getColumn(0).setCellRenderer(dtcr);
        tableColumnModel.getColumn(3).setCellRenderer(dtcr);
        // 2번째 행은 이미지를 표시하도록 설정
        tableColumnModel.getColumn(1).setCellRenderer(new PanelRenderer());
    }
    
    public static void listTableSetting(JTable jTable){
        /* 테이블 셀 사이즈 변경 */
        setTableCellSize(jTable, new int[]{50, 100, 550 ,194});
        setImageCell(jTable, 1);
    }
}
