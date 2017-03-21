package com.liangmayong.android_base;

import android.content.ContentValues;

import com.liangmayong.android_base.db.TestDB;
import com.liangmayong.android_base.demo.DemoListFrag;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindTitle;

/**
 * Created by LiangMaYong on 2016/12/26.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("Test")
public class TestActivity extends FlowBaseActivity {

    @Override
    protected FlowBaseFragment getFirstFragment() {
        TestDB testDB = new TestDB(this);
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", "ssssssssssssssssssssssssssss");
        contentValues.put("age", 1);
        contentValues.put("name", "2asdfa");
        long id = testDB.insertData(contentValues);
        showToast(id + "");
        return new DemoListFrag();
    }

}
