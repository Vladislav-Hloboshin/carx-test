package ru.vladislav.carx.app.repository.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import ru.vladislav.carx.app.repository.mongodb.data.AggregatedUser;
import ru.vladislav.carx.domain.mongodb.UserData;

import java.time.LocalDateTime;
import java.util.Iterator;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class UserDataRepositoryImpl implements UserDataRepositoryCustom {

    private final MongoOperations mongoOperations;

    @Autowired
    public UserDataRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Iterator<AggregatedUser> getIteratorOfAggregatedUsers(LocalDateTime lastStamp){
        Aggregation agg = newAggregation(
                match(where("stamp").gt(lastStamp)),
                sort(ASC, "stamp"),
                group("userId")
                        .push("userId").as("userId")
                        .min("stamp").as("minStamp")
                        .max("stamp").as("maxStamp")
                        .first("country").as("firstCountry")
                        .last("money").as("lastMoney")
        );
        return mongoOperations.aggregate(agg, UserData.class, AggregatedUser.class).iterator();
    }

}
