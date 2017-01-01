package ru.vladislav.carx.domain.mongodb;

import com.mongodb.DBObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document(collection = "user_data")
@CompoundIndexes({
        @CompoundIndex(def = "{'userId': 1, 'stamp': -1}")
})
public class UserData {

    @Id
    private String id;

    private UUID userId;

    @Indexed
    private LocalDateTime stamp;

    private String country;

    private long money;

    private DBObject data;

}
