/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Entity.Playlist.Playlist;
import java.sql.Date;

/**
 *
 * @author Donghyeon <20183188>
 */
public class Relaylist2 {

    public String id;       // PK
    public String title;
    public String author;   // FK >- User.Id
    public String inform;
    public int likes;
    public Date createTime;
    public String firstSongTitle;
    public String firstSongSinger;
    public String firstSongImage;

    public Relaylist2() {
    }

    public Relaylist2(String id, String title, String author, String inform, int likes, Date createTime, String firstSongTitle, String firstSongSinger, String firstSongImage) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.inform = inform;
        this.likes = likes;
        this.createTime = createTime;
        this.firstSongTitle = firstSongTitle;
        this.firstSongSinger = firstSongSinger;
        this.firstSongImage = firstSongImage;
    }
    
    public static class RelaylistBuilder2 {
        private String id;       // PK
        private String title;
        private String author;   // FK >- User.Id
        private String inform;
        private int likes;
        private Date createTime;
        private String firstSongTitle;
        private String firstSongSinger;
        private String firstSongImage;

        public RelaylistBuilder2 id(String id) {
            this.id = id;
            return this;
        }

        public RelaylistBuilder2 title(String title) {
            this.title = title;
            return this;
        }

        public RelaylistBuilder2 author(String author) {
            this.author = author;
            return this;
        }

        public RelaylistBuilder2 inform(String inform) {
            this.inform = inform;
            return this;
        }

        public RelaylistBuilder2 likes(int likes) {
            this.likes = likes;
            return this;
        }

        public RelaylistBuilder2 createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public RelaylistBuilder2 firstSongTitle(String firstSongTitle) {
            this.firstSongTitle = firstSongTitle;
            return this;
        }

        public RelaylistBuilder2 firstSongSinger(String firstSongSinger) {
            this.firstSongSinger = firstSongSinger;
            return this;
        }

        public RelaylistBuilder2 firstSongImage(String firstSongImage) {
            this.firstSongImage = firstSongImage;
            return this;
        }

        public Relaylist2 build() {
            return new Relaylist2(this);
        }

    }

    public Relaylist2(RelaylistBuilder2 relaylistBuilder2) {
        this.id = relaylistBuilder2.id;
        this.title = relaylistBuilder2.title;
        this.author = relaylistBuilder2.author;
        this.inform = relaylistBuilder2.inform;
        this.likes = relaylistBuilder2.likes;
        this.createTime = relaylistBuilder2.createTime;
        this.firstSongTitle = relaylistBuilder2.firstSongTitle;
        this.firstSongSinger = relaylistBuilder2.firstSongSinger;
        this.firstSongImage = relaylistBuilder2.firstSongImage;
    }

    @Override
    public String toString() {
        return "Relaylist2{" + "id=" + id + ", title=" + title + ", author=" + author + ", inform=" + inform + ", likes=" + likes + ", createTime=" + createTime + ", firstSongTitle=" + firstSongTitle + ", firstSongSinger=" + firstSongSinger + ", firstSongImage=" + firstSongImage + '}';
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getInform() {
        return inform;
    }

    public int getLikes() {
        return likes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getFirstSongTitle() {
        return firstSongTitle;
    }

    public String getFirstSongSinger() {
        return firstSongSinger;
    }

    public String getFirstSongImage() {
        return firstSongImage;
    }

    public static Playlist toEntity(/* RelaylistDto 필요 */) {
        /* RelaylistDto 선언 후 구현 */
        return null;
    }
}
