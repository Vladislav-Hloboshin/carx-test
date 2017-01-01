package ru.vladislav.carx.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "createDate_country", columnList = "createDate,country"),
        @Index(name = "country_money",columnList = "country,money")
})
public class User {

    @Id
    @Column(length = 16)
    private UUID id;

    @Column(length = 2, nullable = false)
    private String country;

    @Column(nullable = false, columnDefinition = "DATETIME(3)"/*mongodb stores milliseconds*/)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private long money;

}
