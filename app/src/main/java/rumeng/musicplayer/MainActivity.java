package rumeng.musicplayer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rumeng.musicplayer.adapter.MyContentAdapter;
import rumeng.musicplayer.fragment.BasedFragment;
import rumeng.musicplayer.fragment.MineFragment;
import rumeng.musicplayer.fragment.MusicFragment;
import rumeng.musicplayer.fragment.VideoFragment;

/**
 * Created by Administrator on 2017/8/13.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public List<BasedFragment> fragments;
    private ImageView ivMine;
    private ImageView ivVideo;
    private ImageView ivMusic;
    private ViewPager vpContent;
    private TextView tvMine;
    private TextView tvMusic;
    private TextView tvVideo;
    private LinearLayout layoutMine;
    private LinearLayout layoutMusic;
    private LinearLayout layoutVideo;
    private int position=0;
    private LinearLayout layout;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        layoutMine.setOnClickListener(this);
        layoutMusic.setOnClickListener(this);
        layoutVideo.setOnClickListener(this);
        FragmentManager manager=getSupportFragmentManager();
        initData();

        vpContent.setAdapter(new MyContentAdapter(manager,fragments));
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 根据滑动事件改变选中状态
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        musicSelected();

                        break;
                    case 1:
                        videoSelected();
                        break;
                    case 2:
                        mineSelected();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });}



    private void mineSelected() {
        ivMusic.setSelected(false);
        ivVideo.setSelected(false);
        ivMine.setSelected(true);
        tvMine.setSelected(true);
        tvVideo.setSelected(false);
        tvMusic.setSelected(false );
        title.setText("mine");
        layout.setBackgroundResource(R.drawable.mine);


    }

    private void videoSelected() {
        ivMusic.setSelected(false);
        ivVideo.setSelected(true);
        ivMine.setSelected(false);
        tvMusic.setSelected(false);
        tvVideo.setSelected(true);
        tvMine.setSelected(false);
        title.setText("video");
        layout.setBackgroundResource(R.drawable.flying);

    }

    private void musicSelected() {
        ivMusic.setSelected(true);
        ivVideo.setSelected(false);
        ivMine.setSelected(false);
        tvMine.setSelected(false);
        tvMusic.setSelected(true);
        tvVideo.setSelected(false);
        title.setText("music");
        layout.setBackgroundResource(R.drawable.flower);

    }

    private void initView() {
        ivMine = (ImageView) findViewById(R.id.iv_mine);
        ivVideo = (ImageView) findViewById(R.id.iv_video);
        ivMusic = (ImageView) findViewById(R.id.iv_music);
        tvMine = (TextView) findViewById(R.id.tv_mine);
        tvMusic = (TextView) findViewById(R.id.tv_music);
        tvVideo = (TextView) findViewById(R.id.tv_video);
        vpContent = (ViewPager) findViewById(R.id.vp_content);
        layoutMine = (LinearLayout) findViewById(R.id.ll_mine);
        layoutMusic = (LinearLayout) findViewById(R.id.ll_music);
        layoutVideo = (LinearLayout) findViewById(R.id.ll_video);
        layout = (LinearLayout) findViewById(R.id.mainActivityLinearLayout);
        title = (TextView) findViewById(R.id.title);
    }

    private  void  initData() {
        /**
         * 初始状态
         */
        fragments=new ArrayList<>();
        fragments.add(new MusicFragment());
        fragments.add(new VideoFragment());
        fragments.add(new MineFragment());
        ivMusic.setSelected(true);
        /**
         * 图标置蓝
         */
        tvMusic.setSelected(true);
        /**
         * 文字置蓝
         */

    }

    /**
     * 根据点击选择状态更换当前fragment，设置图标和文字状态显示
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_music:
                if (position==0){
                    return;
                }
                musicSelected();
                vpContent.setCurrentItem(0);
                position=0;
                break;
            case R.id.ll_video:
                if (position==1) {
                    return;
                }
                videoSelected();
                vpContent.setCurrentItem(1);
                position=1;
                break;
            case R.id.ll_mine:
                if (position==2) {
                    return;
                }
                mineSelected();
                vpContent.setCurrentItem(2);
                position=2;
                break;
        }

    }
}
