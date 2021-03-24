package com.spring.rest.ecommerce.DTO.User;

import com.spring.rest.ecommerce.entity.UserAuthority;

public class NewUsersAuthorityDTO {

    private String newAuthority;

    public UserAuthority ChangeAuthority(UserAuthority authority){
        authority.setAuthority(newAuthority);
        return authority;
    }

    public String getNewAuthority() {
        return newAuthority;
    }

    public void setNewAuthority(String newAuthority) {
        this.newAuthority = newAuthority;
    }
}
