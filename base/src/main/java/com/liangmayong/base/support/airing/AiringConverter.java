package com.liangmayong.base.support.airing;

import android.os.Bundle;

/**
 * Created by LiangMaYong on 2017/1/16.
 */
public interface AiringConverter {

    String AIRING_OBJ_TYPE_EXTRA = "airing_obj_type_extra";

    Object toEvent(Bundle extras);

    Bundle toExtras(Object obj);

    String getType();

}
