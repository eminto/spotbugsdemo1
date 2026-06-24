package gov.nystax.ecore.domain;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Domain object representing an application user.
 *
 * SpotBugs rule enforced: domain objects MUST implement Serializable and declare
 * a serialVersionUID. All fields must themselves be Serializable (or transient).
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private LocalDate registeredOn;  // LocalDate is Serializable – no SE_BAD_FIELD

    public User() {}

    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.registeredOn = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getRegisteredOn() { return registeredOn; }
    public void setRegisteredOn(LocalDate registeredOn) { this.registeredOn = registeredOn; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "'}";
    }
}
