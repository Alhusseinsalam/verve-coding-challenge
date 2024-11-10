package com.vervegroup.challenge.service;

import com.vervegroup.challenge.cron.UniqueRequestsChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class VerveService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH:mm");

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

    public String getUniqueRequestCount() {
        String currentMinute = getCurrentMinute();
        String counterKey = "minute:" + currentMinute;
        Object count = redisTemplate.opsForHash().get(counterKey, "numberOfUniqueRequests");
        return count == null ? "0" : count.toString();
    }
}
