package com.liangmayong.base.basic.expands.crash;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.liangmayong.base.basic.expands.crash.dao.CrashDao;
import com.liangmayong.base.basic.expands.crash.model.CrashModel;
import com.liangmayong.base.basic.expands.list.FlowListFragment;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.adapter.SuperItemView;
import com.liangmayong.base.support.adapter.SuperListAdapter;
import com.liangmayong.base.support.adapter.view.DefaultSuperItemView;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.utils.DateFormatUtils;
import com.liangmayong.base.widget.iconfont.IconFont;

import java.util.List;

/**
 * Created by LiangMaYong on 2017/3/28.
 */
@BindTitle("Crash")
public class CrashFragment extends FlowListFragment {

    private SuperListAdapter superListAdapter;
    private CrashDao crashDao;

    @Override
    protected void initListViews(ListView listView) {
        crashDao = new CrashDao(getContext());
        superListAdapter = new SuperListAdapter();
        listView.setAdapter(superListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SuperItemView superItemView = superListAdapter.getItem(position);
                if (superItemView instanceof DefaultSuperItemView) {
                    CrashModel crashModel = (CrashModel) ((DefaultSuperItemView) superItemView).getData().getTag();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("crashModel", crashModel);
                    open(new CrashLogFragment(), bundle);
                }
            }
        });
        refreshList();
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.onInitDefaultToolbar(defaultToolbar);
        defaultToolbar.rightOne().icon(IconFont.base_icon_delete).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crashDao.delAll();
                refreshList();
            }
        });
    }

    private void refreshList() {
        List<CrashModel> crashModels = crashDao.getCrashList();
        superListAdapter.removeAll();
        for (int i = 0; i < crashModels.size(); i++) {
            CrashModel crashModel = crashModels.get(i);
            DefaultSuperItemView.Data data = new DefaultSuperItemView.Data(crashModel.getTitle(), DateFormatUtils.formatYearMonthDayTime(crashModel.getTime()));
            data.setTag(crashModel);
            superListAdapter.add(new DefaultSuperItemView(data));
        }
        superListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            refreshList();
        }
    }
}
