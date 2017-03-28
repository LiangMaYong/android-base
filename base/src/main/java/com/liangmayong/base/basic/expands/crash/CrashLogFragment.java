package com.liangmayong.base.basic.expands.crash;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.expands.crash.dao.CrashDao;
import com.liangmayong.base.basic.expands.crash.model.CrashModel;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.widget.iconfont.IconFont;

/**
 * Created by LiangMaYong on 2017/3/28.
 */
@BindTitle("Crash Log")
public class CrashLogFragment extends FlowBaseFragment {

    private CrashModel crashModel = null;
    private CrashDao crashDao = null;

    @Override
    protected void initViews(View containerView) {
        crashModel = getArguments().getParcelable("crashModel");
        if (crashModel == null) {
            closeSelf();
        }
        crashDao = new CrashDao(getContext());
        ViewHolder viewHolder = new ViewHolder(containerView);
        SpannableStringBuilder styled = new SpannableStringBuilder(crashModel.getLog());
        int i = crashModel.getLog().indexOf(crashModel.getTitle());
        int j = 0;
        if (i >= 0) {
            j = i + crashModel.getTitle().length();
        }
        styled.setSpan(new ForegroundColorSpan(Color.RED), i, j, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.base_default_crash.setText(styled);
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.onInitDefaultToolbar(defaultToolbar);
        defaultToolbar.rightOne().icon(IconFont.base_icon_delete).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crashDao.delCrash(crashModel);
                closeSelf();
            }
        });
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_crash;
    }

    public class ViewHolder {
        public View rootView;
        public TextView base_default_crash;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.base_default_crash = (TextView) rootView.findViewById(R.id.base_default_crash);
        }
    }
}
