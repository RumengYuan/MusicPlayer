package rumeng.musicplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import rumeng.musicplayer.fragment.BasedFragment;

/**
 * Created by Administrator on 2017/8/13.
 */

public class MyContentAdapter extends FragmentPagerAdapter {
    private List<BasedFragment> fragments;
    public MyContentAdapter(FragmentManager fm,List<BasedFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }
}
