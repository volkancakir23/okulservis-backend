package com.okulservis.service;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class BaseServiceImpl /*implements IBaseService*/ {

    public BaseServiceImpl() {
    }

    public SessionUser getSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails details = (UserDetails) authentication.getDetails();
        return (SessionUser)authentication.getPrincipal();
    }

}

