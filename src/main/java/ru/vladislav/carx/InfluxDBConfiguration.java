package ru.vladislav.carx;

import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBProperties;
import org.springframework.data.influxdb.InfluxDBTemplate;
import ru.vladislav.carx.domain.influxdb.InfluxDBEntity;

@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBConfiguration
{
    @Bean
    public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties)
    {
        return new InfluxDBConnectionFactory(properties);
    }

    @Bean
    public InfluxDBTemplate<InfluxDBEntity> influxDBTemplate(final InfluxDBConnectionFactory connectionFactory)
    {
        return new InfluxDBTemplate<>(connectionFactory, x->Lists.newArrayList(x.convert()));
    }

}
