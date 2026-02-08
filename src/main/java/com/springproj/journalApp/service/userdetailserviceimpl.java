package com.springproj.journalApp.service;

import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.userentryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())   // BCrypt hash
                .authorities("ROLE_USER")       // ✅ GUARANTEED authority
                .build();
    }

}
