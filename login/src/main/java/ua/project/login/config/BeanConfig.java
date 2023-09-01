package ua.project.login.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.project.login.model.LoginCode;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanConfig {

    @Bean
    @Qualifier(value = "authCodes")
    public List<LoginCode> loginCodeDtoList() {
        return new ArrayList<>();
    }

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
