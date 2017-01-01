package ru.vladislav.carx.app.repository.mongodb.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
//@Entity("!fake!")
public class AggregatedUser {

    //@Property("_id")
    private UUID userId;

    private LocalDateTime minStamp;

    private LocalDateTime maxStamp;

    private String firstCountry;

    private long lastMoney;
}
