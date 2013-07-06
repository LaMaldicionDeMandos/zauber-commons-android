package com.zauberlabs.android.network;

import android.content.Intent;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hernan on 7/5/13.
 */
public class BroadcastManager {

    public static final String SINGLE_OBJECT_PAYLOAD_KEY = "DATA";
    public static final String LIST_PAYLOAD_KEY = "LIST";

    public static <T extends Parcelable> T getSingleObjectPayload(Intent intent) {
        return intent.getParcelableExtra(SINGLE_OBJECT_PAYLOAD_KEY);
    }

    public static <T extends  Parcelable>List<T> getListPayload(Intent intent) {
        return intent.getParcelableArrayListExtra(LIST_PAYLOAD_KEY);
    }
}
