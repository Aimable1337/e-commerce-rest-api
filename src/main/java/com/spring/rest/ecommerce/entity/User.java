package com.spring.rest.ecommerce.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "username")
    private String userName;

    private String password;

    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetail userDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_authority_id")
    private UserAuthority userAuthority;

    public User() {}

    public User(String userName, String password, boolean enabled, UserDetail userDetails, UserAuthority userAuthority) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.userDetails = userDetails;
        this.userAuthority = userAuthority;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        // causes null pointer exception
        // TODO: develop save way to do this
        //this.userAuthority.setUsername(userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserDetail getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetail userDetails) {
        this.userDetails = userDetails;
    }

    public UserAuthority getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(UserAuthority userAuthority) {
        this.userAuthority = userAuthority;
    }
}
