package ru.vladislav.carx.app.controller;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Controller;
import ru.vladislav.carx.app.repository.mongodb.UserDataRepository;
import ru.vladislav.carx.domain.influxdb.InfluxDBEntity;
import ru.vladislav.carx.domain.influxdb.UserStatistic;
import ru.vladislav.carx.domain.mongodb.UserData;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class EndpointsController {

    private final static String COUNTRY_FIELD = "country";
    private final static String MONEY_FIELD = "money";

    private final UserDataRepository userDataRepository;
    private final InfluxDBTemplate<InfluxDBEntity> influxDBTemplate;

    @Autowired
    public EndpointsController(UserDataRepository userDataRepository,
                               InfluxDBTemplate<InfluxDBEntity> influxDBTemplate)
    {
        this.userDataRepository = userDataRepository;
        this.influxDBTemplate = influxDBTemplate;
    }

    public UserStatistic storeStatistic(UUID userId, long activity){
        final UserStatistic userStatistic = UserStatistic.builder()
                .timeMillis(System.currentTimeMillis())
                .userId(userId)
                .activity(activity)
                .build();
        influxDBTemplate.write(userStatistic);

        return userStatistic;
    }

    public UserData storeData(UUID userId, String jsonData){
        if(jsonData.length()>10_1024) throw new RuntimeException("json is too big");

        DBObject data = (DBObject) JSON.parse(jsonData);

        String country = data.get(COUNTRY_FIELD).toString();
        long money;
        try{
            money = Long.parseLong(data.get(MONEY_FIELD).toString());
        }catch (NumberFormatException e){
            money = 0;
        }

        UserData userData = new UserData();
        userData.setUserId(userId);
        userData.setStamp(LocalDateTime.now());
        userData.setData(data);

        userData.setCountry( country.substring(0, Math.min(country.length(), 2)) );
        userData.setMoney(money);

        return userDataRepository.save(userData);
    }

    public String getLastData(UUID userId){
        UserData lastUserData = userDataRepository.findOneByUserIdOrderByStampDesc(userId);
        return lastUserData.getData().toString();
    }

}
