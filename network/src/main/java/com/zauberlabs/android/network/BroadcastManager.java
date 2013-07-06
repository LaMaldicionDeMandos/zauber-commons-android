package com.zauberlabs.android.network;

import android.content.Intent;
import android.os.Parcelable;

/**
 * Created by hernan on 7/5/13.
 */
public class BroadcastManager {

    public static final String SINGLE_OBJECT_PAYLOAD_KEY = "DATA";

    public static <T extends Parcelable> T getSingleObjectPayload(Intent intent) {
        return intent.getParcelableExtra(SINGLE_OBJECT_PAYLOAD_KEY);
    }
}
