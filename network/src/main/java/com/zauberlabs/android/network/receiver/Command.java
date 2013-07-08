package com.zauberlabs.android.network.receiver;

import android.content.Context;
import android.os.Parcelable;

/**
 * Created by hernan on 5/7/13.
 */
public interface Command<T extends Parcelable> {

    void execute(Context context, T data);

}
