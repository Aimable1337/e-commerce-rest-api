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

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetail userDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_authority_id")
    private UserAuthority userAuthority;

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
