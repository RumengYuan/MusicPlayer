package rumeng.musicplayer.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/22.
 */

   public   class MusicBean implements Serializable {
        public String name;
        public String artist;
        public String size;
        public String duration;
        public String path;



        public  MusicBean(String name, String artist, String size,String duration,String path) {
            this.name = name;
            this.artist = artist;
            this.size = size;
            this.duration=duration;
            this.path=path;
        }
    }

