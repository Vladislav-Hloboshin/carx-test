package ru.vladislav.carx.app.repository.mongodb;

import ru.vladislav.carx.app.repository.mongodb.data.AggregatedUser;

import java.time.LocalDateTime;
import java.util.Iterator;

public interface UserDataRepositoryCustom {
    Iterator<AggregatedUser> getIteratorOfAggregatedUsers(LocalDateTime lastStamp);
}
