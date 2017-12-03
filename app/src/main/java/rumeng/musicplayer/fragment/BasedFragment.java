package rumeng.musicplayer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/6.
 */

public abstract class BasedFragment extends Fragment {
    private int layoutId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        initData(view);
        initListener(view);


        return view;
    }

    protected abstract int getLayoutId();

    protected abstract View initListener(View view);

    protected abstract View initData(View view);

    protected abstract View initView(View view);



    }
