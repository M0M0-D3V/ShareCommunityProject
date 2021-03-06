package com.sharecommunity.demo.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, message = "First Name is required")
    private String username;
    @Size(min = 1, message = "First Name is required")
    private String firstName;
    @Size(min = 1, message = "Last Name is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is required")
    private String email;
    @NotEmpty(message = "Contact is required")
    private String contact;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @Transient
    private String passwordConfirmation;
    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    // ***************FKs and JOINS****************************

    // One User can receive many requests from other Users
    @OneToMany(mappedBy = "requestsReceiver", fetch = FetchType.LAZY)
    private List<User> requestsReceivedFrom;

    // Many Requests received by One User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User requestsReceiver;

    // One User to send many pending Requests to other Users
    @OneToMany(mappedBy = "requestSender", fetch = FetchType.LAZY)
    private List<User> usersRequestedTo;

    // Many Requests received by One User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User requestSender;

    // Many Users can have Many Friends
    @ManyToMany(fetch = FetchType.LAZY)
    // @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<User> friends;

    @ManyToMany(mappedBy = "friends")
    private List<User> acceptedRequest;

    // @JoinTable(name = "EMPLOYEE_COLLEAGUE", joinColumns = { @JoinColumn(name =
    // "EMPLOYEE_ID") }, inverseJoinColumns = {
    // @JoinColumn(name = "COLLEAGUE_ID") })
    // private Set<Employee> colleagues = new HashSet<Employee>();

    // @ManyToMany(mappedBy = "colleagues")
    // private Set<Employee> teammates = new HashSet<Employee>();

    public User() {

    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    public User(Long id, @Size(min = 1, message = "First Name is required") String username,
            @Size(min = 1, message = "First Name is required") String firstName,
            @Size(min = 1, message = "Last Name is required") String lastName,
            @Email(message = "Email is required") String email,
            @NotEmpty(message = "Contact is required") String contact) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<User> getRequestsReceivedFrom() {
        return requestsReceivedFrom;
    }

    public void setRequestsReceivedFrom(List<User> requestsReceivedFrom) {
        this.requestsReceivedFrom = requestsReceivedFrom;
    }

    public User getRequestsReceiver() {
        return requestsReceiver;
    }

    public void setRequestsReceiver(User requestsReceiver) {
        this.requestsReceiver = requestsReceiver;
    }

    public List<User> getUsersRequestedTo() {
        return usersRequestedTo;
    }

    public void setUsersRequestedTo(List<User> usersRequestedTo) {
        this.usersRequestedTo = usersRequestedTo;
    }

    public User getRequestSender() {
        return requestSender;
    }

    public void setRequestSender(User requestSender) {
        this.requestSender = requestSender;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}