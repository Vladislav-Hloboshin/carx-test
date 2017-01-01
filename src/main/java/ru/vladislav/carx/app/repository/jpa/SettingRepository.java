package ru.vladislav.carx.app.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladislav.carx.domain.jpa.Setting;

public interface SettingRepository extends JpaRepository<Setting, String> {

}
