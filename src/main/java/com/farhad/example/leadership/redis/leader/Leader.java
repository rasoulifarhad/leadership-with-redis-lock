package com.farhad.example.leadership.redis.leader;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Service
@RequiredArgsConstructor
@Slf4j
public class Leader {

    private static final int LOCK_TIMEOUT = 5000;
	private static final String LEADER_LOCK = "LEADER_LOCK";

	private final Jedis jedis;
	private final String myApplicationId;
    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedDelay = 2000)
    public void tryToAcquireLock() {

        jedis.set(LEADER_LOCK,"" +  myApplicationId,SetParams.setParams().nx().px(LOCK_TIMEOUT));
        if (amILeader()) {
            log.info("{} I am leader!!!!",myApplicationId);
        } else {
            log.info("{} Sad I am not leader",myApplicationId);

        }
    }

    public boolean amILeader( ) {
        return myApplicationId.equals(jedis.getDel(LEADER_LOCK));
    }
}
