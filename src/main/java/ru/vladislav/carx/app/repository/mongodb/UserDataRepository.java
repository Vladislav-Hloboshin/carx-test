package ru.vladislav.carx.app.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.vladislav.carx.domain.mongodb.UserData;

import java.util.UUID;

public interface UserDataRepository extends MongoRepository<UserData, String>,UserDataRepositoryCustom {

    UserData findOneByUserIdOrderByStampDesc(UUID userId);

}
