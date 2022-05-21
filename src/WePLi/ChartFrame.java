/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package WePLi;

import WePLi.UI.ComponentSetting;
import WePLi.UI.JFrameSetting;
import WePLi.UI.JPanelSetting;
import WePLi.UI.JTableSetting;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Donghyeon <20183188>
 */



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

public class ChartFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
//    private static MainFrame mainFrame;
    private ArrayList<JPanel> panelList = new ArrayList<>();

    public ChartFrame() {
        JFrameSetting.layoutInit();

        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);

        panelList.add(chartPanel);
        panelList.add(playlistPanel);
        panelList.add(relaylistPanel);
        panelList.add(notifyPanel);

        /* ChartTable 기본 디자인 세팅 */
        JTableSetting.tableInit(chartScrollPanel, chartTable);
        JTableSetting.tableHeaderInit(chartTable, chartScrollPanel.getWidth(), 40);
        chartTableSetting();
        
        /* PlaylistTable 기본 디자인 세팅 */
        JTableSetting.tableInit(playlistScrollPanel, playlistTable);
        JTableSetting.tableHeaderInit(playlistTable, playlistPanel.getWidth(), 40);
        playlistTableSetting();
        
        /* 테스트 값 생성 */
        String url1 = "https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/082/662/688/82662688_1651196114166_1_600x600.JPG/dims/resize/Q_80,0";
        String url2 = "https://image.bugsm.co.kr/album/images/50/40756/4075667.jpg?version=20220515063240.0";

        Object[][] values = {
            {"1", ComponentSetting.imageToIcon(url1, 60,60), convertSongToHtml("회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"), "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", ComponentSetting.imageToIcon(url2, 60,60), "", "테스트", "테스트"},
            {"1", ComponentSetting.imageToIcon(url1, 60,60), convertSongToHtml("회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)"), "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", ComponentSetting.imageToIcon(url1, 60,60), convertSongToHtml("회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)"), "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", ComponentSetting.imageToIcon(url1, 60,60), convertSongToHtml("회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)"), "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", ComponentSetting.imageToIcon(url1, 60,60), convertSongToHtml("회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)"), "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", ComponentSetting.imageToIcon(url1, 60,60), convertSongToHtml("회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)"), "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
            {"1", ComponentSetting.imageToIcon(url1, 60,60), convertSongToHtml("회전목마 (Feat. Zion.T, 원슈타인) (Prod. Slom)", "디핵 (D-Hack)"), "디핵 (D-Hack)", "사랑인가 봐 (사내맞선 OST 스페셜 트랙)"},
        };

        /* 테이블에 값 추가*/
        JTableSetting.insertTableRow((DefaultTableModel) chartTable.getModel(), values);
        

        
    }
    
    
    
    public String convertPlaylistToHtml(String title, String author, String inform){
        return String.format("<html>\n" +
                        "<head>\n" +
                        "    <style>\n" +
                        "        p {margin-left: 20px;}" +
                        "        #author{\n" +
                        "            color: #a2a2a2;\n" +
                        "            font-size: 10px;\n" +
                        "        }\n" +
                        "        #inform{\n" +
                        "            color: #5D5D5D;\n" +
                        "            font-size: 11px;\n" +
                        "            margin-top: 10px;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <p id = \"title\">%s</p>\n" +
                        "    <p id = \"author\">%s</p>\n" +
                        "    <p id = \"inform\">%s</p>\n" +
                        "</body>\n" +
                        "</html>", title, author, inform);
    }
    
    public String convertSongToHtml(String title, String album){
        return String.format("<html>\n" +
                        "<head>\n" +
                        "    <style> #album{color: #a2a2a2;} </style>" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <p id=\"title\">%s</p>\n" +
                        "    <p id=\"album\">%s</p>\n" +
                        "</body>\n" +       
                    "</html>", title, album);
    }
    

    public void chartTableSetting() {
        /* 테이블 셀 사이즈 변경 */
        JTableSetting.setTableCellSize(this.chartTable, new int[]{70, 80, 464, 140, 140});
//        JTableSetting.setImageCell(this.chartTable, 1);
        chartTable.getColumnModel().getColumn(1).setCellRenderer(new PanelRenderer());

        /* 테이블 컬럼 중앙 정렬 */
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트 테이블 셀 렌더러 생성
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로

        TableColumnModel tableColumnModel = this.chartTable.getColumnModel();
        tableColumnModel.getColumn(0).setCellRenderer(dtcr);
        tableColumnModel.getColumn(3).setCellRenderer(dtcr);
        tableColumnModel.getColumn(4).setCellRenderer(dtcr);
    }

    public void playlistTableSetting(){
        /* 테이블 셀 사이즈 변경 */
        JTableSetting.setTableCellSize(this.playlistTable, new int[]{50, 100, 550 ,194});
//        playlistTable.getColumnModel().getColumn(1).setCellRenderer(new PanelRenderer());
                JTableSetting.setImageCell(playlistTable, 1);
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
        playlistPanel = new javax.swing.JPanel();
        playlistScrollPanel = new javax.swing.JScrollPane();
        playlistTable = new javax.swing.JTable();
        chartPanel = new javax.swing.JPanel();
        chartScrollPanel = new javax.swing.JScrollPane();
        chartTable = new javax.swing.JTable();
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

        playlistPanel.setBackground(new java.awt.Color(255, 255, 255));

        playlistScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        playlistScrollPanel.setBorder(null);
        playlistScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        playlistScrollPanel.setToolTipText("");
        playlistScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        playlistScrollPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                playlistScrollPanelMouseWheelMoved(evt);
            }
        });

        playlistTable.setBackground(new java.awt.Color(255,255,255,0));
        playlistTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        playlistTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "공백", "사진", "플레이리스트                                              ", "날짜"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playlistTable.setMinimumSize(new java.awt.Dimension(10, 400));
        playlistTable.setOpaque(false);
        playlistTable.setRowHeight(100);
        playlistTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        playlistTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        playlistTable.getTableHeader().setResizingAllowed(false);
        playlistTable.getTableHeader().setReorderingAllowed(false);
        playlistScrollPanel.setViewportView(playlistTable);

        javax.swing.GroupLayout playlistPanelLayout = new javax.swing.GroupLayout(playlistPanel);
        playlistPanel.setLayout(playlistPanelLayout);
        playlistPanelLayout.setHorizontalGroup(
            playlistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playlistPanelLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(playlistScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        playlistPanelLayout.setVerticalGroup(
            playlistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playlistPanelLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(playlistScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BackgroundPanel.add(playlistPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 910, 660));

        chartPanel.setBackground(new java.awt.Color(255, 255, 255));
        chartPanel.setOpaque(false);

        chartScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        chartScrollPanel.setBorder(null);
        chartScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chartScrollPanel.setToolTipText("");
        chartScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        chartScrollPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                chartScrollPanelMouseWheelMoved(evt);
            }
        });

        chartTable.setBackground(new java.awt.Color(255,255,255,0));
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
        chartTable.setOpaque(false);
        chartTable.setRowHeight(80);
        chartTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        chartTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        chartTable.getTableHeader().setResizingAllowed(false);
        chartTable.getTableHeader().setReorderingAllowed(false);
        chartScrollPanel.setViewportView(chartTable);

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chartPanelLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(chartScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chartPanelLayout.createSequentialGroup()
                .addContainerGap(125, Short.MAX_VALUE)
                .addComponent(chartScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BackgroundPanel.add(chartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 910, 660));

        notifyPanel.setBackground(new java.awt.Color(255, 255, 255));

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

        relaylistPanel.setBackground(new java.awt.Color(255, 255, 255));

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
        JTableSetting.tableScroll(chartTable, chartScrollPanel, evt);
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

    private void playlistScrollPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_playlistScrollPanelMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_playlistScrollPanelMouseWheelMoved

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ChartFrame().setVisible(true);
        });
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
    private javax.swing.JScrollPane playlistScrollPanel;
    private javax.swing.JTable playlistTable;
    private javax.swing.JPanel relaylistPanel;
    // End of variables declaration//GEN-END:variables
}
