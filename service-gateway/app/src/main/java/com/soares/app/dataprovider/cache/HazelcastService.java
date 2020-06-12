package com.soares.app.dataprovider.cache;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class HazelcastService {

    @Getter
    private HazelcastInstance hzInstance;

    @PostConstruct
    private void init() {
        // Running Hazelcast
        hzInstance = Hazelcast.newHazelcastInstance();
    }

    @Bean
    public Config hazelCastConfig() {
        //config cache setup
        Config config = new Config();
        config.setInstanceName("EXAMPLE-CACHE");

        MapConfig ourCache = new MapConfig();
        ourCache.setTimeToLiveSeconds(1000);
        ourCache.setEvictionPolicy(EvictionPolicy.LFU);
        config.getMapConfigs().put("OUR_CACHE", ourCache);

        return config;
    }
}