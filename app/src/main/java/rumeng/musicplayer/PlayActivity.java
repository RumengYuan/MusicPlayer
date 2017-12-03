package rumeng.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import rumeng.musicplayer.bean.MusicBean;
import rumeng.musicplayer.services.PlayManage;

/**
 * Created by Administrator on 2017/9/3.
 */

public class PlayActivity extends AppCompatActivity implements PlayManage.PlayController,View.OnClickListener,SeekBar.OnSeekBarChangeListener {
    private static final String POSITION = "position";
    private static final String LIST = "list";
    private static PlayManage playManage;
    private static final String TAG = "PlayActivity";


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            PlayManage.MyBinder myBinder = (PlayManage.MyBinder) service;
            playManage = myBinder.getService();
            playManage.setPlayController(PlayActivity.this);
            Log.d(TAG, "onServiceConnected: success");
            playMusic();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myHandler.removeCallbacksAndMessages(null);

        }
    };

    private SeekBar seekBar;
    private ImageView preSong;
    private ImageView nextSong;
    private ImageView playSong;


    private ArrayList<MusicBean> list;
    private int position;
    private TextView songName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        /**
         * 进入界面判断是否存在music.xml文件，存在直接拿到这个文件关联的对象，假如说不存在 创建这个文件并拿到相应的对象。

        sp = getSharedPreferences("music",MODE_PRIVATE);
        int state=sp.getInt("state",2);
         */
        if (playManage!=null && playManage.getState()==0){
            playManage.stopPlayer();
        }

        playSong.setOnClickListener(this);
        preSong.setOnClickListener(this);
        nextSong.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        Intent intent = getIntent();
        position = intent.getIntExtra(POSITION, position);
        list = (ArrayList<MusicBean>) intent.getSerializableExtra(LIST);
            bindService(new Intent(this, PlayManage.class), serviceConnection, BIND_AUTO_CREATE);
    }


    /**
     * 跳转逻辑发送数据
     *
     * @param context
     * @param list
     * @param position
     */
    public static void startAction(Context context, ArrayList<MusicBean> list, int position) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(POSITION, position);
        intent.putExtra(LIST, list);
        context.startActivity(intent);
    }
    @Override
    public void playMusic() {
        Log.d(TAG, "playMusic: ");
        songName.setText(list.get(position).name);
        switch (playManage.getState()) {
            case PlayManage.IS_PAUSE:
                /**
                 * 更新相应的ＵＩ，调用reStart()
                 */
	            sendMessage();
                playManage.reStart();
                playSong.setSelected(true);
                break;
            case PlayManage.IS_PLAYING:
                /**
                 * 更新相应的ＵＩ,调用pause();
                 */
                myHandler.removeCallbacksAndMessages(null);
                playManage.pause();
                playSong.setSelected(false);

                break;
            case PlayManage.IS_STOP:
                Log.d(TAG, "playMusic:stop-播放逻辑");
                /**
                 * 更新相应的ＵＩ，调用服务的play方法
                 */
                //更新SeekBar
                seekBar.setProgress(0);
                seekBar.setMax(Integer.valueOf(list.get(position).duration));
                Message message1 = new Message();
                message1.what = 0;
                message1.obj = playManage.getCurrentProgress();
                myHandler.sendMessageDelayed(message1, 500);
                playManage.play(list.get(position).path);
                playSong.setSelected(true);
                break;
        }
    }
	
	private void sendMessage() {
		Message message = new Message();
		message.what = 0;
		message.obj = playManage.getCurrentProgress();
		myHandler.sendMessageDelayed(message, 500);
	}
	
	private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = (int) msg.obj;
            switch (msg.what) {
                case 0:
                    seekBar.setProgress(progress);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = playManage.getCurrentProgress();
                    myHandler.sendMessageDelayed(message, 500);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
      //  sp.edit().putInt("state",playManage.getState()).commit();
        super.onDestroy();
    }

    @Override
    public void preMusic() {
        Log.d(TAG, "preMusic: ");
       playManage.stopPlayer();
        myHandler.removeCallbacksAndMessages(null);
        seekBar.setProgress(0);
        seekBar.setMax(Integer.valueOf(list.get(position).duration));
        position--;
        if (position < 0) {
            position = 0;
        }
       playManage.play(list.get(position).path);
        Message message = new Message();
        message.what = 0;
        message.obj = playManage.getCurrentProgress();
        myHandler.sendMessageDelayed(message, 500);
        songName.setText(list.get(position).name);
    }

    @Override
    public void pauseMusic() {
        Log.d(TAG, "pauseMusic: ");
        if (playManage.getState() == 0) {
            playManage.pause();
            playSong.setSelected(false);
        } else {
            playMusic();
            playSong.setSelected(true);
        }


    }

    @Override
    public void nextMusic() {
        Log.d(TAG, "nextMusic: ");
        playManage.stopPlayer();
        Log.d(TAG, "nextMusic: 222");
        myHandler.removeCallbacksAndMessages(null);
        Log.d(TAG, "nextMusic: ");
        seekBar.setProgress(0);
        seekBar.setMax(Integer.valueOf(list.get(position).duration));
        position++;
        if (position > list.size() - 1) {
            position = list.size() - 1;
        }
        playManage.play(list.get(position).path);

        Message message = new Message();
        message.what = 0;
        message.obj = playManage.getCurrentProgress();
        myHandler.sendMessageDelayed(message, 500);
        songName.setText(list.get(position).name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * 点击切换按钮
             */
            case R.id.play:
                pauseMusic();
                break;
            case R.id.pre:
                preMusic();
                break;
            case R.id.next:
                nextMusic();
                break;
        }
    }
    
    public  void  initView(){
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        preSong = (ImageView) findViewById(R.id.pre);
        nextSong = (ImageView) findViewById(R.id.next);
        playSong = (ImageView) findViewById(R.id.play);
        songName = (TextView) findViewById(R.id.playMusicDisplayText);


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b){
            myHandler.removeCallbacksAndMessages(null);
            playManage.seek2(seekBar,i);
        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Message message = new Message();
        message.what = 0;
        message.obj = playManage.getCurrentProgress();
        myHandler.sendMessage(message);
    }
}
