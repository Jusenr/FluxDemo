package com.myself.fluxdemo.basic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by leo on 2016/10/18.
 */

public class BaseNetworkStateListener extends BroadcastReceiver {
    private int status = 0;
    public static final int NO_NETWORK = -1;

    @Override
    public void onReceive(Context context, Intent intent) {


//            EventBusHelper.post();
    }
}
