package ru.vladislav.carx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vladislav.carx.app.controller.EndpointsController;
import ru.vladislav.carx.domain.mongodb.UserData;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = "scheduler.disabled=true")
public class EndpointsControllerTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EndpointsController controller;

    @Test
    public void testStoreStatistic(){
        LocalDateTime testStartTime = LocalDateTime.now();
        UUID userId = UUID.randomUUID();

        String key = controller.storeData(userId, "{country:'RU',money:1}").getId();

        UserData userData = mongoTemplate.findById(key, UserData.class);

        assertNotNull(userData);
        assertEquals(userData.getUserId(), userId);
        assertEquals(userData.getCountry(), "RU");
        assertEquals(userData.getMoney(), 1);
        assertTrue(userData.getStamp().compareTo(testStartTime) >= 0);
        assertTrue(userData.getStamp().compareTo(LocalDateTime.now()) <= 0);
    }

}
