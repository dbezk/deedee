package ua.project.login;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.project.login.bot.AuthBot;

@SpringBootApplication
@EnableKafka
public class DeedeeLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeedeeLoginApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AuthBot authBot) {
		return args -> {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(authBot);
		};
	}

}
