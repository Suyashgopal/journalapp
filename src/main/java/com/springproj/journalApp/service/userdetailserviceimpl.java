package com.springproj.journalApp.service;

import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.userentryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class userdetailserviceimpl implements UserDetailsService {

 @Autowired
 private userentryrepo userentryrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        user user = userentryrepo.findByUsername(username);
        if(user!= null){
          UserDetails userdetails=   org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
          return userdetails;


        }

throw new UsernameNotFoundException("user not found the username "+username );


    }
}
