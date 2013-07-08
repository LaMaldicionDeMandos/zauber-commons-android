package com.zauberlabs.android.network.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hernan on 7/6/13.
 */
public class VoidBroadcastReceiver extends BroadcastReceiver implements EventReceiver {

    private final VoidCommand command;
    private final Event event;

    public VoidBroadcastReceiver(VoidCommand command, Event event) {
        checkNotNull(command);
        checkNotNull(event);
        this.command = command;
        this.event = event;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        command.execute(context);
    }

    @Override
    public Event getEvent() {
        return event;
    }
}
