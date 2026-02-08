package com.springproj.journalApp.scheduler;

import com.springproj.journalApp.entity.journalentry;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.enums.sentiment;
import com.springproj.journalApp.model.SentimentData;
import com.springproj.journalApp.repository.userRepoimpl;
import com.springproj.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class userscheduler {


    @Autowired
    private EmailService emailservice;
    @Autowired
    private userRepoimpl userRepoimpl;

    @Autowired
    private KafkaTemplate<String , SentimentData> kafkaTemplate;


    @Scheduled(cron = "0 0 1 * * ?")
    public void fetchUSERsendSAmail() {

        List<user> users = userRepoimpl.getuserforsa();

        for (user u : users) {

            List<journalentry> journalentries = u.getJournalentries();

            List<sentiment> sentiments = journalentries.stream()
                    .filter(x -> x.getDate().isAfter(
                            LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(journalentry::getSentiment)
                    .filter(s -> s != null)
                    .collect(Collectors.toList());

            if (sentiments.isEmpty()) {
                continue; // no entries in last 7 days
            }

            Map<sentiment, Integer> sentimentcounts = new HashMap<>();

            for (sentiment s : sentiments) {
                sentimentcounts.put(s,
                        sentimentcounts.getOrDefault(s, 0) + 1);
            }

            sentiment mostfreq = null;
            int maxcount = 0;

            for (Map.Entry<sentiment, Integer> entry : sentimentcounts.entrySet()) {
                if (entry.getValue() > maxcount) {
                    maxcount = entry.getValue();
                    mostfreq = entry.getKey();
                }
            }

            if (mostfreq != null) {
                //SENDING A DIRECT EMAIL -----------------------WE USE KAFKA
//                emailservice.sendemail(
//                        u.getEmail(),
//                        "Sentiment for last 7 days",
//                        "Your most frequent sentiment this week was: " + mostfreq
//                );
                SentimentData sentimentData = SentimentData.builder().email(u.getEmail()).sentiment("sentiment for last 7 days " +mostfreq).build();
                  kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(),sentimentData);//key is email

            }
        }
    }

    }
