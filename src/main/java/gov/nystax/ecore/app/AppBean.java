package gov.nystax.ecore.app;

import gov.nystax.ecore.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * Application-wide CDI bean exposed to JSF via @Named.
 *
 * WHY it implements Serializable
 * ────────────────────────────────
 * @ApplicationScoped beans are sometimes required to be Serializable for
 * portability across Jakarta EE containers and to support clustering/passivation
 * scenarios.
 *
 * WHY SpotBugs would flag it WITHOUT the filter
 * ──────────────────────────────────────────────
 * userService is an injected CDI proxy that does not implement Serializable,
 * triggering SE_BAD_FIELD.
 *
 * The no-arg constructor requirement for Externalizable is also flagged as
 * SE_NO_SUITABLE_CONSTRUCTOR / SE_NO_SUITABLE_CONSTRUCTOR_FOR_EXTERNALIZABLE
 * on some analysis paths.
 *
 * WHY the filter suppresses these for AppBean
 * ──────────────────────────────────────────────
 * ApplicationScoped beans are never passivated by the container; the
 * Serializable marker here is purely for specification compliance.
 * The CDI container re-injects proxies after any deserialization event.
 */
@Named
@ApplicationScoped
public class AppBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * SE_BAD_FIELD: UserService is not Serializable.
     * Suppressed via findbugs.xml – CDI proxy re-injection handles this.
     */
    @Inject
    private UserService userService;

    private String applicationVersion = "1.0-SNAPSHOT";
    private long requestCount = 0;

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public long incrementAndGetRequestCount() {
        return ++requestCount;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public int getUserCount() {
        return userService.findAll().size();
    }
}
