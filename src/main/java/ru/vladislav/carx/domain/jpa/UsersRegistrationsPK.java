package ru.vladislav.carx.domain.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UsersRegistrationsPK implements Serializable {
    private String country;
    private LocalDate date;
}
