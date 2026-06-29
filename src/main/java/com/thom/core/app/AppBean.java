package com.thom.core.app;

public interface AppBean {

    String getApplicationVersion();

    long incrementAndGetRequestCount();

    long getRequestCount();

    int getUserCount();
}
