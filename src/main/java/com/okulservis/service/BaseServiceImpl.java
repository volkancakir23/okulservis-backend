package com.okulservis.service;

import com.okulservis.domain.User;
import com.okulservis.repository.UserRepository;
import com.okulservis.security.SecurityUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class BaseServiceImpl /*implements IBaseService*/ {

    public UserRepository userRepository;

    public BaseServiceImpl() {

    }

    public BaseServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SessionUser getSessionUser() {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        return (SessionUser)user.get();
    }

}

