package com.thom.core.base;

/**
 * Non-Serializable base class with only a parameterized constructor.
 *
 * When a Serializable subclass (AppBeanImpl) extends this, SpotBugs raises
 * SE_NO_SUITABLE_CONSTRUCTOR because Java's deserialization mechanism requires
 * the first non-Serializable ancestor to expose a no-arg constructor.
 */
public class BeanBase {

    private final String beanName;

    public BeanBase(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
