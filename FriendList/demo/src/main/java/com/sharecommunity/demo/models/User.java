package com.sharecommunity.demo.models;

import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "user_id")
    private User requestsReceiver;

    // One User to send many pending Requests to other Users
    @OneToMany(mappedBy = "requestSender", fetch = FetchType.LAZY)
    private List<User> usersRequestedTo;

    // Many Requests received by One User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User requestSender;

    // Many Users can have Many Friends
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> friends;

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