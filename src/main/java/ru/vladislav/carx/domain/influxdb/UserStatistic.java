package ru.vladislav.carx.domain.influxdb;

import lombok.Builder;
import lombok.Data;
import org.influxdb.dto.Point;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
@Builder
public class UserStatistic implements InfluxDBEntity {
    private final long timeMillis;
    private final UUID userId;
    private final long activity;

    @Override
    public Point convert() {
        return Point.measurement("userStatistic")
                .time(timeMillis, TimeUnit.MILLISECONDS)
                .tag("userId", userId.toString())
                .field("activity", activity)
                .build();
    }
}
