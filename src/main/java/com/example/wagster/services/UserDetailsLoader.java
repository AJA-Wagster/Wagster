package com.example.wagster.services;

import com.example.wagster.models.User;
import com.example.wagster.models.UserWithRoles;
import com.example.wagster.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UserDetailsLoader implements UserDetailsService {
    private final UserRepo users;

    public UserDetailsLoader(UserRepo users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found" + username);
        }
        return new UserWithRoles(user);
    }

}
