package com.liangmayong.android_base.presenters;


import com.liangmayong.base.binding.mvp.Presenter;

/**
 * Created by LiangMaYong on 2016/12/30.
 */
public class DemoPer extends Presenter<DemoPer.IView> {

    public interface IView {
        //efine interface
        void toast();
    }

    // TODO:Do something

    public void toast() {
        getViewState().toast();
    }

}