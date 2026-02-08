package com.springproj.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "weather.api.key=dummy_key"
})
public class emailservicetest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendemail(
                "suyashchamoli1@gmail.com",
                "test no1",
                "hello this is just a test"
        );
    }
}
