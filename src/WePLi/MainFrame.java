/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package WePLi;

import Controller.Notification.NotificationController;
import WePLi.Enum.ListType;
import Controller.PlayBsideTrackController;
import Controller.PlaylistController;
import Controller.RelayBsideTrackController;
import Controller.RelayUserController;
import Controller.RelaylistController;
import Controller.SongController;
import Dto.PlayBsideTrack.PlayBsideTrackDto;
import Dto.Playlist.PlaylistCreateDto;
import Dto.Playlist.PlaylistDto;
import Dto.RelayBsideTrack.RelayBsideTrackDto;
import Dto.RelayUser.RelayUserDto;
import Dto.Relaylist.RelaylistCreateDto;
import Dto.Relaylist.RelaylistDto;
import Dto.Song.SongCreateDto;
import Dto.Song.SongDto;
import Entity.SongChart.SongChart;
import Observer.Observer;
import WePLi.SearchFrame.SearchFrame;
import WePLi.UI.ComponentSetting;
import WePLi.UI.DataParser;
import WePLi.UI.JFrameSetting;
import WePLi.UI.JPanelSetting;
import WePLi.UI.JTableSetting;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Donghyeon <20183188>
 */
public class MainFrame extends javax.swing.JFrame implements Observer {

    /**
     * Creates new form MainFrame
     */
    


    private int mouseX, mouseY;
    private int voteIndex = 0;
    private ArrayList<SongDto> recommendList;
    private ArrayList<RelayBsideTrackDto> votedList;
    private ArrayList<JPanel> panelList = new ArrayList<>();
    
    private SongController songController = SongController.getInstance();// 컨트롤러 생성
    private PlaylistController playlistController = PlaylistController.getInstance();
    private PlayBsideTrackController playBsideTrackController = PlayBsideTrackController.getInstance();
    private RelayBsideTrackController relayBsideTrackController = RelayBsideTrackController.getInstance();
    private RelaylistController relaylistController = RelaylistController.getInstance();
    private RelayUserController relayUserController = RelayUserController.getInstance();
    private NotificationController notificationController = NotificationController.getInstance();
    
    public MainFrame() {
        JFrameSetting.layoutInit();
        
        setResizable(false); // 크기 변경 불가능하도록 함
        setUndecorated(true); // 프레임의 타이틀바를 없앰
        
        initComponents();
        
        setVisible(true);
        setLocationRelativeTo(null);
        
        JPanelSetting.changePanel(panelList, chartPanel);
        
        // 옵저버로 등록        
        notificationController.registerObserver(this);
        // 진행 중인 릴레이리스트가 있는지 확인
        relaylistController.checkRelaylistTime();
    }
    
    @Override
    public void update() {
        // 알림 아이콘 표시
        this.notifyIconLabel.setVisible(true);
    }
    
    /*------------------------------- 플레이리스트 관련 메소드 --------------------------------*/
    private void createPlaylist(){
                
        // 플레이리스트 제목, 설명, 이미지, 작성자, 수록곡 필요
        TableModel tm = playBsideTable.getModel();
        int row = tm.getRowCount();
        
        if(row == 0) return;    // 한 곡도 선택하지 않은 경우
        
        // 제목, 가수, 이미지, 앨범 필요
        // 선택한 곡을 Songlist로 변환
        ArrayList<SongCreateDto> songlist = new ArrayList<>();        
        for (int i = 0; i < row; i++) songlist.add(DataParser.parseHtmlToSongCreateDto(tm.getValueAt(i, 2)));
        
        // 1. 선택한 곡 Song 테이블에 저장
        ArrayList<SongDto> songDtolist = songController.addSongList(songlist);
        
        // 2. 플레이리스트 저장
        PlaylistCreateDto playlistCreateDto = PlaylistCreateDto.builder()
                                    .title(createPlayTitleField.getText())
                                    .inform(createPlayInformTextArea.getText())
                                    .author(LoginUserLabel.getText())
                                    .image(songlist.get(0).getImage())
                                    .createTime(new java.sql.Date(new java.util.Date().getTime()))
                                    .build();
        
        PlaylistDto playlistDto = playlistController.createPlaylist(playlistCreateDto);
        
        // 3. 수록곡 저장
        ArrayList<PlayBsideTrackDto> bSideTrackDto = new ArrayList<>();
        
        for (SongDto songDto : songDtolist) {
            bSideTrackDto.add(PlayBsideTrackDto.builder()
                                               .playlistId(playlistDto.getId())
                                               .songId(songDto.getId())
                                               .build());
        }
        
        playBsideTrackController.addPlayBsideTrack(bSideTrackDto);
        
        JOptionPane.showMessageDialog(null, "플레이리스트 생성이 완료 되었습니다.");
        
        JPanelSetting.changePanel(panelList, playlistPanel);
        initPlaylistPanel();
    }
        
    private Object[][] getPlaylist(TableModel model, int row){
        // 플레이리스트 아이디 가져오기
        Document doc = Jsoup.parse(model.getValueAt(row, 2).toString());
        
        Element element = doc.selectFirst("body");
        String listId = element.select("#listId").attr("value");
        
        //1. 플레이리스트 아이디로 플레이리스트 가져오기
        PlaylistDto playlist = playlistController.getPlaylist(listId);
        
        //2. 플레이리스트 조회 화면 설정
        playImageLabel.setIcon(ComponentSetting.imageToIcon(playlist.getImage(), 260, 260)); // 썸네일 지정
        playTitleLabel.setText(playlist.getTitle());                                         // 제목 지정
        playDateLabel.setText(playlist.getCreateTime().toString());                          // 생성 날짜 지정
        playInformLabel.setText(playlist.getInform());                                       // 설명 지정
        playAuthorLabel.setText(playlist.getAuthor());                                       // 작성자 지정
        playIdLabel.setText(playlist.getId());                                               // 플레이리스트 아이디 지정
        
        //3. 수록곡 가져오기
        ArrayList<SongDto> sideTrack = songController.getBsideTrack("playBsideTrack", listId);
        
        //4. 수록곡 테이블에 노래 삽입
        Object[][] value = DataParser.songDtoToObject(sideTrack, 0);
        
        return value;
    }
      
    private Object[][] getAllPlaylists(DefaultTableModel model){
        // 플레이리스트를 전부 가져옴
        ArrayList<PlaylistDto> playlist = playlistController.getAllPlaylists();

        Object[][] data = new Object[playlist.size()][];
        
        for (int i = 0; i < playlist.size(); i++) {
            // 플레이리스트 정보 추출
            String playlistId = playlist.get(i).getId();
            String title = playlist.get(i).getTitle();
            String author = playlist.get(i).getAuthor();
            String inform = playlist.get(i).getInform();
            String imageUrl = playlist.get(i).getImage();
            ImageIcon image = ComponentSetting.imageToIcon(imageUrl, 100, 100);
            java.sql.Date createTime = playlist.get(i).getCreateTime();
            
            data[i] = new Object[]{
                model.getRowCount() + (i + 1),
                image,
                DataParser.convertListToHtml(playlistId, title, author, inform),
                createTime
            };
        }
        
        return data;
    }
    
    private void initPlaylistPanel(){
        // 테이블 초기화
        DefaultTableModel model = (DefaultTableModel) playlistTable.getModel();
        model.setRowCount(0);
        
        // 플레이리스트 목록을 가져옴
        Object[][] data = getAllPlaylists(model);
        
        // 테이블에 플레이리스트 삽입
        JTableSetting.insertTableRow((DefaultTableModel) playlistTable.getModel(), data);
    }
    /*----------------------------  플레이리스트 관련 메소드 끝 -------------------------------*/
        
    /*------------------------------- 릴레이리스트 관련 메소드 --------------------------------*/
    // 릴레이리스트 생성 메소드
    private void createRelaylist(){
        TableModel tm = this.relayFirstTable.getModel();
        int row = tm.getRowCount();
        
        if(row == 0) return;    // 한 곡도 선택하지 않은 경우
        
        // 제목, 가수, 이미지, 앨범 필요
        // 선택한 곡을 Songlist로 변환
        ArrayList<SongCreateDto> songlist = new ArrayList<>();        
        SongCreateDto firstSong = DataParser.parseHtmlToSongCreateDto(tm.getValueAt(0, 2));  
        songlist.add(firstSong); // 리스트 타입으로 전달 (addSongList 형식 맞추기 위해서)
        
        // 1. 선택한 곡 Song 테이블에 저장
        ArrayList<SongDto> songDtolist = songController.addSongList(songlist);
        
        // 2. 릴레이리스트 저장
        RelaylistCreateDto relaylistCreateDto = RelaylistCreateDto.builder()
                                               .title(createRelayTitleField.getText())
                                               .inform(createRelayInformTextArea.getText())
                                               .author(LoginUserLabel.getText())
                                               .firstSongTitle(firstSong.getTitle())
                                               .firstSongSinger(firstSong.getSinger())
                                               .firstSongAlbum(firstSong.getAlbum())
                                               .firstSongImage(firstSong.getImage())
                                               .createTime(new java.sql.Date(new java.util.Date().getTime()))
                                               .build();
        
        
        RelaylistDto relaylistDto = relaylistController.createRelaylist(relaylistCreateDto);
        
        if(!Objects.isNull(relaylistDto)) {
            JOptionPane.showMessageDialog(null, "릴레이리스트 생성이 완료 되었습니다.");
            JPanelSetting.changePanel(panelList, relaylistPanel);
            initRelaylistPanel();
        }
        else JOptionPane.showMessageDialog(null, "릴레이리스트 생성에 실패 했습니다.");
    }
    
    // 릴레이리스트 목록 출력(조회) 메소드
    private Object[][] getAllRelaylists(DefaultTableModel model){
        // 1. 모든 릴레이리스트 가져오기
        ArrayList<RelaylistDto> relaylists = relaylistController.getRelaylists();
        Object[][] data = new Object[relaylists.size()][];
        
        // 2. 릴레이리스트 정보 추출
        for (int i = 0; i < relaylists.size(); i++) {
            String relaylistId = relaylists.get(i).getId();
            String title = relaylists.get(i).getTitle();
            String author = relaylists.get(i).getAuthor();
            String inform = relaylists.get(i).getInform();
            String imageUrl = relaylists.get(i).getFirstSongImage();
            ImageIcon image = ComponentSetting.imageToIcon(imageUrl, 100, 100);
            java.sql.Date createTime = relaylists.get(i).getCreateTime();
            
            data[i] = new Object[]{
                model.getRowCount() + (i + 1),
                image,
                DataParser.convertListToHtml(relaylistId, title, author, inform),          // 릴레이리스트 정보를 Html 형식으로 변환
                createTime
            };
        }
                
        return data;
    }
    
        private void initRelaylistPanel(){
        // 테이블 초기화
        DefaultTableModel model = (DefaultTableModel) relaylistTable.getModel();
        model.setRowCount(0);
        
        // 플레이리스트 목록을 가져옴
        Object[][] data = getAllRelaylists(model);
        
        // 테이블에 플레이리스트 삽입
        JTableSetting.insertTableRow((DefaultTableModel) relaylistTable.getModel(), data);
    }
    /*----------------------------- 릴레이리스트 관련 메소드 끝 --------------------------------*/
       
    /*----------------------------- 곡 투표 관련 메소드 - --------------------------------*/
    
    private RelayBsideTrackDto getCurrentVoteSong(){
        int songId = Integer.parseInt(relayVoteSongIdLabel.getText());
        String listId = relayVoteListIdLabel.getText();
        
        return RelayBsideTrackDto.builder()
                                 .relaylistId(listId)
                                 .songId(songId)
                                 .build();
    }
    
    /*----------------------------- 곡 투표 관련 메소드 끝 --------------------------------*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackgroundPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ExitLabel = new javax.swing.JLabel();
        FrameDragBar = new javax.swing.JLabel();
        emptyLabel = new javax.swing.JLabel();
        LoginUserLabel = new javax.swing.JLabel();
        HomeLabel = new javax.swing.JLabel();
        PlaylistLabel = new javax.swing.JLabel();
        RelaylistLabel = new javax.swing.JLabel();
        notifyIconLabel = new javax.swing.JLabel();
        NotifyLabel = new javax.swing.JLabel();
        HeaderLabel = new javax.swing.JLabel();
        SidebarLabel = new javax.swing.JLabel();
        notifyPanel = new javax.swing.JPanel();
        notifyScrollPanel = new javax.swing.JScrollPane();
        notifyTable = new javax.swing.JTable();
        profilePanel = new javax.swing.JPanel();
        profileBgLabel = new javax.swing.JLabel();
        playlistTabLabel = new javax.swing.JLabel();
        relaylistTabLabel = new javax.swing.JLabel();
        myListScrollPanel = new javax.swing.JScrollPane();
        myListTable = new javax.swing.JTable();
        relaylistPanel = new javax.swing.JPanel();
        addRelaylistBtn = new javax.swing.JButton();
        relaylistScrollPanel = new javax.swing.JScrollPane();
        relaylistTable = new javax.swing.JTable();
        playlistPanel = new javax.swing.JPanel();
        addPlaylistBtn = new javax.swing.JButton();
        playlistScrollPanel = new javax.swing.JScrollPane();
        playlistTable = new javax.swing.JTable();
        relayVotePanel = new javax.swing.JPanel();
        relayVoteImageLabel = new javax.swing.JLabel();
        relayVoteSingerLabel = new javax.swing.JLabel();
        relayVoteTitleLabel = new javax.swing.JLabel();
        relayVoteSongIdLabel = new javax.swing.JLabel();
        relayVoteListIdLabel = new javax.swing.JLabel();
        relayVoteBackBtn = new javax.swing.JButton();
        relayVoteForwardBtn = new javax.swing.JButton();
        relayVoteNotPickBtn = new javax.swing.JButton();
        relayVotePickBtn = new javax.swing.JButton();
        relayVoteBlurLabel = new javax.swing.JLabel();
        relayVoteBGLabel = new javax.swing.JLabel();
        createRelayPanel = new javax.swing.JPanel();
        relayInformScrollPanel = new javax.swing.JScrollPane();
        createRelayInformTextArea = new javax.swing.JTextArea();
        createRelayTitleField = new javax.swing.JTextField();
        relayFirstScrollPanel = new javax.swing.JScrollPane();
        relayFirstTable = new javax.swing.JTable();
        addSongBtn = new javax.swing.JButton();
        createRelayBtn = new javax.swing.JButton();
        createRelayInformLabel = new javax.swing.JLabel();
        createRelayTitleLabel = new javax.swing.JLabel();
        createRelayImgLabel = new javax.swing.JLabel();
        createRelayBGLabel = new javax.swing.JLabel();
        createPlayPanel = new javax.swing.JPanel();
        playInformScrollPanel = new javax.swing.JScrollPane();
        createPlayInformTextArea = new javax.swing.JTextArea();
        playBsideScrollPanel = new javax.swing.JScrollPane();
        playBsideTable = new javax.swing.JTable();
        createPlayTitleField = new javax.swing.JTextField();
        createPlayBtn = new javax.swing.JButton();
        addTrackBtn = new javax.swing.JButton();
        createPlayImgLabel = new javax.swing.JLabel();
        createPlayTitleLabel = new javax.swing.JLabel();
        createPlayInformLabel = new javax.swing.JLabel();
        createPlayBGLabel = new javax.swing.JLabel();
        relaylistDetailPanel = new javax.swing.JPanel();
        relaylistInformLabel = new javax.swing.JLabel();
        firstSongImageLabel = new javax.swing.JLabel();
        firstSongTitleLabel = new javax.swing.JLabel();
        relaylistTitleLabel = new javax.swing.JLabel();
        firstSongSingerLabel = new javax.swing.JLabel();
        blurLabel = new javax.swing.JLabel();
        relayImageLabel = new javax.swing.JLabel();
        voteLabel = new javax.swing.JLabel();
        recommendLabel = new javax.swing.JLabel();
        relaylistIdLabel = new javax.swing.JLabel();
        relayDetailScrollPanel = new javax.swing.JScrollPane();
        relayDetailTable = new javax.swing.JTable();
        playlistDetailPanel = new javax.swing.JPanel();
        playDeleteBtn = new javax.swing.JButton();
        playEditBtn = new javax.swing.JButton();
        playImageLabel = new javax.swing.JLabel();
        playTitleLabel = new javax.swing.JLabel();
        playInformLabel = new javax.swing.JLabel();
        playDateLabel = new javax.swing.JLabel();
        playAuthorLabel = new javax.swing.JLabel();
        playIdLabel = new javax.swing.JLabel();
        playDetailScrollPanel = new javax.swing.JScrollPane();
        playDetailTable = new javax.swing.JTable();
        chartPanel = new javax.swing.JPanel();
        chartScrollPanel = new javax.swing.JScrollPane();
        chartTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        BackgroundPanel.setBackground(new java.awt.Color(255, 255, 255));
        BackgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/background/profile.png"))); // NOI18N
        BackgroundPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 680, 24, 23));

        ExitLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/exit_btn.png"))); // NOI18N
        ExitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExitLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ExitLabelMouseEntered(evt);
            }
        });
        BackgroundPanel.add(ExitLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 0, 50, 60));

        FrameDragBar.setBackground(new java.awt.Color(255,255,255,0));
        FrameDragBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                FrameDragBarMouseDragged(evt);
            }
        });
        FrameDragBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                FrameDragBarMousePressed(evt);
            }
        });
        BackgroundPanel.add(FrameDragBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 60));

        emptyLabel.setBackground(new java.awt.Color(255, 255, 255));
        emptyLabel.setOpaque(true);
        BackgroundPanel.add(emptyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, 200, 60));

        LoginUserLabel.setFont(new java.awt.Font("나눔스퀘어 Bold", 1, 16)); // NOI18N
        LoginUserLabel.setForeground(new java.awt.Color(187,187,187));
        LoginUserLabel.setText("admin");
        LoginUserLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoginUserLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LoginUserLabelMouseEntered(evt);
            }
        });
        BackgroundPanel.add(LoginUserLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 673, 110, 40));

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

        notifyIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/component/normal/notify.png"))); // NOI18N
        notifyIconLabel.setText("jLabel2");
        BackgroundPanel.add(notifyIconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 244, 3, 15));
        notifyIconLabel.setVisible(false);

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

        notifyPanel.setBackground(new java.awt.Color(255, 255, 255));

        notifyScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        notifyScrollPanel.setBorder(null);
        notifyScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        notifyScrollPanel.setToolTipText("");
        notifyScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        notifyTable.setBackground(new java.awt.Color(255,255,255,0));
        notifyTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        notifyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "번호", "커버", "알림 내용", "날짜"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        notifyTable.setMinimumSize(new java.awt.Dimension(10, 400));
        notifyTable.setOpaque(false);
        notifyTable.setRowHeight(100);
        notifyTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        notifyTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        notifyTable.getTableHeader().setResizingAllowed(false);
        notifyTable.getTableHeader().setReorderingAllowed(false);
        notifyTable.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                notifyTableMouseWheelMoved(evt);
            }
        });
        notifyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notifyTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                notifyTableMouseEntered(evt);
            }
        });
        notifyScrollPanel.setViewportView(notifyTable);
        /* PlaylistTable 기본 세팅 */
        JTableSetting.tableInit(notifyScrollPanel, notifyTable);
        JTableSetting.tableHeaderInit(notifyTable, notifyScrollPanel.getWidth(), 40);
        JTableSetting.listTableSetting(notifyTable);

        javax.swing.GroupLayout notifyPanelLayout = new javax.swing.GroupLayout(notifyPanel);
        notifyPanel.setLayout(notifyPanelLayout);
        notifyPanelLayout.setHorizontalGroup(
            notifyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notifyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(notifyScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        notifyPanelLayout.setVerticalGroup(
            notifyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, notifyPanelLayout.createSequentialGroup()
                .addContainerGap(154, Short.MAX_VALUE)
                .addComponent(notifyScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BackgroundPanel.add(notifyPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 910, 660));
        panelList.add(notifyPanel);

        profilePanel.setBackground(new java.awt.Color(255, 255, 255));
        profilePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profileBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/background/profileBackground.png"))); // NOI18N
        profilePanel.add(profileBgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 912, 245));

        playlistTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/field/normal/myPlaylist.png"))); // NOI18N
        playlistTabLabel.setToolTipText("");
        playlistTabLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playlistTabLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playlistTabLabelMouseExited(evt);
            }
        });
        profilePanel.add(playlistTabLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 456, 27));

        relaylistTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/field/normal/myRelaylist.png"))); // NOI18N
        relaylistTabLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                relaylistTabLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                relaylistTabLabelMouseExited(evt);
            }
        });
        profilePanel.add(relaylistTabLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 259, 456, 27));

        myListScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        myListScrollPanel.setBorder(null);
        myListScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myListScrollPanel.setToolTipText("");
        myListScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        myListTable.setBackground(new java.awt.Color(255,255,255,0));
        myListTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        myListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "번호", "커버", "리스트", "생성일"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        myListTable.setMinimumSize(new java.awt.Dimension(10, 400));
        myListTable.setOpaque(false);
        myListTable.setRowHeight(100);
        myListTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        myListTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        myListTable.getTableHeader().setResizingAllowed(false);
        myListTable.getTableHeader().setReorderingAllowed(false);
        myListTable.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                myListTableMouseWheelMoved(evt);
            }
        });
        myListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                myListTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                myListTableMouseEntered(evt);
            }
        });
        myListScrollPanel.setViewportView(myListTable);
        /* PlaylistTable 기본 세팅 */
        JTableSetting.tableInit(myListScrollPanel, myListTable);
        JTableSetting.tableHeaderInit(myListTable, myListScrollPanel.getWidth(), 40);
        JTableSetting.listTableSetting(myListTable);

        profilePanel.add(myListScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 286, 896, 370));

        BackgroundPanel.add(profilePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 912, 660));
        this.panelList.add(profilePanel);

        relaylistPanel.setBackground(new java.awt.Color(255, 255, 255));
        relaylistPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addRelaylistBtn.setBackground(new java.awt.Color(255,255,255,0));
        addRelaylistBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/addTrack_btn.png"))); // NOI18N
        addRelaylistBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addRelaylistBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addRelaylistBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addRelaylistBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addRelaylistBtnMouseExited(evt);
            }
        });
        relaylistPanel.add(addRelaylistBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 120, 57, 22));

        relaylistScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        relaylistScrollPanel.setBorder(null);
        relaylistScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        relaylistScrollPanel.setToolTipText("");
        relaylistScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        relaylistTable.setBackground(new java.awt.Color(255,255,255,0));
        relaylistTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        relaylistTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "공백", "사진", "릴레이리스트                                              ", "날짜"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        relaylistTable.setMinimumSize(new java.awt.Dimension(10, 400));
        relaylistTable.setOpaque(false);
        relaylistTable.setRowHeight(100);
        relaylistTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        relaylistTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        relaylistTable.getTableHeader().setResizingAllowed(false);
        relaylistTable.getTableHeader().setReorderingAllowed(false);
        relaylistTable.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                relaylistTableMouseWheelMoved(evt);
            }
        });
        relaylistTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relaylistTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                relaylistTableMouseEntered(evt);
            }
        });
        relaylistScrollPanel.setViewportView(relaylistTable);
        /* PlaylistTable 기본 세팅 */
        JTableSetting.tableInit(relaylistScrollPanel, relaylistTable);
        JTableSetting.tableHeaderInit(relaylistTable, relaylistPanel.getWidth(), 40);
        JTableSetting.listTableSetting(relaylistTable);

        relaylistPanel.add(relaylistScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 154, 896, 500));

        BackgroundPanel.add(relaylistPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 910, 660));
        panelList.add(relaylistPanel);

        playlistPanel.setBackground(new java.awt.Color(255, 255, 255));
        playlistPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addPlaylistBtn.setBackground(new java.awt.Color(255,255,255,0));
        addPlaylistBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/addTrack_btn.png"))); // NOI18N
        addPlaylistBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addPlaylistBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addPlaylistBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addPlaylistBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addPlaylistBtnMouseExited(evt);
            }
        });
        playlistPanel.add(addPlaylistBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 120, 57, 22));

        playlistScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        playlistScrollPanel.setBorder(null);
        playlistScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        playlistScrollPanel.setToolTipText("");
        playlistScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

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
                false, false, false, false
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
        playlistTable.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                playlistTableMouseWheelMoved(evt);
            }
        });
        playlistTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playlistTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playlistTableMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                playlistTableMousePressed(evt);
            }
        });
        playlistScrollPanel.setViewportView(playlistTable);
        /* PlaylistTable 기본 세팅 */
        JTableSetting.tableInit(playlistScrollPanel, playlistTable);
        JTableSetting.tableHeaderInit(playlistTable, playlistPanel.getWidth(), 40);
        JTableSetting.listTableSetting(playlistTable);

        playlistPanel.add(playlistScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 154, 896, 500));

        BackgroundPanel.add(playlistPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 910, 660));
        panelList.add(playlistPanel);

        relayVotePanel.setBackground(new java.awt.Color(255, 255, 255));
        relayVotePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        relayVotePanel.add(relayVoteImageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 50, 350, 350));

        relayVoteSingerLabel.setFont(new java.awt.Font("나눔스퀘어", 0, 18)); // NOI18N
        relayVoteSingerLabel.setForeground(new java.awt.Color(255,255,255,170));
        relayVoteSingerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        relayVoteSingerLabel.setText("윤하(YUNHA)");
        relayVotePanel.add(relayVoteSingerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, 810, 40));

        relayVoteTitleLabel.setFont(new java.awt.Font("나눔스퀘어 Bold", 0, 28)); // NOI18N
        relayVoteTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        relayVoteTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        relayVoteTitleLabel.setText("사건의 지평선");
        relayVotePanel.add(relayVoteTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 810, 40));

        relayVoteSongIdLabel.setForeground(new java.awt.Color(255,255,255,0));
        relayVotePanel.add(relayVoteSongIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 150, 30));

        relayVoteListIdLabel.setForeground(new java.awt.Color(255,255,255,0));
        relayVotePanel.add(relayVoteListIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 617, 150, 30));

        relayVoteBackBtn.setBackground(new java.awt.Color(255,255,255,0));
        relayVoteBackBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/vote_back_btn.png"))); // NOI18N
        relayVoteBackBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relayVoteBackBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                relayVoteBackBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                relayVoteBackBtnMouseExited(evt);
            }
        });
        relayVotePanel.add(relayVoteBackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 33, 64));

        relayVoteForwardBtn.setBackground(new java.awt.Color(255,255,255,0));
        relayVoteForwardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/vote_forward_btn.png"))); // NOI18N
        relayVoteForwardBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relayVoteForwardBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                relayVoteForwardBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                relayVoteForwardBtnMouseExited(evt);
            }
        });
        relayVotePanel.add(relayVoteForwardBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 200, 33, 64));

        relayVoteNotPickBtn.setBackground(new java.awt.Color(255,255,255,0));
        relayVoteNotPickBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/pick_btn.png"))); // NOI18N
        relayVoteNotPickBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relayVoteNotPickBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                relayVoteNotPickBtnMouseEntered(evt);
            }
        });
        relayVotePanel.add(relayVoteNotPickBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(436, 530, 40, 40));

        relayVotePickBtn.setBackground(new java.awt.Color(255,255,255,0));
        relayVotePickBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/hover/pick_btn_clicked.png"))); // NOI18N
        relayVotePickBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relayVotePickBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                relayVotePickBtnMouseEntered(evt);
            }
        });
        relayVotePanel.add(relayVotePickBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 529, 40, 40));
        this.relayVotePickBtn.setVisible(false);

        relayVoteBlurLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/background/blur912.png"))); // NOI18N
        relayVotePanel.add(relayVoteBlurLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 912, 660));
        relayVotePanel.add(relayVoteBGLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 912, 660));

        BackgroundPanel.add(relayVotePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 912, 660));
        panelList.add(relayVotePanel);

        createRelayPanel.setBackground(new java.awt.Color(255, 255, 255));
        createRelayPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        relayInformScrollPanel.setBackground(new java.awt.Color(255, 255, 255));
        relayInformScrollPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        createRelayInformTextArea.setBackground(new java.awt.Color(255, 255, 255));
        createRelayInformTextArea.setColumns(20);
        createRelayInformTextArea.setFont(new java.awt.Font("나눔스퀘어", 0, 12)); // NOI18N
        createRelayInformTextArea.setRows(5);
        createRelayInformTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        createRelayInformTextArea.setOpaque(false);
        createRelayInformTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                createRelayInformTextAreaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                createRelayInformTextAreaFocusLost(evt);
            }
        });
        relayInformScrollPanel.setViewportView(createRelayInformTextArea);

        createRelayPanel.add(relayInformScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 185, 530, 90));

        createRelayTitleField.setBackground(new java.awt.Color(255,255,255,0));
        createRelayTitleField.setFont(new java.awt.Font("나눔스퀘어", 0, 12)); // NOI18N
        createRelayTitleField.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        createRelayTitleField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                createRelayTitleFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                createRelayTitleFieldFocusLost(evt);
            }
        });
        createRelayPanel.add(createRelayTitleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 75, 530, 30));

        relayFirstScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        relayFirstScrollPanel.setBorder(null);
        relayFirstScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        relayFirstScrollPanel.setToolTipText("");
        relayFirstScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        relayFirstScrollPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                relayFirstScrollPanelMouseWheelMoved(evt);
            }
        });

        relayFirstTable.setBackground(new java.awt.Color(255,255,255,0));
        relayFirstTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        relayFirstTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "순위", "커버", "곡/앨범", "가수"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        relayFirstTable.setMinimumSize(new java.awt.Dimension(10, 400));
        relayFirstTable.setOpaque(false);
        relayFirstTable.setRowHeight(80);
        relayFirstTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        relayFirstTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        relayFirstTable.getTableHeader().setResizingAllowed(false);
        relayFirstTable.getTableHeader().setReorderingAllowed(false);
        relayFirstTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relayFirstTableMouseClicked(evt);
            }
        });
        relayFirstScrollPanel.setViewportView(relayFirstTable);
        /* ChartTable 기본 디자인 세팅 */
        JTableSetting.tableInit(relayFirstScrollPanel, relayFirstTable);
        JTableSetting.tableHeaderInit(relayFirstTable, relayFirstScrollPanel.getWidth(), 40);
        JTableSetting.songTableSetting(relayFirstTable);

        createRelayPanel.add(relayFirstScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 349, 896, 305));

        addSongBtn.setBackground(new java.awt.Color(255,255,255,0));
        addSongBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/addTrack_btn.png"))); // NOI18N
        addSongBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addSongBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSongBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addSongBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addSongBtnMouseExited(evt);
            }
        });
        createRelayPanel.add(addSongBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 310, 57, 22));

        createRelayBtn.setBackground(new java.awt.Color(255,255,255,0));
        createRelayBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/playCreate_btn.png"))); // NOI18N
        createRelayBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        createRelayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createRelayBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createRelayBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createRelayBtnMouseExited(evt);
            }
        });
        createRelayPanel.add(createRelayBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 310, 57, 22));

        createRelayInformLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/field/normal/playInform_field.png"))); // NOI18N
        createRelayPanel.add(createRelayInformLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 175, 568, 109));

        createRelayTitleLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/field/normal/playTitle_field.png"))); // NOI18N
        createRelayPanel.add(createRelayTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 568, 42));
        createRelayPanel.add(createRelayImgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 24, 260, 260));

        createRelayBGLabel.setBackground(new java.awt.Color(255,255,255,0));
        createRelayBGLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/background/createRelaylistBG.png"))); // NOI18N
        createRelayPanel.add(createRelayBGLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 912, 362));

        BackgroundPanel.add(createRelayPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 912, 660));
        panelList.add(createRelayPanel);

        createPlayPanel.setBackground(new java.awt.Color(255, 255, 255));
        createPlayPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        playInformScrollPanel.setBackground(new java.awt.Color(255, 255, 255));
        playInformScrollPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        createPlayInformTextArea.setBackground(new java.awt.Color(255, 255, 255));
        createPlayInformTextArea.setColumns(20);
        createPlayInformTextArea.setFont(new java.awt.Font("나눔스퀘어", 0, 12)); // NOI18N
        createPlayInformTextArea.setRows(5);
        createPlayInformTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        createPlayInformTextArea.setOpaque(false);
        createPlayInformTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                createPlayInformTextAreaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                createPlayInformTextAreaFocusLost(evt);
            }
        });
        playInformScrollPanel.setViewportView(createPlayInformTextArea);

        createPlayPanel.add(playInformScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 185, 530, 90));

        playBsideScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        playBsideScrollPanel.setBorder(null);
        playBsideScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        playBsideScrollPanel.setToolTipText("");
        playBsideScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        playBsideScrollPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                playBsideScrollPanelMouseWheelMoved(evt);
            }
        });

        playBsideTable.setBackground(new java.awt.Color(255,255,255,0));
        playBsideTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        playBsideTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "순위", "커버", "곡/앨범", "가수"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playBsideTable.setMinimumSize(new java.awt.Dimension(10, 400));
        playBsideTable.setOpaque(false);
        playBsideTable.setRowHeight(80);
        playBsideTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        playBsideTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        playBsideTable.getTableHeader().setResizingAllowed(false);
        playBsideTable.getTableHeader().setReorderingAllowed(false);
        playBsideTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playBsideTableMouseClicked(evt);
            }
        });
        playBsideScrollPanel.setViewportView(playBsideTable);
        /* ChartTable 기본 디자인 세팅 */
        JTableSetting.tableInit(playBsideScrollPanel, playBsideTable);
        JTableSetting.tableHeaderInit(playBsideTable, playBsideScrollPanel.getWidth(), 40);
        JTableSetting.songTableSetting(playBsideTable);

        createPlayPanel.add(playBsideScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 349, 896, 305));

        createPlayTitleField.setBackground(new java.awt.Color(255,255,255,0));
        createPlayTitleField.setFont(new java.awt.Font("나눔스퀘어", 0, 12)); // NOI18N
        createPlayTitleField.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        createPlayTitleField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                createPlayTitleFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                createPlayTitleFieldFocusLost(evt);
            }
        });
        createPlayPanel.add(createPlayTitleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 75, 530, 30));

        createPlayBtn.setBackground(new java.awt.Color(255,255,255,0));
        createPlayBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/playCreate_btn.png"))); // NOI18N
        createPlayBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        createPlayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createPlayBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createPlayBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createPlayBtnMouseExited(evt);
            }
        });
        createPlayPanel.add(createPlayBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 310, 57, 22));

        addTrackBtn.setBackground(new java.awt.Color(255,255,255,0));
        addTrackBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/addTrack_btn.png"))); // NOI18N
        addTrackBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addTrackBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTrackBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addTrackBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addTrackBtnMouseExited(evt);
            }
        });
        createPlayPanel.add(addTrackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 310, 57, 22));
        createPlayPanel.add(createPlayImgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 24, 260, 260));

        createPlayTitleLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/field/normal/playTitle_field.png"))); // NOI18N
        createPlayPanel.add(createPlayTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 568, 42));

        createPlayInformLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/field/normal/playInform_field.png"))); // NOI18N
        createPlayPanel.add(createPlayInformLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 175, 568, 109));

        createPlayBGLabel.setBackground(new java.awt.Color(255,255,255,0));
        createPlayBGLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/background/createPlaylistBG.png"))); // NOI18N
        createPlayPanel.add(createPlayBGLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 912, 362));

        BackgroundPanel.add(createPlayPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 912, 660));
        panelList.add(createPlayPanel);

        relaylistDetailPanel.setBackground(new java.awt.Color(255, 255, 255));
        relaylistDetailPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        relaylistInformLabel.setFont(new java.awt.Font("AppleSDGothicNeoSB00", 0, 14)); // NOI18N
        relaylistInformLabel.setForeground(new java.awt.Color(204, 204, 204));
        relaylistInformLabel.setText("도토리를 훔쳐간 싸이월드 BGM");
        relaylistInformLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        relaylistDetailPanel.add(relaylistInformLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 140, 530, 40));

        firstSongImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/test/younha.jpg"))); // NOI18N
        relaylistDetailPanel.add(firstSongImageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 250, 250));

        firstSongTitleLabel.setFont(new java.awt.Font("AppleSDGothicNeoSB00", 0, 18)); // NOI18N
        firstSongTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        firstSongTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstSongTitleLabel.setText("오늘 헤어 졌어요");
        relaylistDetailPanel.add(firstSongTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 225, 540, 30));

        relaylistTitleLabel.setFont(new java.awt.Font("나눔스퀘어 Bold", 1, 36)); // NOI18N
        relaylistTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        relaylistTitleLabel.setText("<html><p>도토리를 훔쳐간<br>싸이월드 BGM</p></html>");
        relaylistTitleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        relaylistDetailPanel.add(relaylistTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 50, 530, 100));

        firstSongSingerLabel.setFont(new java.awt.Font("AppleSDGothicNeoSB00", 0, 16)); // NOI18N
        firstSongSingerLabel.setForeground(new java.awt.Color(204, 204, 204));
        firstSongSingerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstSongSingerLabel.setText("윤하");
        firstSongSingerLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        relaylistDetailPanel.add(firstSongSingerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 255, 540, 20));

        blurLabel.setBackground(new java.awt.Color(0,0,0,0));
        blurLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/background/blur.png"))); // NOI18N
        relaylistDetailPanel.add(blurLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 912, 290));

        relayImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/test/younha.jpg"))); // NOI18N
        relaylistDetailPanel.add(relayImageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 290));

        voteLabel.setFont(new java.awt.Font("AppleSDGothicNeoSB00", 0, 16)); // NOI18N
        voteLabel.setForeground(new java.awt.Color(187,187,187));
        voteLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/vote_btn.png"))); // NOI18N
        voteLabel.setText("  곡 투표하기");
        voteLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                voteLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                voteLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                voteLabelMouseExited(evt);
            }
        });
        relaylistDetailPanel.add(voteLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 610, 120, 40));

        recommendLabel.setFont(new java.awt.Font("AppleSDGothicNeoSB00", 0, 16)); // NOI18N
        recommendLabel.setForeground(new java.awt.Color(187,187,187));
        recommendLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/recommend_btn.png"))); // NOI18N
        recommendLabel.setText("  곡 추천하기");
        recommendLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recommendLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                recommendLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                recommendLabelMouseExited(evt);
            }
        });
        relaylistDetailPanel.add(recommendLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 610, 120, 40));

        relaylistIdLabel.setForeground(new java.awt.Color(255, 255, 255));
        relaylistDetailPanel.add(relaylistIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 190, 20));

        relayDetailScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        relayDetailScrollPanel.setBorder(null);
        relayDetailScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        relayDetailScrollPanel.setToolTipText("");
        relayDetailScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        relayDetailScrollPanel.setOpaque(false);
        relayDetailScrollPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                relayDetailScrollPanelMouseWheelMoved(evt);
            }
        });

        relayDetailTable.setBackground(new java.awt.Color(255,255,255,0));
        relayDetailTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        relayDetailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "번호", "커버", "곡/앨범", "가수"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        relayDetailTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        relayDetailTable.setMinimumSize(new java.awt.Dimension(10, 400));
        relayDetailTable.setOpaque(false);
        relayDetailTable.setRowHeight(80);
        relayDetailTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        relayDetailTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        relayDetailTable.getTableHeader().setResizingAllowed(false);
        relayDetailTable.getTableHeader().setReorderingAllowed(false);
        relayDetailTable.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                relayDetailTableMouseWheelMoved(evt);
            }
        });
        relayDetailTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relayDetailTableMouseClicked(evt);
            }
        });
        relayDetailScrollPanel.setViewportView(relayDetailTable);
        /* PlayDetailTable 기본 디자인 세팅 */
        JTableSetting.tableInit(relaylistScrollPanel, relayDetailTable);
        JTableSetting.tableHeaderInit(relayDetailTable, relaylistScrollPanel.getWidth(), 40);
        JTableSetting.songTableSetting(relayDetailTable);

        relaylistDetailPanel.add(relayDetailScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 300, 900, 310));

        BackgroundPanel.add(relaylistDetailPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 912, 660));
        panelList.add(relaylistDetailPanel);

        playlistDetailPanel.setBackground(new java.awt.Color(255, 255, 255));
        playlistDetailPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        playDeleteBtn.setBackground(new java.awt.Color(255,255,255,0));
        playDeleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/delete_btn.png"))); // NOI18N
        playDeleteBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        playDeleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playDeleteBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playDeleteBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playDeleteBtnMouseExited(evt);
            }
        });
        playlistDetailPanel.add(playDeleteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, 57, 22));

        playEditBtn.setBackground(new java.awt.Color(255,255,255,0));
        playEditBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/layout/button/normal/edit_btn.png"))); // NOI18N
        playEditBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        playEditBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playEditBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playEditBtnMouseExited(evt);
            }
        });
        playlistDetailPanel.add(playEditBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 57, 22));

        playImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/test/younha.jpg"))); // NOI18N
        playlistDetailPanel.add(playImageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 260, 260));

        playTitleLabel.setFont(new java.awt.Font("나눔스퀘어 Bold", 0, 36)); // NOI18N
        playTitleLabel.setForeground(new java.awt.Color(0, 0, 0));
        playTitleLabel.setText("윤하 노래 모음");
        playlistDetailPanel.add(playTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 520, 50));

        playInformLabel.setFont(new java.awt.Font("AppleSDGothicNeoB00", 0, 18)); // NOI18N
        playInformLabel.setText("<html>초저녁 감성</html>");
        playInformLabel.setForeground(new Color(187,187,187));
        playInformLabel.setVerticalAlignment(JLabel.TOP);
        playlistDetailPanel.add(playInformLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 530, 90));

        playDateLabel.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 18)); // NOI18N
        playDateLabel.setText("2022-05-22");
        playDateLabel.setForeground(new Color(187,187,187));
        playlistDetailPanel.add(playDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 510, 20));

        playAuthorLabel.setFont(new java.awt.Font("AppleSDGothicNeoB00", 0, 18)); // NOI18N
        playAuthorLabel.setForeground(new java.awt.Color(87, 144, 255));
        playAuthorLabel.setText("by 랄로(Ralo)");
        playlistDetailPanel.add(playAuthorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 245, 410, 30));

        playIdLabel.setForeground(new java.awt.Color(255, 255, 255));
        playlistDetailPanel.add(playIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(748, 270, 150, -1));

        playDetailScrollPanel.setBackground(new java.awt.Color(255,255,255,0)
        );
        playDetailScrollPanel.setBorder(null);
        playDetailScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        playDetailScrollPanel.setToolTipText("");
        playDetailScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        playDetailScrollPanel.setOpaque(false);
        playDetailScrollPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                playDetailScrollPanelMouseWheelMoved(evt);
            }
        });

        playDetailTable.setBackground(new java.awt.Color(255,255,255,0));
        playDetailTable.setOpaque(false);
        playDetailTable.setFont(new java.awt.Font("AppleSDGothicNeoR00", 0, 14)); // NOI18N
        playDetailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "번호", "커버", "곡/앨범", "가수"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playDetailTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        playDetailTable.setMinimumSize(new java.awt.Dimension(10, 400));
        playDetailTable.setOpaque(false);
        playDetailTable.setRowHeight(80);
        playDetailTable.setSelectionBackground(new java.awt.Color(216, 229, 255));
        playDetailTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        playDetailTable.getTableHeader().setResizingAllowed(false);
        playDetailTable.getTableHeader().setReorderingAllowed(false);
        playDetailTable.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                playDetailTableMouseWheelMoved(evt);
            }
        });
        playDetailTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playDetailTableMouseClicked(evt);
            }
        });
        playDetailScrollPanel.setViewportView(playDetailTable);
        /* PlayDetailTable 기본 디자인 세팅 */
        JTableSetting.tableInit(playlistScrollPanel, playDetailTable);
        JTableSetting.tableHeaderInit(playDetailTable, playlistScrollPanel.getWidth(), 40);
        JTableSetting.songTableSetting(playDetailTable);

        playlistDetailPanel.add(playDetailScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 300, 900, 350));

        BackgroundPanel.add(playlistDetailPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 912, 660));
        panelList.add(playlistDetailPanel);

        chartPanel.setBackground(new java.awt.Color(255, 255, 255));
        chartPanel.setOpaque(false);
        chartPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
                "순위", "커버", "곡/앨범", "가수"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        chartTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chartTableMouseClicked(evt);
            }
        });
        chartScrollPanel.setViewportView(chartTable);
        /* ChartTable 기본 디자인 세팅 */
        JTableSetting.tableInit(chartScrollPanel, chartTable);
        JTableSetting.tableHeaderInit(chartTable, chartScrollPanel.getWidth(), 40);
        JTableSetting.songTableSetting(chartTable);

        chartPanel.add(chartScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 125, 896, 529));

        BackgroundPanel.add(chartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 60, 910, 660));
        panelList.add(chartPanel);

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

    // 릴레이리스트 목록 조회 이벤트
    private void RelaylistLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RelaylistLabelMouseClicked
        JPanelSetting.changePanel(this.panelList, this.relaylistPanel);        

        // 릴레이리스트 테이블 초기화
        DefaultTableModel model = (DefaultTableModel) relaylistTable.getModel();
        model.setRowCount(0);
        
        Object[][] data = getAllRelaylists(model); // 릴레이리스트 목록 조회 메소드
        
        // 테이블에 릴레이리스트 삽입
        JTableSetting.insertTableRow((DefaultTableModel) relaylistTable.getModel(), data);
    }//GEN-LAST:event_RelaylistLabelMouseClicked

    // 플레이리스트 조회 이벤트
    private void PlaylistLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlaylistLabelMouseClicked
        // TODO add your handling code here:
        // 화면 변경
        JPanelSetting.changePanel(this.panelList, this.playlistPanel);
        
        // 플레이리스트 목록 초기화(갱신)
        initPlaylistPanel();
    }//GEN-LAST:event_PlaylistLabelMouseClicked
    


    
    private void NotifyLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NotifyLabelMouseClicked
        // TODO add your handling code here:
        this.notifyIconLabel.setVisible(false);
        JPanelSetting.changePanel(this.panelList, this.notifyPanel);
    }//GEN-LAST:event_NotifyLabelMouseClicked

    private void playDetailScrollPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_playDetailScrollPanelMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(playlistTable, playlistScrollPanel, evt);
    }//GEN-LAST:event_playDetailScrollPanelMouseWheelMoved

    private void playDetailTableMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_playDetailTableMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(playDetailTable, playDetailScrollPanel, evt);
    }//GEN-LAST:event_playDetailTableMouseWheelMoved

    private void playDetailTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playDetailTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_playDetailTableMouseClicked

    // 플레이리스트 상세 조회
    private void playlistTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playlistTableMouseClicked
        // TODO add your handling code here:
        // 선택 된 행의 플레이리스트 ID를 가져와서 컨트롤러에게 플레이리스트 정보를 요청
        // 전달받은 플레이리스트의 정보를 playDetailPanel로 전달하여 출력해야 함
        
        // 플레이리스트 상세 조회 페이지 초기화
        initPlayDetialPanel();
        
        int row = this.playlistTable.getSelectedRow();
        if (row < 0) return;    // 빈 공간을 선택한 경우 
        
        TableModel model = this.playlistTable.getModel();
        
        Object[][] value = getPlaylist(model, row);

        JTableSetting.insertTableRow((DefaultTableModel) playDetailTable.getModel(), value);
        
        JPanelSetting.changePanel(panelList, playlistDetailPanel);
    }//GEN-LAST:event_playlistTableMouseClicked
    
  
    
    private void relayDetailTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayDetailTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_relayDetailTableMouseClicked

    private void relayDetailScrollPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_relayDetailScrollPanelMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(relayDetailTable, relayDetailScrollPanel, evt);
    }//GEN-LAST:event_relayDetailScrollPanelMouseWheelMoved

    private void playlistTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playlistTableMousePressed
        // TODO add your handling code here:
        this.playlistTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_playlistTableMousePressed

    // 릴레이리스트 상세 조회 이벤트
    private void relaylistTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relaylistTableMouseClicked
        // 릴레이리스트 상세 조회 페이지 초기화
        initRelayDetialPanel();
        
        int row = relaylistTable.getSelectedRow();
        if (row < 0) return;
        
        DefaultTableModel model = (DefaultTableModel) relaylistTable.getModel();
        
        // 릴레이리스트 아이디 가져오기
        Document doc = Jsoup.parse(model.getValueAt(row, 2).toString());
        
        Element element = doc.selectFirst("body");
        String listId = element.select("#listId").attr("value");
        
        // 1. 릴레이리스트 아이디로 릴레이리스트 가져오기
        RelaylistDto relaylist = relaylistController.getRelaylist(listId);
        
        // 2. 릴레이리스트 조회 화면 세팅
        relaylistIdLabel.setText(listId);                                                                       // 릴레이리스트 아이디 지정
        firstSongImageLabel.setIcon(ComponentSetting.imageToIcon(relaylist.getFirstSongImage(), 260, 260));     // 썸네일 지정
        relayImageLabel.setIcon(ComponentSetting.getBigBlurImage(relaylist.getFirstSongImage(), 912, 912));     // 배경 지정
        relaylistTitleLabel.setText("<html><p>" + relaylist.getTitle().replace("\n", "<br>")+ "</p></html>");   // 제목 지정
        relaylistInformLabel.setText(relaylist.getInform());                                                    // 설명 지정
        firstSongTitleLabel.setText(relaylist.getFirstSongTitle());                                             // 첫 곡 제목 지정
        firstSongSingerLabel.setText(relaylist.getFirstSongSinger());                                           // 첫 곡 가수 지정
        
        // 3. 수록곡 테이블에 노래 삽입
        ArrayList<SongDto> sideTrack = songController.getBsideTrack("relayBsideTrack", listId);
        
        Object[][] value = DataParser.songDtoToObject(sideTrack, 0);
        
        JTableSetting.insertTableRow((DefaultTableModel) relayDetailTable.getModel(), value);
        
        JPanelSetting.changePanel(panelList, relaylistDetailPanel);
        
    }//GEN-LAST:event_relaylistTableMouseClicked

    /* 프레임 로딩 이벤트 (인기차트 조회) */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

        /* 인기차트 */
        songController.updateSongChart();
        ArrayList<SongChart> chart = songController.getSongChart();
        Object[][] values = DataParser.songChartToObject(chart);

        /* 테이블에 값 추가*/
        JTableSetting.insertTableRow((DefaultTableModel) chartTable.getModel(), values);
    }//GEN-LAST:event_formWindowOpened

    /* 인기차트 테이블 마우스 클릭 이벤트 */
    private void chartTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chartTableMouseClicked
        // TODO add your handling code here:
        int row = this.chartTable.getSelectedRow();
        int column = this.chartTable.getSelectedColumn();
        TableModel model = this.chartTable.getModel();

        System.out.println(row + "행, " + column + "열 : " + model.getValueAt(row, 2) + " 선택했음");
    }//GEN-LAST:event_chartTableMouseClicked

    /* 플레이리스트 제목 생성 필드 이벤트 */
    private void createPlayTitleFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createPlayTitleFieldFocusGained
        // TODO add your handling code here:
        this.createPlayTitleLabel.setIcon(new ImageIcon("./src/resources/layout/field/focus/playTitle_field_focus.png"));
    }//GEN-LAST:event_createPlayTitleFieldFocusGained

    private void createPlayTitleFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createPlayTitleFieldFocusLost
        // TODO add your handling code here:
        this.createPlayTitleLabel.setIcon(new ImageIcon("./src/resources/layout/field/normal/playTitle_field.png"));
    }//GEN-LAST:event_createPlayTitleFieldFocusLost

    /* 플레이리스트 설명 생성 필드 이벤트 */
    private void createPlayInformTextAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createPlayInformTextAreaFocusGained
        // TODO add your handling code here:
        this.createPlayInformLabel.setIcon(new ImageIcon("./src/resources/layout/field/focus/playInform_field_focus.png"));
    }//GEN-LAST:event_createPlayInformTextAreaFocusGained

    private void createPlayInformTextAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createPlayInformTextAreaFocusLost
        // TODO add your handling code here:
        this.createPlayInformLabel.setIcon(new ImageIcon("./src/resources/layout/field/normal/playInform_field.png"));
    }//GEN-LAST:event_createPlayInformTextAreaFocusLost

    private void playBsideTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playBsideTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_playBsideTableMouseClicked

    private void playBsideScrollPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_playBsideScrollPanelMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(playBsideTable, playBsideScrollPanel, evt);
    }//GEN-LAST:event_playBsideScrollPanelMouseWheelMoved

    private void addTrackBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTrackBtnMouseEntered
        // TODO add your handling code here:
        this.addTrackBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/addTrack_btn_hover.png"));
        this.addTrackBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_addTrackBtnMouseEntered

    private void addTrackBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTrackBtnMouseExited
        // TODO add your handling code here:
        this.addTrackBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/addTrack_btn.png"));
    }//GEN-LAST:event_addTrackBtnMouseExited

    private void createPlayBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createPlayBtnMouseEntered
        // TODO add your handling code here:
        this.createPlayBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/playCreate_btn_hover.png"));
        this.createPlayBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_createPlayBtnMouseEntered

    private void createPlayBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createPlayBtnMouseExited
        // TODO add your handling code here:
        this.createPlayBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/playCreate_btn.png"));
    }//GEN-LAST:event_createPlayBtnMouseExited

    private void addPlaylistBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPlaylistBtnMouseEntered
        // TODO add your handling code here:
        this.addPlaylistBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/addTrack_btn_hover.png"));
        this.addPlaylistBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_addPlaylistBtnMouseEntered

    private void addPlaylistBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPlaylistBtnMouseExited
        // TODO add your handling code here:
        this.addPlaylistBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/addTrack_btn.png"));
    }//GEN-LAST:event_addPlaylistBtnMouseExited

    private void addPlaylistBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPlaylistBtnMouseClicked
        // TODO add your handling code here:
        // 플레이리스트 생성 페이지 초기화
        this.createPlayImgLabel.setIcon(null);
        this.createPlayTitleField.setText("");
        this.createPlayInformTextArea.setText("");
        ((DefaultTableModel) this.playBsideTable.getModel()).setRowCount(0);
        
        JPanelSetting.changePanel(panelList, createPlayPanel);
    }//GEN-LAST:event_addPlaylistBtnMouseClicked

    private void addTrackBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTrackBtnMouseClicked
        // TODO add your handling code here:
        SearchFrame search = new SearchFrame();
        
        search.setBsideTable(this.playBsideTable);
        search.setCreatePlayImgLabel(this.createPlayImgLabel);
        search.setListType(ListType.PLAYLIST);
    }//GEN-LAST:event_addTrackBtnMouseClicked

    /* 플레이리스트 생성 완료 버튼 */
    private void createPlayBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createPlayBtnMouseClicked
        createPlaylist();
    }//GEN-LAST:event_createPlayBtnMouseClicked
    

    private void playDeleteBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playDeleteBtnMouseEntered
        // TODO add your handling code here:
        this.playDeleteBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/delete_btn_hover.png"));
        this.playDeleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_playDeleteBtnMouseEntered

    private void playDeleteBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playDeleteBtnMouseExited
        // TODO add your handling code here:
        this.playDeleteBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/delete_btn.png"));
    }//GEN-LAST:event_playDeleteBtnMouseExited

    private void playEditBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playEditBtnMouseEntered
        // TODO add your handling code here:
        this.playEditBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/edit_btn_hover.png"));
        this.playEditBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_playEditBtnMouseEntered

    private void playEditBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playEditBtnMouseExited
        // TODO add your handling code here:
        this.playEditBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/edit_btn.png"));
    }//GEN-LAST:event_playEditBtnMouseExited

    private void playDeleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playDeleteBtnMouseClicked
        // TODO add your handling code here:
        boolean result = playlistController.deletePlaylist(playIdLabel.getText());
        
        if(result) JOptionPane.showMessageDialog(null, "플레이리스트가 삭제 되었습니다.");
        else JOptionPane.showMessageDialog(null, "플레이리스트 삭제에 실패 했습니다.");
    }//GEN-LAST:event_playDeleteBtnMouseClicked

    private void createRelayInformTextAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createRelayInformTextAreaFocusGained
        // TODO add your handling code here:
        this.createPlayInformLabel.setIcon(new ImageIcon("./src/resources/layout/field/focus/playInform_field_focus.png"));
    }//GEN-LAST:event_createRelayInformTextAreaFocusGained

    private void createRelayInformTextAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createRelayInformTextAreaFocusLost
        // TODO add your handling code here:
        this.createPlayInformLabel.setIcon(new ImageIcon("./src/resources/layout/field/normal/playInform_field.png"));
    }//GEN-LAST:event_createRelayInformTextAreaFocusLost

    private void createRelayTitleFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createRelayTitleFieldFocusGained
        // TODO add your handling code here:
         this.createRelayTitleLabel.setIcon(new ImageIcon("./src/resources/layout/field/focus/playTitle_field_focus.png"));
    }//GEN-LAST:event_createRelayTitleFieldFocusGained

    private void createRelayTitleFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createRelayTitleFieldFocusLost
        // TODO add your handling code here:
        this.createRelayTitleLabel.setIcon(new ImageIcon("./src/resources/layout/field/normal/playTitle_field.png"));
    }//GEN-LAST:event_createRelayTitleFieldFocusLost

    private void relayFirstTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayFirstTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_relayFirstTableMouseClicked

    private void relayFirstScrollPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_relayFirstScrollPanelMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_relayFirstScrollPanelMouseWheelMoved

    private void addSongBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSongBtnMouseClicked
        // TODO add your handling code here:
        SearchFrame search = new SearchFrame();
        
        search.setBsideTable(this.relayFirstTable);
        search.setCreatePlayImgLabel(this.createRelayImgLabel);
        search.setListType(ListType.RELAYLIST);
    }//GEN-LAST:event_addSongBtnMouseClicked

    private void addSongBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSongBtnMouseEntered
        // TODO add your handling code here:
        this.addSongBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/addTrack_btn_hover.png"));
        this.addSongBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_addSongBtnMouseEntered

    private void addSongBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSongBtnMouseExited
        // TODO add your handling code here:
        this.addSongBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/addTrack_btn.png"));
    }//GEN-LAST:event_addSongBtnMouseExited

    // 릴레이리스트 생성 버튼
    private void createRelayBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createRelayBtnMouseClicked
        createRelaylist();  //릴레이리스트 생성 메소드
    }//GEN-LAST:event_createRelayBtnMouseClicked

    private void createRelayBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createRelayBtnMouseEntered
        // TODO add your handling code here:
        this.createRelayBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/playCreate_btn_hover.png"));
        this.createRelayBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_createRelayBtnMouseEntered

    private void createRelayBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createRelayBtnMouseExited
        // TODO add your handling code here:
        this.createRelayBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/playCreate_btn.png"));
    }//GEN-LAST:event_createRelayBtnMouseExited

    private void addRelaylistBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRelaylistBtnMouseClicked
        // TODO add your handling code here:
        this.createRelayImgLabel.setIcon(null);
        this.createRelayTitleField.setText("");
        this.createRelayInformTextArea.setText("");
        ((DefaultTableModel) this.relayFirstTable.getModel()).setRowCount(0);
        
        JPanelSetting.changePanel(panelList, createRelayPanel);
    }//GEN-LAST:event_addRelaylistBtnMouseClicked

    private void addRelaylistBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRelaylistBtnMouseEntered
        // TODO add your handling code here:
        this.addRelaylistBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/addTrack_btn_hover.png"));
        this.addRelaylistBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_addRelaylistBtnMouseEntered

    private void addRelaylistBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRelaylistBtnMouseExited
        // TODO add your handling code here:
        this.addRelaylistBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/addTrack_btn.png"));
    }//GEN-LAST:event_addRelaylistBtnMouseExited

    private void FrameDragBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FrameDragBarMousePressed
        // TODO add your handling code here:
        mouseX = evt.getX();
        mouseY = evt.getY();
        
    }//GEN-LAST:event_FrameDragBarMousePressed

    private void FrameDragBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FrameDragBarMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x - mouseX, y - mouseY);
    }//GEN-LAST:event_FrameDragBarMouseDragged

    private void ExitLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitLabelMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_ExitLabelMouseClicked

    private void ExitLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitLabelMouseEntered
        // TODO add your handling code here:
        this.ExitLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_ExitLabelMouseEntered

    private void relayDetailTableMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_relayDetailTableMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(relayDetailTable, relayDetailScrollPanel, evt);
    }//GEN-LAST:event_relayDetailTableMouseWheelMoved

    private void relaylistTableMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_relaylistTableMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(relaylistTable, relaylistScrollPanel, evt);
    }//GEN-LAST:event_relaylistTableMouseWheelMoved

    private void voteLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voteLabelMouseEntered
        // TODO add your handling code here:
        this.voteLabel.setForeground(new java.awt.Color(87,144,255));
        this.voteLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_voteLabelMouseEntered

    private void voteLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voteLabelMouseExited
        // TODO add your handling code here:
        this.voteLabel.setForeground(new java.awt.Color(187,187,187));
    }//GEN-LAST:event_voteLabelMouseExited

    private void recommendLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recommendLabelMouseEntered
        // TODO add your handling code here:
        this.recommendLabel.setForeground(new java.awt.Color(87,144,255));
        this.recommendLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_recommendLabelMouseEntered

    private void recommendLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recommendLabelMouseExited
        // TODO add your handling code here:
        this.recommendLabel.setForeground(new java.awt.Color(187,187,187));
    }//GEN-LAST:event_recommendLabelMouseExited

    /* 곡 추천 버튼 클릭 */
    private void recommendLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recommendLabelMouseClicked
        // TODO add your handling code here:
        SearchFrame search = new SearchFrame();
        
        search.setBsideTable(this.relayDetailTable);
        search.setListType(ListType.VOTE);                  
        search.setRelaylistId(relaylistIdLabel.getText());
    }//GEN-LAST:event_recommendLabelMouseClicked

    private void relayVoteForwardBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVoteForwardBtnMouseEntered
        // TODO add your handling code here:
        this.relayVoteForwardBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/vote_forward_btn_hover.png"));
        this.relayVoteForwardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_relayVoteForwardBtnMouseEntered

    private void relayVoteForwardBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVoteForwardBtnMouseExited
        // TODO add your handling code here:
        this.relayVoteForwardBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/vote_forward_btn.png"));
    }//GEN-LAST:event_relayVoteForwardBtnMouseExited

    private void relayVoteBackBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVoteBackBtnMouseEntered
        // TODO add your handling code here:
        this.relayVoteBackBtn.setIcon(new ImageIcon("./src/resources/layout/button/hover/vote_back_btn_hover.png"));
        this.relayVoteBackBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_relayVoteBackBtnMouseEntered

    private void relayVoteBackBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVoteBackBtnMouseExited
        // TODO add your handling code here:
        this.relayVoteBackBtn.setIcon(new ImageIcon("./src/resources/layout/button/normal/vote_back_btn.png"));
    }//GEN-LAST:event_relayVoteBackBtnMouseExited

    private void relayVoteNotPickBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVoteNotPickBtnMouseClicked
        // TODO add your handling code here:
        this.relayVoteNotPickBtn.setVisible(false);
        this.relayVotePickBtn.setVisible(true);
        
        RelayBsideTrackDto votedSong = getCurrentVoteSong();
        
        // 투표한 곡을 저장
        votedList.add(votedSong);
        
        for (RelayBsideTrackDto song : votedList) {
            System.out.println(song);
        }
    }//GEN-LAST:event_relayVoteNotPickBtnMouseClicked

    
    /* 곡 투표 버튼 이벤트 */
    private void relayVotePickBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVotePickBtnMouseClicked
        // TODO add your handling code here:
        this.relayVoteNotPickBtn.setVisible(true);
        this.relayVotePickBtn.setVisible(false);
        
        int songId = Integer.parseInt(relayVoteSongIdLabel.getText());
        String listId = relayVoteListIdLabel.getText();
        
        RelayBsideTrackDto votedSong = RelayBsideTrackDto.builder()
                                                         .relaylistId(listId)
                                                         .songId(songId)
                                                         .build();
        
        // 투표 취소한 곡을 리스트에서 제거
        votedList.remove(votedSong);
        
        for (RelayBsideTrackDto song : votedList) {
            System.out.println(song);
        }
    }//GEN-LAST:event_relayVotePickBtnMouseClicked

    private void relayVoteNotPickBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVoteNotPickBtnMouseEntered
        // TODO add your handling code here:
        this.relayVoteNotPickBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_relayVoteNotPickBtnMouseEntered

    private void relayVotePickBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVotePickBtnMouseEntered
        // TODO add your handling code here:
        this.relayVotePickBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_relayVotePickBtnMouseEntered

    /* 곡 투표 버튼 클릭 이벤트 */
    private void voteLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voteLabelMouseClicked
        // TODO add your handling code here:
        String listId = relaylistIdLabel.getText();
        String userId = LoginUserLabel.getText();
        
        DefaultTableModel model = (DefaultTableModel) relayDetailTable.getModel();
        int row = model.getRowCount();
        
        // 1. 사용자의 voteCnt를 가져옴
        RelayUserDto relayUser = relayUserController.getRelayUser(listId, userId);
        
        // 2. 테이블의 row와 개수 비교
        // 2.1 row보다 크거나 같으면 투표 할 수 없음 (이미 모든 곡을 투표 한 경우)
        if(relayUser.getVoteCnt() >= row) {
            JOptionPane.showMessageDialog(null, "이미 모든 곡을 투표 하셨습니다.");
            return;
        }
        
        // 2-2. row보다 작으면 voteCnt 이후의 노래부터 투표 시작
        // 릴레이리스트의 수록곡을 SongDto로 전부 변환
        ArrayList<SongDto> songlist = new ArrayList<>();
        
        for (int i = relayUser.getVoteCnt(); i < row; i++) {
            songlist.add(DataParser.parseHtmlToSongDto(model.getValueAt(i, 2)));
        }
        
        // 변환한 수록곡 리스트를 저장
        this.recommendList = songlist;
        
        // 투표 화면 세팅
        SongDto song = recommendList.get(0);
        
        setRelayVotePage(song, listId);
        
        // 투표 인덱스, 투표 기록 초기화
        voteIndex = 0; 
        votedList = new ArrayList<>();
        JPanelSetting.changePanel(panelList, relayVotePanel);
    }//GEN-LAST:event_voteLabelMouseClicked


    
    private void relayVoteForwardBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVoteForwardBtnMouseClicked
        // TODO add your handling code here:
        
        String listId = relayVoteListIdLabel.getText();
        
        // 마지막 곡인지 체크
        if(voteIndex + 1 >= recommendList.size()){
            
            boolean voteResult = relayBsideTrackController.updateVoteCnt(votedList);
            
            RelayUserDto relayUser = RelayUserDto.builder()
                                                 .relaylistId(listId)
                                                 .userId(LoginUserLabel.getText())
                                                 .voteCnt(recommendList.size())
                                                 .build();
            System.out.println(relayUser);
            
            boolean userResult = relayUserController.addRelayUser(relayUser);
            
            boolean result = (voteResult) && (userResult);
            
            if(result) JOptionPane.showMessageDialog(null, "투표가 완료 되었습니다.");
            else JOptionPane.showMessageDialog(null, "투표에 실패 했습니다.");
            
            JPanelSetting.changePanel(panelList, relaylistDetailPanel);
            return;
        }
        
        // 다음 곡을 화면에 설정
        SongDto song = recommendList.get(++voteIndex);
        
        
        setRelayVotePage(song, listId);
        initVoteBtn();
    }//GEN-LAST:event_relayVoteForwardBtnMouseClicked

    private void relayVoteBackBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relayVoteBackBtnMouseClicked
        // TODO add your handling code here:
        // 마지막 곡인지 체크
        if(voteIndex - 1 < 0){
            JOptionPane.showMessageDialog(null, "첫 곡입니다.");
            return;
        }
        
        SongDto song = recommendList.get(--voteIndex);
        String listId = relayVoteListIdLabel.getText();
        
        setRelayVotePage(song, listId);
        initVoteBtn();
    }//GEN-LAST:event_relayVoteBackBtnMouseClicked

    private void playlistTableMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_playlistTableMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(playlistTable, playlistScrollPanel, evt);
    }//GEN-LAST:event_playlistTableMouseWheelMoved

    private void relaylistTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relaylistTableMouseEntered
        // TODO add your handling code here:
        this.relaylistTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_relaylistTableMouseEntered

    private void playlistTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playlistTableMouseEntered
        // TODO add your handling code here:
        this.playlistTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_playlistTableMouseEntered

    private void playlistTabLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playlistTabLabelMouseEntered
        // TODO add your handling code here:
        this.playlistTabLabel.setIcon(new ImageIcon("./src/resources/layout/field/focus/myPlaylist_clicked.png"));
        this.playlistTabLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_playlistTabLabelMouseEntered

    private void playlistTabLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playlistTabLabelMouseExited
        // TODO add your handling code here:
        this.playlistTabLabel.setIcon(new ImageIcon("./src/resources/layout/field/normal/myPlaylist.png"));
    }//GEN-LAST:event_playlistTabLabelMouseExited

    private void relaylistTabLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relaylistTabLabelMouseEntered
        // TODO add your handling code here:
        this.relaylistTabLabel.setIcon(new ImageIcon("./src/resources/layout/field/focus/myRelaylist_clicked.png"));
        this.relaylistTabLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_relaylistTabLabelMouseEntered

    private void relaylistTabLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relaylistTabLabelMouseExited
        // TODO add your handling code here:
        this.relaylistTabLabel.setIcon(new ImageIcon("./src/resources/layout/field/normal/myRelaylist.png"));
    }//GEN-LAST:event_relaylistTabLabelMouseExited

    private void myListTableMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_myListTableMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(myListTable, myListScrollPanel, evt);
    }//GEN-LAST:event_myListTableMouseWheelMoved

    private void myListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myListTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_myListTableMouseClicked

    private void myListTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myListTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_myListTableMouseEntered

    private void LoginUserLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginUserLabelMouseEntered
        // TODO add your handling code here:
        this.LoginUserLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_LoginUserLabelMouseEntered

    private void LoginUserLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginUserLabelMouseClicked
        // TODO add your handling code here:
        JPanelSetting.changePanel(panelList, profilePanel);
    }//GEN-LAST:event_LoginUserLabelMouseClicked

    private void notifyTableMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_notifyTableMouseWheelMoved
        // TODO add your handling code here:
        JTableSetting.tableScroll(notifyTable, notifyScrollPanel, evt);
    }//GEN-LAST:event_notifyTableMouseWheelMoved

    private void notifyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notifyTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_notifyTableMouseClicked

    private void notifyTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notifyTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_notifyTableMouseEntered
    
    // 투표 버튼(하트) 상태 설정 메소드
    private void initVoteBtn(){
        RelayBsideTrackDto currentSong = getCurrentVoteSong();
        
        // 현재 곡이 votedList에 존재하면 선택 표시
        if(votedList.contains(currentSong)) {
            this.relayVoteNotPickBtn.setVisible(false);
            this.relayVotePickBtn.setVisible(true);
        }
        else {
            this.relayVoteNotPickBtn.setVisible(true);
            this.relayVotePickBtn.setVisible(false);
        }
    }
    
    // 릴레이리스트 투표 화면 세팅
    private void setRelayVotePage(SongDto song, String listId){
        this.relayVotePickBtn.setVisible(false);
        this.relayVoteNotPickBtn.setVisible(true);
        
        String blurImage = song.getImage().replace("resize/1000", "resize/912").replace("images/1000", "images/912");   // 배경에 맞게 사이즈 조절
        String thumbImage = song.getImage().replace("resize/1000", "resize/350").replace("images/1000", "images/350");  // 썸네일에 맞게 사이즈 조절
        
        relayVoteSongIdLabel.setText(String.valueOf(song.getId()));                                                     // 투표할 곡의 아이디 지정
        relayVoteListIdLabel.setText(listId);                                                                           // 릴레이리스트 아이디 지정
        relayVoteBGLabel.setIcon(ComponentSetting.getBigBlurImage(blurImage, 912, 912));                                // 배경 지정
        relayVoteImageLabel.setIcon(ComponentSetting.imageToIcon(thumbImage, 350, 350));                                // 썸네일 지정
        relayVoteTitleLabel.setText(song.getTitle());                                                                   // 제목 지정
        relayVoteSingerLabel.setText(song.getSinger());                                                                 // 가수 지정
    }
    
    // 플레이리스트 상세 조회 페이지 초기화
    private void initPlayDetialPanel(){
        playImageLabel.setIcon(null);               // 썸네일 지정
        playTitleLabel.setText("");                 // 제목 지정
        playDateLabel.setText("");                  // 생성 날짜 지정
        playInformLabel.setText("");                // 설명 지정
        playAuthorLabel.setText("");                // 작성자 지정
        
        DefaultTableModel tm = (DefaultTableModel) playDetailTable.getModel();
        tm.setRowCount(0);
    }
    
    // 릴레이리스트 상세 조회 페이지 초기화
    private void initRelayDetialPanel(){
        firstSongImageLabel.setIcon(null);
        relayImageLabel.setIcon(null);
        relaylistTitleLabel.setText("");
        relaylistInformLabel.setText("");
        firstSongTitleLabel.setText("");
        firstSongSingerLabel.setText("");
        
        DefaultTableModel tm = (DefaultTableModel) relayDetailTable.getModel();
        tm.setRowCount(0);
    }

    
    

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
    
    public void setLoginUser(String userId) { this.LoginUserLabel.setText(userId); }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackgroundPanel;
    private javax.swing.JLabel ExitLabel;
    private javax.swing.JLabel FrameDragBar;
    private javax.swing.JLabel HeaderLabel;
    private javax.swing.JLabel HomeLabel;
    private javax.swing.JLabel LoginUserLabel;
    private javax.swing.JLabel NotifyLabel;
    private javax.swing.JLabel PlaylistLabel;
    private javax.swing.JLabel RelaylistLabel;
    private javax.swing.JLabel SidebarLabel;
    private javax.swing.JButton addPlaylistBtn;
    private javax.swing.JButton addRelaylistBtn;
    private javax.swing.JButton addSongBtn;
    private javax.swing.JButton addTrackBtn;
    private javax.swing.JLabel blurLabel;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JScrollPane chartScrollPanel;
    private javax.swing.JTable chartTable;
    private javax.swing.JLabel createPlayBGLabel;
    private javax.swing.JButton createPlayBtn;
    private javax.swing.JLabel createPlayImgLabel;
    private javax.swing.JLabel createPlayInformLabel;
    private javax.swing.JTextArea createPlayInformTextArea;
    private javax.swing.JPanel createPlayPanel;
    private javax.swing.JTextField createPlayTitleField;
    private javax.swing.JLabel createPlayTitleLabel;
    private javax.swing.JLabel createRelayBGLabel;
    private javax.swing.JButton createRelayBtn;
    private javax.swing.JLabel createRelayImgLabel;
    private javax.swing.JLabel createRelayInformLabel;
    private javax.swing.JTextArea createRelayInformTextArea;
    private javax.swing.JPanel createRelayPanel;
    private javax.swing.JTextField createRelayTitleField;
    private javax.swing.JLabel createRelayTitleLabel;
    private javax.swing.JLabel emptyLabel;
    private javax.swing.JLabel firstSongImageLabel;
    private javax.swing.JLabel firstSongSingerLabel;
    private javax.swing.JLabel firstSongTitleLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane myListScrollPanel;
    private javax.swing.JTable myListTable;
    private javax.swing.JLabel notifyIconLabel;
    private javax.swing.JPanel notifyPanel;
    private javax.swing.JScrollPane notifyScrollPanel;
    private javax.swing.JTable notifyTable;
    private javax.swing.JLabel playAuthorLabel;
    private javax.swing.JScrollPane playBsideScrollPanel;
    private javax.swing.JTable playBsideTable;
    private javax.swing.JLabel playDateLabel;
    private javax.swing.JButton playDeleteBtn;
    private javax.swing.JScrollPane playDetailScrollPanel;
    private javax.swing.JTable playDetailTable;
    private javax.swing.JButton playEditBtn;
    private javax.swing.JLabel playIdLabel;
    private javax.swing.JLabel playImageLabel;
    private javax.swing.JLabel playInformLabel;
    private javax.swing.JScrollPane playInformScrollPanel;
    private javax.swing.JLabel playTitleLabel;
    private javax.swing.JPanel playlistDetailPanel;
    private javax.swing.JPanel playlistPanel;
    private javax.swing.JScrollPane playlistScrollPanel;
    private javax.swing.JLabel playlistTabLabel;
    private javax.swing.JTable playlistTable;
    private javax.swing.JLabel profileBgLabel;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel recommendLabel;
    private javax.swing.JScrollPane relayDetailScrollPanel;
    private javax.swing.JTable relayDetailTable;
    private javax.swing.JScrollPane relayFirstScrollPanel;
    private javax.swing.JTable relayFirstTable;
    private javax.swing.JLabel relayImageLabel;
    private javax.swing.JScrollPane relayInformScrollPanel;
    private javax.swing.JLabel relayVoteBGLabel;
    private javax.swing.JButton relayVoteBackBtn;
    private javax.swing.JLabel relayVoteBlurLabel;
    private javax.swing.JButton relayVoteForwardBtn;
    private javax.swing.JLabel relayVoteImageLabel;
    private javax.swing.JLabel relayVoteListIdLabel;
    private javax.swing.JButton relayVoteNotPickBtn;
    private javax.swing.JPanel relayVotePanel;
    private javax.swing.JButton relayVotePickBtn;
    private javax.swing.JLabel relayVoteSingerLabel;
    private javax.swing.JLabel relayVoteSongIdLabel;
    private javax.swing.JLabel relayVoteTitleLabel;
    private javax.swing.JPanel relaylistDetailPanel;
    private javax.swing.JLabel relaylistIdLabel;
    private javax.swing.JLabel relaylistInformLabel;
    private javax.swing.JPanel relaylistPanel;
    private javax.swing.JScrollPane relaylistScrollPanel;
    private javax.swing.JLabel relaylistTabLabel;
    private javax.swing.JTable relaylistTable;
    private javax.swing.JLabel relaylistTitleLabel;
    private javax.swing.JLabel voteLabel;
    // End of variables declaration//GEN-END:variables
}
