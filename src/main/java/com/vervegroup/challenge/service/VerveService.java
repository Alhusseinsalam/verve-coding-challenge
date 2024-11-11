package com.vervegroup.challenge.service;

import com.vervegroup.challenge.cron.UniqueRequestsChecker;
import com.vervegroup.challenge.model.UniqueRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class VerveService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH:mm");
    private static final Logger logger = LoggerFactory.getLogger(VerveService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "unique-requests-count-per-minute";

    public String getCurrentMinute() {
        return LocalDateTime.now().format(FORMATTER);
    }

    public boolean handleRequest(int id) {
        String currentMinute = getCurrentMinute();
        String uniqueIdsKey = "uniqueIds:" + currentMinute;
        String counterKey = "minute:" + currentMinute;

        SetOperations<String, Object> setOps = redisTemplate.opsForSet();

        if (setOps.add(uniqueIdsKey, Integer.toString(id)) == 1) {
            redisTemplate.opsForHash().increment(counterKey, "numberOfUniqueRequests", 1);
            return true;
        }
        return false;
    }

    public void sendUniqueRequestCount(String endpoint) {
        String uniqueRequestCount = getUniqueRequestCount();
        ResponseEntity<UniqueRequests> response = restTemplate.postForEntity(endpoint, new UniqueRequests(uniqueRequestCount), UniqueRequests.class);
        logger.info("HTTP Status Code: {}", response.getStatusCodeValue());
    }

    public String getUniqueRequestCount() {
        String currentMinute = getCurrentMinute();
        String counterKey = "minute:" + currentMinute;
        Object count = redisTemplate.opsForHash().get(counterKey, "numberOfUniqueRequests");
        return count == null ? "0" : count.toString();
    }

    public void streamUniqueRequests() {
        String uniqueRequestCount = getUniqueRequestCount();
        kafkaTemplate.send(TOPIC, uniqueRequestCount);
    }


}
