package com.liangmayong.android_base;

import android.os.Handler;
import android.view.View;

import com.liangmayong.base.appbox.AppboxFragment;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.pullrefresh.PullRefreshLayout;
import com.liangmayong.base.widget.pullrefresh.drawables.PictureDrawable;
import com.liangmayong.base.widget.relistview.ReListView;
import com.liangmayong.skin.Skin;
import com.liangmayong.viewbinding.annotations.BindLayout;
import com.liangmayong.viewbinding.annotations.BindOnClick;
import com.liangmayong.viewbinding.annotations.BindTitle;

/**
 * Created by LiangMaYong on 2016/10/15.
 */
@BindLayout(R.layout.activity_main)
@BindTitle("Appbox")
public class MainFragment extends AppboxFragment {


    @BindOnClick(R.id.sbutton)
    private void btn() {
        goTo(MainActivity.class);
    }

    // colors
    private int[] colors = {0xff336666, 0xff663366, 0xff3399ff, 0xffff6858, 0xfffcb815};
    // index
    private int index = 0;

    public void initView(View view) {
        getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHostActivity().finish();
            }
        });
        getDefualtToolbar().rightOne().iconToLeft(Icon.icon_add).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(MainActivity.class);
            }
        });
        final PullRefreshLayout pullRefreshLayout = (PullRefreshLayout) view.findViewById(R.id.pull);
        final ReListView reListView = (ReListView) view.findViewById(R.id.relist);
        for (int i = 0; i < 50; i++) {
            reListView.getPool().add(new ViewItem(""));
        }
        reListView.getPool().notifyDataSetChanged();
        reListView.setDecorationSize(DimenUtils.dip2px(this, 2));

        PictureDrawable pictureDrawable = new PictureDrawable(pullRefreshLayout, R.mipmap.loading_bee, R.mipmap.loading_bee1, R.mipmap.loading_bee2, R.mipmap.loading_bee3, R.mipmap.loading_bee4, R.mipmap.loading_bee5, R.mipmap.loading_bee6);
        pullRefreshLayout.setRefreshDrawable(pictureDrawable);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index++;
                if (index > colors.length - 1) {
                    index = 0;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout.setRefreshing(false);
                        Skin.editor().setThemeColor(colors[index], 0xffffffff).commit();
                    }
                }, 0);
                reListView.setColumnCount(index + 1);
            }
        });
    }

}
