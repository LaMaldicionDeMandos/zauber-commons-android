package com.zauberlabs.android.network.receiver;

/**
 * Created by hernan on 5/7/13.
 */
public interface Event {

    Class getPayloadClass();

    String getName();

}
