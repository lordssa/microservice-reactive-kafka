package com.cidsoares.bff.dataprovider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;


@Data
@Builder
@RedisHash("Response")
@AllArgsConstructor
public class ResponseDB {
    @Id
    private String correlationId;
    private String value;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long ttl;
}
