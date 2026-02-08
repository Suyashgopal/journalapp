package com.springproj.journalApp.service;

import com.springproj.journalApp.entity.user;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.springproj.journalApp.repository.userentryrepo;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class userservicetest {
    @Autowired
    private userentryrepo userentryrepo;



//@Disabled//to disable a test
//   @Test
//public void testfindByusername(){
//    user user = userentryrepo.findByUsername("suyash");
//    assertTrue(!user.getJournalentries().isEmpty());
//
//   }


   @ParameterizedTest//this is for a parameter passesd test below are the test cases
    @CsvSource(
            {"1,1,2",
      "10,1,11",
        "12,1,2"})
    public void test(int a,int b,int expected){
    assertEquals(expected,a+b,"failed for "+a+b+expected);
   }

}
