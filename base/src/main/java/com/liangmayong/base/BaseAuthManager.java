package com.liangmayong.base;

import com.liangmayong.base.widget.database.DataPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangmayong on 2016/11/20.
 */
public class BaseAuthManager {

    private final String DATA_PREFERENCES_NAME = "base_auth";
    private final String IS_LOGIN_EXTRA = "base_is_auth";

    private static BaseAuthManager ourInstance = new BaseAuthManager();

    public static BaseAuthManager getInstance() {
        return ourInstance;
    }

    private BaseAuthManager() {
    }

    public interface OnAuthStateChangeListener {
        void onAuthStateChange(boolean isAuth);
    }

    private final List<OnAuthStateChangeListener> listeners = new ArrayList<OnAuthStateChangeListener>();

    public void addAuthStateChangeListener(OnAuthStateChangeListener authStateChangeListener) {
        if (authStateChangeListener != null && !listeners.contains(authStateChangeListener)) {
            listeners.add(authStateChangeListener);
            authStateChangeListener.onAuthStateChange(isAuth());
        }
    }

    public void removeAuthStateChangeListener(OnAuthStateChangeListener authStateChangeListener) {
        if (authStateChangeListener != null && listeners.contains(authStateChangeListener)) {
            listeners.remove(authStateChangeListener);
        }
    }

    public boolean isAuth() {
        if (getAuthPreferences().getBoolean(IS_LOGIN_EXTRA, false)) {
            return true;
        }
        return false;
    }

    public DataPreferences getAuthPreferences() {
        return DataPreferences.getPreferences(DATA_PREFERENCES_NAME);
    }

    public BaseAuthManager setAuthState(boolean isAuth) {
        getAuthPreferences().setBoolean(IS_LOGIN_EXTRA, isAuth);
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onAuthStateChange(isAuth);
        }
        return this;
    }

}
