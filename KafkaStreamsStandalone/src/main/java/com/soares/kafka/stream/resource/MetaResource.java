package com.soares.kafka.stream.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaResource {

    private Long qtdContacts;
    private LocalDateTime executionTime = LocalDateTime.now();
}
