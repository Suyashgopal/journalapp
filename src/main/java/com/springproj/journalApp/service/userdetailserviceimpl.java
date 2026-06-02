package com.springproj.journalApp.service;

import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.userentryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userdetailserviceimpl implements UserDetailsService {

 @Autowired
 private userentryrepo userentryrepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        user user = userentryrepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Map the user's actual persisted roles to Spring authorities.
        // Previously this was hardcoded to "ROLE_USER", which made every
        // ADMIN authority unreachable and broke all role-based access control.
        List<String> roles = user.getRoles();
        String[] authorities = (roles == null || roles.isEmpty())
                ? new String[]{"ROLE_USER"}
                : roles.stream().map(r -> "ROLE_" + r).toArray(String[]::new);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())   // BCrypt hash
                .authorities(authorities)
                .build();
    }

}
