package com.liangmayong.base.basic.expands.crash.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LiangMaYong on 2017/3/28.
 */
public class CrashModel implements Parcelable {

    private long id = 0;
    private String title = "";
    private String log = "";
    private long time = 0;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.log);
        dest.writeLong(this.time);
    }

    public CrashModel() {
    }

    protected CrashModel(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.log = in.readString();
        this.time = in.readLong();
    }

    public static final Creator<CrashModel> CREATOR = new Creator<CrashModel>() {
        @Override
        public CrashModel createFromParcel(Parcel source) {
            return new CrashModel(source);
        }

        @Override
        public CrashModel[] newArray(int size) {
            return new CrashModel[size];
        }
    };

    @Override
    public String toString() {
        return "CrashModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", log='" + log + '\'' +
                ", time=" + time +
                '}';
    }
}
