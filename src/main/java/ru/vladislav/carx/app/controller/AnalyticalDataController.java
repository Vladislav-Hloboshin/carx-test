package ru.vladislav.carx.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ru.vladislav.carx.app.repository.jpa.SettingRepository;
import ru.vladislav.carx.app.repository.jpa.UserRepository;
import ru.vladislav.carx.app.repository.jpa.UsersRegistrationsRepository;
import ru.vladislav.carx.app.repository.mongodb.UserDataRepository;
import ru.vladislav.carx.app.repository.mongodb.data.AggregatedUser;
import ru.vladislav.carx.domain.jpa.Setting;
import ru.vladislav.carx.domain.jpa.User;
import ru.vladislav.carx.domain.jpa.UsersRegistrations;

import java.time.LocalDateTime;
import java.util.Iterator;

@Controller
public class AnalyticalDataController {

    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    private final UsersRegistrationsRepository usersRegistrationsRepository;
    private final SettingRepository settingRepository;

    @Autowired
    public AnalyticalDataController(UserDataRepository userDataRepository,
                                    UserRepository userRepository,
                                    UsersRegistrationsRepository usersRegistrationsRepository,
                                    SettingRepository settingRepository)
    {
        this.userDataRepository = userDataRepository;
        this.userRepository = userRepository;
        this.usersRegistrationsRepository = usersRegistrationsRepository;
        this.settingRepository = settingRepository;
    }

    @Transactional
    public void createAnalyticalData(){
        Setting lastUserDataStampSetting = settingRepository.findOne(Setting.LAST_USER_DATA_STAMP);

        LocalDateTime lastUserDataStamp = lastUserDataStampSetting==null
                ? LocalDateTime.now().minusYears(1)
                : lastUserDataStampSetting.getDateTimeValue();

        Iterator<AggregatedUser> iterator = userDataRepository.getIteratorOfAggregatedUsers(lastUserDataStamp);

        while(iterator.hasNext()){
            AggregatedUser aggregatedUser = iterator.next();
            if(lastUserDataStampSetting==null || aggregatedUser.getMaxStamp().isAfter(lastUserDataStampSetting.getDateTimeValue())){
                lastUserDataStampSetting = Setting.builder()
                        .name(Setting.LAST_USER_DATA_STAMP)
                        .dateTimeValue(aggregatedUser.getMaxStamp())
                        .build();
            }

            if(userRepository.updateMoney(aggregatedUser.getUserId(), aggregatedUser.getLastMoney())>0) continue;
            userRepository.save(User.builder()
                    .id(aggregatedUser.getUserId())
                    .country(aggregatedUser.getFirstCountry())
                    .createDate(aggregatedUser.getMinStamp())
                    .money(aggregatedUser.getLastMoney())
                    .build());
            if(usersRegistrationsRepository.incrementCount(aggregatedUser.getMinStamp().toLocalDate(), aggregatedUser.getFirstCountry())>0) continue;
            usersRegistrationsRepository.save(UsersRegistrations.builder()
                    .date(aggregatedUser.getMinStamp().toLocalDate())
                    .country(aggregatedUser.getFirstCountry())
                    .count(1)
                    .build());
        }

        if(lastUserDataStampSetting!=null){
            settingRepository.save(lastUserDataStampSetting);
        }
    }

}
