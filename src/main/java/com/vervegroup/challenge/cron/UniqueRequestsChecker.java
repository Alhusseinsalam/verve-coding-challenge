package com.vervegroup.challenge.cron;

import com.vervegroup.challenge.service.VerveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UniqueRequestsChecker {
    private static final Logger logger = LoggerFactory.getLogger(UniqueRequestsChecker.class);

    @Autowired
    private VerveService verveService;

    @Scheduled(cron = "59 * * * * ?")
    public void checkUniqueRequests() {
        logger.info("Number of unique requests: {}", verveService.getUniqueRequestCount());
    }

}
