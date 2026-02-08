package com.springproj.journalApp.service;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.userentryrepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

@SpringBootTest
public class userdetailserviceimpltest {
    @InjectMocks
    private userdetailserviceimpl userdetailserviceimpl;

    @Mock
    private userentryrepo userentryrepo;
    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }





@Test
    void loadUserByUsernametest(){
    when(userentryrepo.findByUsername(ArgumentMatchers.anyString())).thenReturn(user.builder().username("ram").password("lol").build());
    UserDetails user= userdetailserviceimpl.loadUserByUsername("suyash");
    Assertions.assertNotNull(user);
}

}
