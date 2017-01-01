package ru.vladislav.carx.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.convert.threetenbp.ThreeTenBackPortJpaConverters;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(UsersRegistrationsPK.class)
public class UsersRegistrations {

    @Id
    @Column(length = 2, nullable = false)
    private String country;

    @Id
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private long count = 0;

}
