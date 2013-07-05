package com.zauberlabs.android.network.receiver;

import android.os.Parcelable;

/**
 * Created by hernan on 5/7/13.
 */
public class DataBroadcastReceiver<T extends Parcelable> {

    private Command<T> command;
    private Event event;

    public DataBroadcastReceiver(Command<T> command, Event event) {
        this.command = command;
        this.event = event;
    }
}
