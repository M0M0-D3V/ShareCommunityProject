package com.sharecommunity.demo.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Size(min = 1, message = " is required")
    private String username;
    @Size(min = 1, message = " is required")
    private String firstName;
    @Size(min = 1, message = " is required")
    private String lastName;
    @NotEmpty(message = " is required")
    @Email(message = " is required")
    private String email;
    @NotEmpty(message = " is required")
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

    // Many Users can receive requests from Many Users
    @ManyToMany(mappedBy = "requestReceivers")
    private Set<User> requestsReceivedFrom = new HashSet<User>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_received", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "receiver_id"))
    private Set<User> requestReceivers = new HashSet<User>();

    // Many Users can send requests to Many Users
    @ManyToMany(mappedBy = "requestSenders")
    private Set<User> requestsSentTo = new HashSet<User>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_sent", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "sender_id"))
    private Set<User> requestSenders;

    // Many Users can have Many Friends
    @ManyToMany(mappedBy = "friends")
    private Set<User> acceptedRequest = new HashSet<User>();

    // @ManyToMany(fetch = FetchType.LAZY)
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<User>();

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

    public User(@Size(min = 1, message = " is required") String username,
            @Size(min = 1, message = " is required") String firstName,
            @Size(min = 1, message = " is required") String lastName,
            @NotEmpty(message = " is required") @Email(message = " is required") String email,
            @NotEmpty(message = " is required") String contact) {
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

    public Set<User> getRequestsReceivedFrom() {
        return requestsReceivedFrom;
    }

    public void setRequestsReceivedFrom(Set<User> requestsReceivedFrom) {
        this.requestsReceivedFrom = requestsReceivedFrom;
    }

    public void addRequestsReceivedFrom(User user) {
        if (this.requestsReceivedFrom.contains(user)) {
            System.out.println(user.getUsername() + " already sent a request to " + this.getUsername());
        }
        this.requestsReceivedFrom.add(user);
        System.out.println("added " + user.getUsername() + " to " + this.getUsername() + "'s pending list...");
    }

    public Set<User> getRequestReceivers() {
        return requestReceivers;
    }

    public void setRequestReceivers(Set<User> requestReceivers) {
        this.requestReceivers = requestReceivers;
    }

    public Set<User> getRequestsSentTo() {
        System.out.println("Getting Requests Sent To...");
        for (User requestee : this.requestsSentTo) {
            System.out.println(requestee.getUsername());
        }
        return requestsSentTo;
    }

    public void setRequestsSentTo(Set<User> requestsSentTo) {
        this.requestsSentTo = requestsSentTo;
    }

    public void addRequestsSentTo(User user) {
        if (this.requestsSentTo.contains(user)) {
            System.out.println(user.getUsername() + " already received an invite from " + this.getUsername());
        }
        Set<User> list = this.requestsSentTo;
        list.add(user);
        this.setRequestsSentTo(list);
        // this.requestsSentTo.add(user);
    }

    public Set<User> getRequestSenders() {
        return requestSenders;
    }

    public void setRequestSenders(Set<User> requestSenders) {
        this.requestSenders = requestSenders;
    }

    public Set<User> getAcceptedRequest() {
        return acceptedRequest;
    }

    public void setAcceptedRequest(Set<User> acceptedRequest) {
        this.acceptedRequest = acceptedRequest;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void addFriends(User user) {
        if (this.friends.contains(user)) {
            System.out.println(user.getUsername() + " is already friends with " + this.getUsername());
        }
        this.friends.add(user);
        System.out.println(user.getUsername() + " has been added as " + this.getUsername() + "'s friend");
    }

}