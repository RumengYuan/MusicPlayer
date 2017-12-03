package rumeng.musicplayer.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;

/**
 * Created by Administrator on 2017/9/3.
 */

public class PlayManage extends Service{
    public  PlayController playController;
    public  static final  int IS_PLAYING=0;
    public static final int IS_PAUSE=1;
    public  static final int IS_STOP=2;
    public int state=2;
    private static final String TAG = "PlayManage";
    public  int getState(){
        return state;
    }

    public int getCurrentProgress(){
        if (player==null){
            return 0;
        }
        return player.getCurrentPosition();
    }
    private MediaPlayer player;
    public void setPlayController(PlayController playController){
        this.playController = playController;
    }


    public void play(String path) {
        if (player!=null){
            stopPlayer();
        }
        player=new MediaPlayer();
        try {
            player.setDataSource(this, Uri.parse(path));
            player.prepare();

            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
        playController.nextMusic();
                }
            });
            state=IS_PLAYING;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void  pause(){

            player.pause();
        player.seekTo(player.getCurrentPosition());

            state=IS_PAUSE;
        }

    public void stopPlayer(){

        player.stop();


    }
    public void  seek2(SeekBar seekBar,int rate){
        if (player!=null){
            player.seekTo(rate);
        }

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public void reStart() {
        player.start();

        state=IS_PLAYING;
    }
    public void playComplete(){
	    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
		    @Override
		    public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onCompletion: ");
                playController.nextMusic();
			
		    }
	    });
	    
    }

    public class MyBinder extends Binder{
        public PlayManage getService(){

            return PlayManage.this;
        }}

    public interface  PlayController{
        void  playMusic();
        void  preMusic();
        void  pauseMusic();
        void  nextMusic();
    }

}
