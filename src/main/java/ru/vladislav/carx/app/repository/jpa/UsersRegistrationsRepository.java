package ru.vladislav.carx.app.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vladislav.carx.domain.jpa.UsersRegistrations;
import ru.vladislav.carx.domain.jpa.UsersRegistrationsPK;

import java.time.LocalDate;

public interface UsersRegistrationsRepository extends JpaRepository<UsersRegistrations, UsersRegistrationsPK> {

    @Modifying
    @Query("update UsersRegistrations ur set ur.count = ur.count+1 where ur.date=:date and ur.country=:country")
    int incrementCount(@Param("date") LocalDate date, @Param("country") String country);

}
