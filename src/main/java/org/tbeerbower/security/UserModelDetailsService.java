package org.tbeerbower.security;

import org.tbeerbower.daos.UserDao;
import org.tbeerbower.model.Authority;
import org.tbeerbower.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

    private final UserDao userDao;

    public UserModelDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating user '{}'", login);

        User user = userDao.getUserByUsername(login);
        if (user != null) {
            return createSpringSecurityUser(login, user);
        } else {
            throw new UsernameNotFoundException("User " + login + " was not found.");
        }
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String login, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + login + " was not activated");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<Authority> userAuthorities = user.getAuthorities();
        for (Authority authority : userAuthorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}

