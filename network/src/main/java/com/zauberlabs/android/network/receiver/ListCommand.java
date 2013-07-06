package com.zauberlabs.android.network.receiver;

import android.content.Context;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hernan on 7/5/13.
 */
public interface ListCommand<T extends Parcelable> {

    void execute(Context context, List<T> data);

}
