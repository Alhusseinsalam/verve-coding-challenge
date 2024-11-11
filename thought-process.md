1. I used SpringBoot to build the server.
2. I used redis to store the unique requests data using the following data in redis
    - Hash Table: to store the map of minute with count of unique requests.
    - Set: to store the unique ids list per minute, set was used to ensure uniqueness.
    - the data id in redis is in the following format
      - minute:YYYYMMDDHH:MM
      - uniqueIds:YYYYMMDDHH:MM
3. I used kafka for real time data streaming of unique requests count.
4. I used a cron job in Spring to check at every 59th second the number of unique requests in that minute from redis and then log it and stream it to Kafka topic.