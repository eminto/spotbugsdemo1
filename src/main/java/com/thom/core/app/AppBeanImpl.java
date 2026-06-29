package com.thom.core.app;

import com.thom.core.base.BeanBase;
import com.thom.core.service.UserService;

import java.io.Serializable;

/**
 * Concrete implementation of AppBean.
 *
 * SpotBugs findings demonstrated here:
 *
 *   SE_NO_SUITABLE_CONSTRUCTOR
 *     BeanBase (the non-Serializable parent) has no no-arg constructor.
 *     Java deserialization cannot reconstruct the parent state, so SpotBugs flags this.
 *
 *   SE_BAD_FIELD
 *     userService is not Serializable. Any attempt to serialize an AppBeanImpl
 *     instance will throw NotSerializableException at runtime.
 */
public class AppBeanImpl extends BeanBase implements AppBean, Serializable {

    private static final long serialVersionUID = 1L;

    // SE_BAD_FIELD: UserService does not implement Serializable
    private UserService userService;

    private String applicationVersion = "1.0-SNAPSHOT";
    private long requestCount = 0;

    public AppBeanImpl() {
        super("appBean");
        this.userService = new UserService();
    }

    @Override
    public String getApplicationVersion() {
        return applicationVersion;
    }

    @Override
    public long incrementAndGetRequestCount() {
        return ++requestCount;
    }

    @Override
    public long getRequestCount() {
        return requestCount;
    }

    @Override
    public int getUserCount() {
        return userService.findAll().size();
    }
}
