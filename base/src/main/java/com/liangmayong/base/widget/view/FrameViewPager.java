package com.liangmayong.base.widget.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.liangmayong.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * FrameViewPager
 *
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("Recycle")
public class FrameViewPager extends FrameLayout {
    /**
     * The enabled.
     */
    private boolean enabled;

    /**
     * @param event the event
     * @return true, if successful
     * @see ViewPager#onTouchEvent(MotionEvent)
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    /**
     * @param event the event
     * @return true, if successful
     * @version v1.0
     * @author: amsoft.cn @date：2013-6-17 上午9:04:50
     * @see ViewPager#onInterceptTouchEvent(MotionEvent)
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    /**
     * Sets the paging enabled.
     *
     * @param enabled the new paging enabled
     */
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private List<Fragment> fragments;
    private ViewPager viewPager;
    private FragmentManager fm;

    public FrameViewPager(Context context) {
        super(context);
        this.enabled = true;
    }

    public FrameViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    public FrameViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.enabled = true;
    }

    private ArrayList<View> views = new ArrayList<View>();

    public ArrayList<View> getViews() {
        return views;
    }

    public void begin(List<Integer> layouts) {
        LayoutInflater li = LayoutInflater.from(getContext());
        FrameLayout frameLayout = (FrameLayout) li.inflate(R.layout.base_default_view_pager, this);
        viewPager = (ViewPager) frameLayout.findViewById(R.id.base_default_widget_viewpager);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < layouts.size(); i++) {
            try {
                View view = inflater.inflate(layouts.get(i), null);
                views.add(view);
            } catch (Exception e) {
            }
        }
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                _selected(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        PagerAdapter mPagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }
        };
        viewPager.setAdapter(mPagerAdapter);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void begin(FragmentActivity activity, List<Fragment> fragments, int index) {
        this.fragments = fragments;
        fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        LayoutInflater li = LayoutInflater.from(getContext());
        FrameLayout frameLayout = (FrameLayout) li.inflate(R.layout.base_default_view_pager, this);
        viewPager = (ViewPager) frameLayout.findViewById(R.id.base_default_widget_viewpager);
        viewPager.setOffscreenPageLimit(fragments.size());
        SectionsPagerAdapter spa = new SectionsPagerAdapter(fm);
        viewPager.setAdapter(spa);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                _selected(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        _selected(index);
        viewPager.setCurrentItem(index, false);
        if (Build.VERSION.SDK_INT >= 9) {
            viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    public interface OnPagerSelectListener {
        void selected(int index);
    }

    private OnPagerSelectListener pagerSelectListener;

    public void setOnPagerSelectListener(OnPagerSelectListener pagerSelectListener) {
        this.pagerSelectListener = pagerSelectListener;
    }

    public void selected(int index) {
        viewPager.setCurrentItem(index, false);
    }

    private void _selected(int index) {
        if (pagerSelectListener != null) {
            pagerSelectListener.selected(index);
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
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
}
