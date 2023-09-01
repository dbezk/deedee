package ua.project.deedee.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.project.deedee.data.DoneBattleData;
import ua.project.deedee.data.OnlineUserData;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;

import java.util.*;

@Configuration
public class BeanConfig {

    @Bean
    @Qualifier(value = "onlineUserDataList")
    public List<OnlineUserData> onlineUserDataList() {
        return new ArrayList<>();
    }

    @Bean
    @Qualifier(value = "mobileAuthedList")
    public Map<Integer, DeeDeeUserPersonalInfoDto> mobileAuthedList() {
        return new HashMap<>();
    }

    @Bean
    @Qualifier(value = "botMessagesList")
    public List<String> botMessagesList() {
        return new ArrayList<>();
    }

    @Bean
    @Qualifier(value = "finishedBattlesList")
    public List<DoneBattleData> finishedBattlesList() {
        return new ArrayList<>();
    }

    @Bean
    public Random getRandom() {
        return new Random();
    }

}
