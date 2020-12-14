package com.spring.rest.ecommerce.DTO;

import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.entity.UserDetail;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserViewDTO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetail userDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_authority_id")
    private UserAuthority userAuthority;

    public UserViewDTO() {}

    public UserViewDTO(String userName, boolean enabled, UserDetail userDetails, UserAuthority userAuthority) {
        this.userName = userName;
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
