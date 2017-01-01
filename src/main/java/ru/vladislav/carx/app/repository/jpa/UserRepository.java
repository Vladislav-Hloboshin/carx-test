package ru.vladislav.carx.app.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vladislav.carx.domain.jpa.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Modifying
    @Query("update User u set u.money = ?2 where u.id = ?1")
    int updateMoney(UUID userId, long money);

}
