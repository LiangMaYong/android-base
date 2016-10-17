package com.liangmayong.android_base;

import android.os.Handler;
import android.view.View;

import com.liangmayong.base.fragments.WebFragment;
import com.liangmayong.base.sub.BaseSubActivity;
import com.liangmayong.base.sub.BaseSubFragment;
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
 * Created by LiangMaYong on 2016/10/17.
 */
@BindLayout(R.layout.activity_main)
@BindTitle("TestSub")
public class SubFrag extends BaseSubFragment {

    // colors
    private int[] colors = {0xff336666, 0xff663366, 0xff3399ff, 0xffff6858, 0xfffcb815};
    // index
    private int index = 0;

    @Override
    protected void initSubView(View rootView) {
        getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final PullRefreshLayout pullRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.pull);
        final ReListView reListView = (ReListView) rootView.findViewById(R.id.relist);
        for (int i = 0; i < 50; i++) {
            reListView.getPool().add(new ViewItem(""));
        }
        reListView.getPool().notifyDataSetChanged();
        reListView.setDecorationSize(DimenUtils.dip2px(getActivity(), 2));

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

    @BindOnClick(R.id.sbutton)
    private void btn() {
        goTo("百度一下", "http://www.baidu.com");
        // ((BaseSubActivity) getActivity()).getSubManager().addFragment(new WebFragment("百度一下", "http://www.baidu.com"));
    }

    @Override
    protected boolean onBackPressed() {
        showToast("onBackPressed");
        return true;
    }
}
