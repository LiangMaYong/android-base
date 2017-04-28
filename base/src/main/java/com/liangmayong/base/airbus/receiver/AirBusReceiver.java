package com.liangmayong.base.airbus.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.liangmayong.base.airbus.converter.AirBusConverter;
import com.liangmayong.base.airbus.listener.AirBusListener;

/**
 * Created by LiangMaYong on 2017/4/28.
 */
public class AirBusReceiver extends BroadcastReceiver {

    private String airName = "";
    private AirBusListener airBusListener = null;

    public AirBusReceiver(String airName, AirBusListener airBusListener) {
        this.airName = airName;
        this.airBusListener = airBusListener;
    }

    private String getAirName() {
        return airName;
    }

    public AirBusListener getAirBusListener() {
        return airBusListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(getAirName())) {
            if (getAirBusListener() != null) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object event = AirBusConverter.parserEvent(bundle);
                    if (event != null) {
                        getAirBusListener().onAirBus(event);
                    }
                }
            }
        }
    }

}
