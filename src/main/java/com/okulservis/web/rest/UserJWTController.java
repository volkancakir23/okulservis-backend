package com.okulservis.web.rest;

import com.okulservis.domain.OkuPersonel;
import com.okulservis.domain.OkuSofor;
import com.okulservis.domain.User;
import com.okulservis.repository.OkuPersonelRepository;
import com.okulservis.repository.OkuSoforRepository;
import com.okulservis.repository.UserRepository;
import com.okulservis.security.jwt.JWTConfigurer;
import com.okulservis.security.jwt.TokenProvider;
import com.okulservis.web.rest.vm.LoginVM;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.okulservis.web.rest.vm.ResponseLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final OkuSoforRepository okuSoforRepository;

    private final OkuPersonelRepository okuPersonelRepository;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager,
                             UserRepository userRepository, OkuSoforRepository okuSoforRepository,
                             OkuPersonelRepository okuPersonelRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.okuSoforRepository = okuSoforRepository;
        this.okuPersonelRepository = okuPersonelRepository;
    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        Optional<User> user = userRepository.findOneByLogin(loginVM.getUsername());
        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);

            OkuPersonel okuPersonel = (OkuPersonel) okuPersonelRepository.findByUserIsCurrentUser();
            OkuSofor okuSofor = (OkuSofor) okuSoforRepository.findOkuSoforByUser(user.get());

            ResponseLogin responseLogin = new ResponseLogin(1, user.get(), new JWTToken(jwt), okuPersonel, okuSofor);
            //return ResponseEntity.ok(new JWTToken(jwt));
            return ResponseEntity.ok(responseLogin);
        } catch (AuthenticationException ae) {
            log.trace("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    public static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
