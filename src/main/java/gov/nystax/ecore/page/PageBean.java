package gov.nystax.ecore.page;

import gov.nystax.ecore.domain.Order;
import gov.nystax.ecore.domain.User;
import gov.nystax.ecore.service.UserService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * JSF backing bean scoped to the HTTP session.
 *
 * WHY it implements Serializable
 * ────────────────────────────────
 * Jakarta EE requires @SessionScoped beans to implement Serializable so that
 * the container can passivate (serialize) and activate (deserialize) sessions
 * during server restarts, cluster fail-overs, or memory pressure.
 *
 * WHY SpotBugs would flag it WITHOUT the filter
 * ──────────────────────────────────────────────
 * The injected UserService field is NOT itself Serializable (it is an
 * @ApplicationScoped CDI proxy). SpotBugs therefore raises:
 *
 *   SE_BAD_FIELD       – userService is not Serializable
 *   SE_BAD_FIELD_STORE – currentOrder (contains User refs) stored in a
 *                        Serializable class without being transient
 *
 * WHY the filter suppresses these for PageBean
 * ──────────────────────────────────────────────
 * CDI automatically re-injects proxies after deserialization, so the field
 * being non-serializable is intentional and safe.  The suppression is scoped
 * strictly to this class in spotbugs/findbugs.xml.
 */
@Named
@SessionScoped
public class PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * SE_BAD_FIELD: UserService does not implement Serializable.
     * Suppressed via findbugs.xml because CDI re-injects proxies after passivation.
     */
    @Inject
    private UserService userService;

    private User selectedUser;
    private Order currentOrder;
    private String statusMessage;

    public List<User> getAllUsers() {
        return userService.findAll();
    }

    public String selectUser(Long userId) {
        this.selectedUser = userService.findById(userId);
        this.statusMessage = "Selected: " + selectedUser.getUsername();
        return "user-detail?faces-redirect=true";
    }

    public String placeOrder() {
        if (selectedUser == null) {
            statusMessage = "No user selected.";
            return null;
        }
        currentOrder = userService.createSampleOrder(selectedUser);
        statusMessage = "Order placed: " + currentOrder.getId();
        return "order-confirm?faces-redirect=true";
    }

    public User getSelectedUser() { return selectedUser; }
    public Order getCurrentOrder() { return currentOrder; }
    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
}
