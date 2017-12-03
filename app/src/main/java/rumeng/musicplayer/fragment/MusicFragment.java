package rumeng.musicplayer.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rumeng.musicplayer.PlayActivity;
import rumeng.musicplayer.R;
import rumeng.musicplayer.adapter.MyMusicAdapter;
import rumeng.musicplayer.bean.MusicBean;
import rumeng.musicplayer.services.PlayManage;


/**
 * Created by Administrator on 2017/8/6.
 */

public class MusicFragment extends BasedFragment {
    private List<MusicBean> musicBeen=new ArrayList<>();
    private RecyclerView recyclerView;
    private String path;
    private String name;
    private String size;
    private String artist;
    private String duration;
    private static final int MY_PERMISSIONS_REQUESTE = 1;
    private static final String TAG = "MusicFragment";
    private PlayManage playManage;

    protected View initData(View view) {

        return view;

    }
    public void permission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getActivity(),"正在获取权限 ",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUESTE);
        }else{
getMusicList();
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (MY_PERMISSIONS_REQUESTE) {
            case MY_PERMISSIONS_REQUESTE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMusicList();
                    Toast.makeText(getActivity(),"Permission ",Toast.LENGTH_LONG).show();

                    // permission was already

                } else {
                    Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;}}
    @Override
    protected int getLayoutId() {

        return R.layout.fragement_music;
    }

    @Override
    protected View initListener(View view) {
        return view;
    }



    private List<MusicBean> getMusicList() {
        final List<MusicBean> list = new ArrayList<>();
        /**
         * 拿到内容解析者
         */
        ContentResolver resolver = getActivity().getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            /**
             * 从系统数据库扫描筛选时长大于30s，大小大于1.5m添加至musicBean list
             */
            if (Integer.valueOf(duration)>30000){

                if (Integer.valueOf(size)>1572864)
            {

                list.add(new MusicBean(name, artist, size, duration, path));
            }}
        }
        /**
         * duration 需要转换一下
         */
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyMusicAdapter myMusicAdapter = new MyMusicAdapter(list, getActivity());
        myMusicAdapter.setOnItemClickListener(new MyMusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),"点了"+position,Toast.LENGTH_LONG).show();
                PlayActivity.startAction(getActivity(), (ArrayList<MusicBean>) list, position);

            }
        });
        recyclerView.setAdapter(myMusicAdapter);
        return list;

    }
    @Override
    protected View initView(View view) {

        recyclerView = view.findViewById(R.id.music_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyMusicAdapter(musicBeen,getContext()));
        permission();
        return view;
    }}