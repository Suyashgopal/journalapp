package com.springproj.journalApp.service;

import com.springproj.journalApp.model.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;


    // @KafkaListener(topics ="weekly-sentiments",groupId = "weekly-sentiment-group")//checks for sentiment msgs constatly
    public void consume(SentimentData sentimentData){
        sendemail(sentimentData);

    }


    private void sendemail(SentimentData sentimentData){
        emailService.sendemail(sentimentData.getEmail(),"sentiment for prev week",sentimentData.getSentiment());
    }


}
