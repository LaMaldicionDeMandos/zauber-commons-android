package com.zauberlabs.android.network.receiver;

/**
 * Created by hernan on 7/9/13.
 */
public enum BasicEvent implements Event {
    
    ERROR("REQUEST_ERROR");

    private final String name;

    BasicEvent(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
