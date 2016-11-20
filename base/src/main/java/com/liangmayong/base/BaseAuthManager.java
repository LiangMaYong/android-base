package com.liangmayong.base;

import com.liangmayong.base.widget.database.DataPreferences;

/**
 * Created by liangmayong on 2016/11/20.
 */

public class BaseAuthManager {
    private static BaseAuthManager ourInstance = new BaseAuthManager();

    public static BaseAuthManager getInstance() {
        return ourInstance;
    }

    private BaseAuthManager() {
    }

    public boolean isAuth() {
        if (DataPreferences.getPreferences("base_auth").getBoolean("base_is_auth", false)) {
            return true;
        }
        return false;
    }

    public void setAuthState(boolean isAuth) {
        DataPreferences.getPreferences("base_auth").setBoolean("base_is_auth", isAuth);
    }
}
