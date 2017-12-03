package rumeng.musicplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rumeng.musicplayer.R;
import rumeng.musicplayer.bean.MusicBean;

/**
 * Created by Administrator on 2017/8/20.
 *
 *
 * 适配器加好  使用
 */

public class MyMusicAdapter extends RecyclerView.Adapter <MyMusicAdapter.MyViewHolder> {
    private List<MusicBean> musicBeenList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    public MyMusicAdapter(List<MusicBean> musicBeenList, Context context) {
        this.musicBeenList = musicBeenList;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder( LayoutInflater.from(context).inflate(R.layout.fragement_music_item,null, false));
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int  position) {
        MusicBean musicBean = musicBeenList.get(position);
        holder.name.setText(musicBean.name);
        holder.artist.setText(musicBean.artist);
        holder.time.setText((int)((Double.valueOf(musicBean.size)/1024.0/1024.0)*100)/100.0+"M");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return musicBeenList.size();
    }

  class MyViewHolder extends RecyclerView.ViewHolder {
      TextView name,artist,time;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) (itemView.findViewById(R.id.music_name));
            artist = (TextView) (itemView.findViewById(R.id.music_artist));
            time = (TextView) (itemView.findViewById(R.id.music_time));
        }
    }
    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
}

