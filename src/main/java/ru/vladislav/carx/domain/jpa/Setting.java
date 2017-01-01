package ru.vladislav.carx.domain.jpa;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Setting {

    public static String LAST_USER_DATA_STAMP = "lastUserDataStamp";

    @Id
    private String name;

    @Column
    private Long longValue;

    @Column(columnDefinition = "DATETIME(3)"/*mongodb stores milliseconds*/)
    private LocalDateTime dateTimeValue;

    @Column
    private String stringValue;

}
