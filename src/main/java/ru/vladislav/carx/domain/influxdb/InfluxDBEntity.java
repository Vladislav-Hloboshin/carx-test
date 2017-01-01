package ru.vladislav.carx.domain.influxdb;

import org.influxdb.dto.Point;

public interface InfluxDBEntity {
    Point convert();
}
