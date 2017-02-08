package com.liangmayong.base.support.photo;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by LiangMaYong on 2017/2/8.
 */
public class Photo {

    //ourInstance
    private static volatile Photo ourInstance;

    /**
     * getInstance
     *
     * @return PhotoUtils
     */
    public static Photo getInstance() {
        if (ourInstance == null) {
            synchronized (Photo.class) {
                ourInstance = new Photo();
            }
        }
        return ourInstance;
    }

    private Photo() {
    }

    /**
     * startTake
     *
     * @param activity activity
     * @param id       id
     * @param crop     crop
     */
    public void startTake(Activity activity, int id, boolean crop) {
        if (crop) {
            activity.startActivityForResult(PhotoIntent.getInstance().getTakeIntent(activity, id + 0xAA), id + 0xAA);
        } else {
            activity.startActivityForResult(PhotoIntent.getInstance().getTakeIntent(activity, id), id);
        }
    }

    /**
     * startTake
     *
     * @param fragment fragment
     * @param id       id
     * @param crop     crop
     */
    public void startTake(Fragment fragment, int id, boolean crop) {
        if (crop) {
            fragment.startActivityForResult(PhotoIntent.getInstance().getTakeIntent(fragment.getActivity(), id + 0xAA), id + 0xAA);
        } else {
            fragment.startActivityForResult(PhotoIntent.getInstance().getTakeIntent(fragment.getActivity(), id), id);
        }
    }

    /**
     * startSelect
     *
     * @param activity activity
     * @param id       id
     */
    public void startSelect(Activity activity, int id, boolean crop) {
        if (crop) {
            activity.startActivityForResult(PhotoIntent.getInstance().getSelectIntent(), id + 0xFF);
        } else {
            activity.startActivityForResult(PhotoIntent.getInstance().getSelectIntent(), id);
        }
    }

    /**
     * startSelect
     *
     * @param fragment fragment
     * @param id       id
     */
    public void startSelect(Fragment fragment, int id, boolean crop) {
        if (crop) {
            fragment.startActivityForResult(PhotoIntent.getInstance().getSelectIntent(), id + 0xFF);
        } else {
            fragment.startActivityForResult(PhotoIntent.getInstance().getSelectIntent(), id);
        }
    }


    /**
     * handleResult
     *
     * @param id          id
     * @param width       width
     * @param height      height
     * @param activity    activity
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     * @param listener    listener
     */
    public void handleResult(final int id, int width, int height, Activity activity, int requestCode, int resultCode,
                             Intent data, PhotoResult.Listener listener) {
        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == id + 0xAA) {
                activity.startActivityForResult(PhotoIntent.getInstance().getCropIntent(activity, PhotoIntent.getInstance().getUri(activity, id + 0xAA), id, width, height), id);
            } else if (requestCode == id + 0xFF) {
                activity.startActivityForResult(PhotoIntent.getInstance().getCropIntent(activity, data.getData(), id, width, height), id);
            } else if (requestCode == id) {
                if (listener != null) {
                    listener.onResult(new PhotoResult(activity, id, width, height, data != null ? data.getData() : null));
                }
            }
        }
    }

    /**
     * handleResult
     *
     * @param id          id
     * @param width       width
     * @param height      height
     * @param fragment    activity
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     * @param listener    listener
     */
    public void handleResult(final int id, int width, int height, Fragment fragment, int requestCode, int resultCode,
                             Intent data, PhotoResult.Listener listener) {
        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == id + 0xAA) {
                fragment.startActivityForResult(PhotoIntent.getInstance().getCropIntent(fragment.getActivity(), PhotoIntent.getInstance().getUri(fragment.getActivity(), id + 0xAA), id, width, height), id);
            } else if (requestCode == id + 0xFF) {
                fragment.startActivityForResult(PhotoIntent.getInstance().getCropIntent(fragment.getActivity(), data.getData(), id, width, height), id);
            } else if (requestCode == id) {
                if (listener != null) {
                    listener.onResult(new PhotoResult(fragment.getActivity(), id, width, height, data != null ? data.getData() : null));
                }
            }
        }
    }
}
