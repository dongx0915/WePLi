/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package WePLi;

import WePLi.UI.JFrameSetting;
import WePLi.UI.JPanelSetting;
import WePLi.UI.JTableSetting;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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

public class MainFrame extends javax.swing.JFrame {
    /**
     * Creates new form MainFrame
     */
    private static MainFrame mainFrame;
    private ArrayList<JPanel> panelList = new ArrayList<>();
    
    public MainFrame() {
        JFrameSetting.layoutInit();
        
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        
        panelList.add(chartPanel);
        panelList.add(playlistPanel);
        panelList.add(relaylistPanel);
        panelList.add(notifyPanel);
        
        /* 테이블 기본 디자인 세팅 */
        tableInit(chartScrollPanel, chartTable);
        tableHeaderInit(chartTable);
        
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
        JTableSetting.insertTableRow((DefaultTableModel) chartTable.getModel(), values);

    }
    
    public void changePanel(JPanel clickPanel){
        System.out.println("changePanel");
        clickPanel.setVisible(true);
        clickPanel.setEnabled(false);
        
        System.out.println("클릭 된 패널 : " + clickPanel.toString());
        
        for (JPanel jPanel : panelList) {
            if(jPanel != clickPanel){
                jPanel.setVisible(false);
                jPanel.setEnabled(false);
            }
        }
    }
    
    /* 웹 이미지 url을 ImageIcon으로 변경하는 메소드 */
    public ImageIcon imageToIcon(String url) {
        try {
            /* 웹 이미지 가져오기 */
            ImageIcon icon = new ImageIcon(new URL(url));
            
            /* 이미지 사이즈 조정 */
            return new ImageIcon(icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        }
        catch(MalformedURLException e){
            return null;
        }
    }   
        
    /* 테이블 기본 디자인 세팅 */
    public void tableInit(JScrollPane jScrollPane, JTable jTable) {
        /* 스크롤 패널 배경 색상 변경 */
        jScrollPane.setBackground(new Color(255, 255, 255, 255));
        jScrollPane.getViewport().setOpaque(false);
        
        /* 테이블 UI, 테이블 헤더 UI 변경 */
        jTable.setOpaque(false);
        jTable.setUI(new BasicTableUI());
        
        /* 테이블 배경 색상 변경 */
        JTableSetting.setTableBackground(chartTable);

        /* 테이블 셀 사이즈 변경 */
        System.out.println(jTable.getSize());
        JTableSetting.setTableCellSize(chartTable, new String[]{"순위", "곡", "제목", "가수", "앨범"}, new int[]{70, 80, 464, 140, 140});
        
        /* 테이블의 2번째 컬럼은 이미지를 출력 */
        jTable.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
        
        /* 테이블 컬럼 중앙 정렬 */
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트 테이블 셀 렌더러 생성
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로
        
        TableColumnModel tableColumnModel = jTable.getColumnModel();
        tableColumnModel.getColumn(0).setCellRenderer(dtcr);
        
        tableColumnModel.getColumn(3).setCellRenderer(dtcr);
        tableColumnModel.getColumn(4).setCellRenderer(dtcr);
    }
    
    /* 테이블 헤더 기본 디자인 세팅 */
    public void tableHeaderInit(JTable jTable){
        /* 테이블 헤더 색상, UI, 글자색 변경 */
        JTableHeader tbh = jTable.getTableHeader();
        
        tbh.setUI(new BasicTableHeaderUI());
        tbh.setOpaque(false);                             // Opaque(투명도) 를 false로 해줘야 색상 적용됨
        tbh.setFont(new Font("AppleSDGothicNeoR00", Font.PLAIN, 14));
        
        tbh.setPreferredSize(new Dimension(chartScrollPanel.getWidth(), 40));
        tbh.setBackground(new Color(255, 255, 255));      // 테이블 헤더의 배경색 설정
        tbh.setForeground(new Color(150, 150, 150));            // 테이블 헤더의 글자색 설정
        
        /* 테이블 헤더 테두리 설정 */
        MatteBorder border = new MatteBorder(2,0,2,0, new Color(250,250,250));
        tbh.setBorder(border);    
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
        chartPanel = new javax.swing.JPanel();
        chartScrollPanel = new javax.swing.JScrollPane();
        chartTable = new javax.swing.JTable();
        playlistPanel = new javax.swing.JPanel();
        notifyPanel = new javax.swing.JPanel();
        relaylistPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BackgroundPanel.setBackground(new java.awt.Color(255, 255, 255));
        BackgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HomeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/menu/normal/home.png"))); // NOI18N
        HomeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeLabelMouseClicked(evt);
            }
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PlaylistLabelMouseClicked(evt);
            }
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RelaylistLabelMouseClicked(evt);
            }
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NotifyLabelMouseClicked(evt);
            }
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

        chartPanel.setBackground(new java.awt.Color(255, 255, 255));
        chartPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        chartPanel.setOpaque(false);

        chartScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        chartScrollPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        chartScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chartScrollPanel.setToolTipText("");
        chartScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        chartScrollPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                chartScrollPanelMouseWheelMoved(evt);
            }
        });

        chartTable.setBackground(new java.awt.Color(255,255,255,0));
        chartTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        chartTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        chartTable.setModel(new javax.swing.table.DefaultTableModel(
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
        chartTable.setMinimumSize(new java.awt.Dimension(10, 400));
        chartTable.setRowHeight(80);
        chartTable.setSelectionBackground(new java.awt.Color(169, 230, 255));
        chartTable.getTableHeader().setResizingAllowed(false);
        chartTable.getTableHeader().setReorderingAllowed(false);
        chartScrollPanel.setViewportView(chartTable);

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chartPanelLayout.createSequentialGroup()
                .addContainerGap(123, Short.MAX_VALUE)
                .addComponent(chartScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BackgroundPanel.add(chartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 910, 660));

        playlistPanel.setBackground(new java.awt.Color(153, 0, 153));

        javax.swing.GroupLayout playlistPanelLayout = new javax.swing.GroupLayout(playlistPanel);
        playlistPanel.setLayout(playlistPanelLayout);
        playlistPanelLayout.setHorizontalGroup(
            playlistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        playlistPanelLayout.setVerticalGroup(
            playlistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );

        BackgroundPanel.add(playlistPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 910, 660));

        notifyPanel.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout notifyPanelLayout = new javax.swing.GroupLayout(notifyPanel);
        notifyPanel.setLayout(notifyPanelLayout);
        notifyPanelLayout.setHorizontalGroup(
            notifyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        notifyPanelLayout.setVerticalGroup(
            notifyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );

        BackgroundPanel.add(notifyPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 910, 660));

        relaylistPanel.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout relaylistPanelLayout = new javax.swing.GroupLayout(relaylistPanel);
        relaylistPanel.setLayout(relaylistPanelLayout);
        relaylistPanelLayout.setHorizontalGroup(
            relaylistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        relaylistPanelLayout.setVerticalGroup(
            relaylistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );

        BackgroundPanel.add(relaylistPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 910, 660));

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
    private void chartScrollPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_chartScrollPanelMouseWheelMoved
        // TODO add your handling code here:
        int TableRowHeight = chartTable.getRowHeight();
        if (evt.getUnitsToScroll() > 0) {
            chartScrollPanel.getVerticalScrollBar().setValue(chartScrollPanel.getVerticalScrollBar().getValue() + TableRowHeight); // 각 행의 Height 만큼씩 Scroll이 움직인다.
            //TableRowHeight 자리에 원하는 숫자를 넣어 스크롤 속도를 조절할 수 있다.
        } else {
            chartScrollPanel.getVerticalScrollBar().setValue(chartScrollPanel.getVerticalScrollBar().getValue() - TableRowHeight);
        }
    }//GEN-LAST:event_chartScrollPanelMouseWheelMoved

    private void HomeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeLabelMouseClicked
        // TODO add your handling code here:
        JPanelSetting.changePanel(this.panelList, this.chartPanel);
    }//GEN-LAST:event_HomeLabelMouseClicked

    private void RelaylistLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RelaylistLabelMouseClicked
        // TODO add your handling code here:
        JPanelSetting.changePanel(this.panelList, this.relaylistPanel);
    }//GEN-LAST:event_RelaylistLabelMouseClicked

    private void PlaylistLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlaylistLabelMouseClicked
        // TODO add your handling code here:
        JPanelSetting.changePanel(this.panelList, this.playlistPanel);
    }//GEN-LAST:event_PlaylistLabelMouseClicked

    private void NotifyLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NotifyLabelMouseClicked
        // TODO add your handling code here:
        JPanelSetting.changePanel(this.panelList, this.notifyPanel);
    }//GEN-LAST:event_NotifyLabelMouseClicked


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
        java.awt.EventQueue.invokeLater(() -> {
            System.out.println("호출 됨");
            MainFrame.mainFrame = new MainFrame();
            MainFrame.mainFrame.setVisible(true);
        });
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackgroundPanel;
    private javax.swing.JLabel HeaderLabel;
    private javax.swing.JLabel HomeLabel;
    private javax.swing.JLabel NotifyLabel;
    private javax.swing.JLabel PlaylistLabel;
    private javax.swing.JLabel RelaylistLabel;
    private javax.swing.JLabel SidebarLabel;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JScrollPane chartScrollPanel;
    private javax.swing.JTable chartTable;
    private javax.swing.JPanel notifyPanel;
    private javax.swing.JPanel playlistPanel;
    private javax.swing.JPanel relaylistPanel;
    // End of variables declaration//GEN-END:variables
}
