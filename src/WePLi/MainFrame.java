/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package WePLi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        label.setIcon((ImageIcon) value);
        return label;
    }
}

public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);

        /* 테이블 기본 디자인 세팅 */
        tableInit(ChartScrollPanel, ChartTable);

        /* 테스트 값 생성 */
        String url1 = "https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/082/662/688/82662688_1651196114166_1_600x600.JPG/dims/resize/Q_80,0";
        String url2 = "https://image.bugsm.co.kr/album/images/50/40756/4075667.jpg?version=20220515063240.0";
        
        Object[][] values = {
            {"1", imageToIcon(url1), "   회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", imageToIcon(url2), "   테스트", "테스트", "테스트"},
            {"1", imageToIcon(url1), "   회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", imageToIcon(url1), "   회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", imageToIcon(url1), "   회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", imageToIcon(url1), "   회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", imageToIcon(url1), "   회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", imageToIcon(url1), "   회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", imageToIcon(url1), "   회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
        };

        /* 테이블에 값 추가*/
        insertTableRow((DefaultTableModel) ChartTable.getModel(), values);

        /* 테이블 배경 색상 변경 */
        setTableBackground(ChartTable);

        /* 테이블 셀 사이즈 변경 */
        System.out.println(ChartTable.getSize());
        setTableCellSize(ChartTable, new String[]{"순위", "곡", "제목", "가수", "앨범"}, new int[]{60, 80, 464, 140, 140});
    }
    
    /* 웹 이미지 url을 ImageIcon으로 변경하는 메소드 */
    public ImageIcon imageToIcon(String url) {
        try {
            /* 웹 이미지 가져오기 */
            ImageIcon icon = new ImageIcon(new URL(url));
            
            /* 이미지 사이즈 조정 */
            return new ImageIcon(icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        }
        catch(MalformedURLException e){
            return null;
        }
    }   
        
    /* 테이블 기본 디자인 세팅 */
    public void tableInit(JScrollPane jScrollPane, JTable jTable) {
        /* 스크롤 패널 배경 색상 변경 */
        jScrollPane.setBackground(new Color(255, 255, 255, 255));
        jScrollPane.setOpaque(true);
        jScrollPane.getViewport().setOpaque(true);

        /* 테이블 UI, 테이블 헤더 UI 변경 */
        jTable.setUI(new BasicTableUI());
        jTable.getTableHeader().setUI(new BasicTableHeaderUI());

        /* 테이블 헤더 색상, 글자색 변경 */
        JTableHeader tbh = jTable.getTableHeader();
        tbh.setPreferredSize(new Dimension(ChartScrollPanel.getWidth(), 100));
        //tbh.setFont(new Font("Segoe UI", Font.BOLD, 12)); // 테이블 헤더의 폰트 설정
        tbh.setOpaque(false);                             // Opaque(투명도) 를 false로 해줘야 색상 적용됨
        tbh.setBackground(new Color(243, 248, 255));      // 테이블 헤더의 배경색 설정
        tbh.setForeground(new Color(0, 0, 0));            // 테이블 헤더의 글자색 설정

        /* 테이블의 2번째 컬럼은 이미지를 출력 */
        jTable.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
        
        
        /* 테이블 컬럼 중앙 정렬 */
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로
        
        TableColumnModel tableColumnModel = jTable.getColumnModel();
        tableColumnModel.getColumn(0).setCellRenderer(dtcr);
        tableColumnModel.getColumn(3).setCellRenderer(dtcr);
        tableColumnModel.getColumn(4).setCellRenderer(dtcr);
    }

    /* 테이블 값 추가 메소드 */
    private void insertTableRow(DefaultTableModel tableModel, Object[][] values) {
        for (Object[] value : values) {
            tableModel.insertRow(tableModel.getRowCount(), value);
        }
    }

    /* 테이블 배경 색상 변경 메소드 */
    private void setTableBackground(JTable jTable) {
        jTable.setOpaque(true);
        jTable.setFillsViewportHeight(true);
        jTable.setBackground(new Color(243, 248, 255));
    }

    /* 테이블 셀 크기 변경 메소드 */
    private void setTableCellSize(JTable jTable, String[] column, int[] width) {
        for (int i = 0; i < column.length; i++) {
            jTable.getColumn(column[i]).setPreferredWidth(width[i]);
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackgroundPanel = new javax.swing.JPanel();
        HomeLabel = new javax.swing.JLabel();
        PlaylistLabel = new javax.swing.JLabel();
        RelaylistLabel = new javax.swing.JLabel();
        NotifyLabel = new javax.swing.JLabel();
        HeaderLabel = new javax.swing.JLabel();
        SidebarLabel = new javax.swing.JLabel();
        ChartPanel = new javax.swing.JPanel();
        ChartScrollPanel = new javax.swing.JScrollPane();
        ChartTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BackgroundPanel.setBackground(new java.awt.Color(243, 248, 255));
        BackgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HomeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/menu/normal/home.png"))); // NOI18N
        HomeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HomeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                HomeLabelMouseExited(evt);
            }
        });
        BackgroundPanel.add(HomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 168, 55));

        PlaylistLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/menu/normal/playlist.png"))); // NOI18N
        PlaylistLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PlaylistLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PlaylistLabelMouseExited(evt);
            }
        });
        BackgroundPanel.add(PlaylistLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 115, 168, 55));

        RelaylistLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/menu/normal/relaylist.png"))); // NOI18N
        RelaylistLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RelaylistLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RelaylistLabelMouseExited(evt);
            }
        });
        BackgroundPanel.add(RelaylistLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 168, 55));

        NotifyLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/menu/normal/notify.png"))); // NOI18N
        NotifyLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                NotifyLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                NotifyLabelMouseExited(evt);
            }
        });
        BackgroundPanel.add(NotifyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 225, 168, 55));

        HeaderLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/header.png"))); // NOI18N
        BackgroundPanel.add(HeaderLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 74));

        SidebarLabel.setBackground(new java.awt.Color(39, 49, 64));
        SidebarLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/sidebar.png"))); // NOI18N
        BackgroundPanel.add(SidebarLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 168, -1));

        ChartPanel.setBackground(new java.awt.Color(243, 248, 255));

        ChartScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ChartScrollPanel.setToolTipText("");
        ChartScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        ChartScrollPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                ChartScrollPanelMouseWheelMoved(evt);
            }
        });

        ChartTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 12)); // NOI18N
        ChartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "순위", "곡", "제목", "가수", "앨범"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ChartTable.setMinimumSize(new java.awt.Dimension(10, 400));
        ChartTable.setRowHeight(80);
        ChartScrollPanel.setViewportView(ChartTable);

        javax.swing.GroupLayout ChartPanelLayout = new javax.swing.GroupLayout(ChartPanel);
        ChartPanel.setLayout(ChartPanelLayout);
        ChartPanelLayout.setHorizontalGroup(
            ChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChartScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
        );
        ChartPanelLayout.setVerticalGroup(
            ChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChartScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
        );

        BackgroundPanel.add(ChartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 890, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* 홈 메뉴 마우스 이벤트 */
    private void HomeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeLabelMouseEntered
        // TODO add your handling code here:
        HomeLabel.setIcon(new ImageIcon("./src/resources/layout/menu/hover/home_hover.png"));
        HomeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_HomeLabelMouseEntered

    private void HomeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeLabelMouseExited
        // TODO add your handling code here:
        HomeLabel.setIcon(new ImageIcon("./src/resources/layout/menu/normal/home.png"));
        HomeLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_HomeLabelMouseExited

    /* 플레이리스트 메뉴 마우스 이벤트 */
    private void PlaylistLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlaylistLabelMouseEntered
        // TODO add your handling code here:
        PlaylistLabel.setIcon(new ImageIcon("./src/resources/layout/menu/hover/playlist_hover.png"));
        PlaylistLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_PlaylistLabelMouseEntered

    private void PlaylistLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlaylistLabelMouseExited
        // TODO add your handling code here:
        PlaylistLabel.setIcon(new ImageIcon("./src/resources/layout/menu/normal/playlist.png"));
        PlaylistLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_PlaylistLabelMouseExited

    /* 릴레이리스트 메뉴 마우스 이벤트 */
    private void RelaylistLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RelaylistLabelMouseEntered
        // TODO add your handling code here:
        RelaylistLabel.setIcon(new ImageIcon("./src/resources/layout/menu/hover/relaylist_hover.png"));
        RelaylistLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_RelaylistLabelMouseEntered

    private void RelaylistLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RelaylistLabelMouseExited
        // TODO add your handling code here:
        RelaylistLabel.setIcon(new ImageIcon("./src/resources/layout/menu/normal/relaylist.png"));
        RelaylistLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_RelaylistLabelMouseExited

    /* 알림 메뉴 마우스 이벤트 */
    private void NotifyLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NotifyLabelMouseEntered
        // TODO add your handling code here:
        NotifyLabel.setIcon(new ImageIcon("./src/resources/layout/menu/hover/notify_hover.png"));
        NotifyLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_NotifyLabelMouseEntered

    private void NotifyLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NotifyLabelMouseExited
        // TODO add your handling code here:
        NotifyLabel.setIcon(new ImageIcon("./src/resources/layout/menu/normal/notify.png"));
        NotifyLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_NotifyLabelMouseExited
    
    /* 스크롤 패널 스크롤 이벤트 구현 */
    private void ChartScrollPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_ChartScrollPanelMouseWheelMoved
        // TODO add your handling code here:
        int TableRowHeight = ChartTable.getRowHeight();
        if (evt.getUnitsToScroll() > 0) {
            ChartScrollPanel.getVerticalScrollBar().setValue(ChartScrollPanel.getVerticalScrollBar().getValue() + TableRowHeight); // 각 행의 Height 만큼씩 Scroll이 움직인다.
            //TableRowHeight 자리에 원하는 숫자를 넣어 스크롤 속도를 조절할 수 있다.
        } else {
            ChartScrollPanel.getVerticalScrollBar().setValue(ChartScrollPanel.getVerticalScrollBar().getValue() - TableRowHeight);
        }
    }//GEN-LAST:event_ChartScrollPanelMouseWheelMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackgroundPanel;
    private javax.swing.JPanel ChartPanel;
    private javax.swing.JScrollPane ChartScrollPanel;
    private javax.swing.JTable ChartTable;
    private javax.swing.JLabel HeaderLabel;
    private javax.swing.JLabel HomeLabel;
    private javax.swing.JLabel NotifyLabel;
    private javax.swing.JLabel PlaylistLabel;
    private javax.swing.JLabel RelaylistLabel;
    private javax.swing.JLabel SidebarLabel;
    // End of variables declaration//GEN-END:variables
}
