package ua.project.deedee;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import ua.project.deedee.entity.character.DeeDeeCharacter;
import ua.project.deedee.entity.giveaway.MoneyGiveaway;
import ua.project.deedee.entity.giveaway.VipMoneyGiveaway;
import ua.project.deedee.entity.market.StaticProduct;
import ua.project.deedee.entity.market.StaticProductInfo;
import ua.project.deedee.entity.market.UniqueProduct;
import ua.project.deedee.entity.market.UniqueProductInfo;
import ua.project.deedee.enums.ProductEvent;
import ua.project.deedee.service.IAdminService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableKafka
public class DeeDeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeeDeeApplication.class, args);
	}

	@Bean
	CommandLineRunner run(IAdminService adminService,
						  Random random,
						  @Value(value = "${server.static.avatars.url-path}") String avatarsPath,
						  @Value(value = "${server.static.messages-path}") String messagesPath,
						  @Value(value = "${server.static.bots-path}") String botsPath,
						  @Qualifier(value = "botMessagesList") List<String> botMessagesList) {
		return args -> {
			var character1 = new DeeDeeCharacter("Poko",
					"characters/Poko.svg",
					17, 150, 10);
			var character2 = new DeeDeeCharacter("Cleek",
					"characters/Cleek.svg",
					10, 130, 17);
			adminService.addCharacter(character1);
			adminService.addCharacter(character2);

			var staticProduct1 = new StaticProduct("Health booster",
					"+10 to character heath", 200, null,
					new StaticProductInfo(null, 10, null));
			var staticProduct2 = new StaticProduct("Power booster",
					"+10 to character power",200, null,
					new StaticProductInfo(10, null, null));
			var staticProduct3 = new StaticProduct("Speed booster",
					"+10 to character speed", 200, null,
					new StaticProductInfo(null, null, 10));
			adminService.addStaticProduct(staticProduct1);
			adminService.addStaticProduct(staticProduct2);
			adminService.addStaticProduct(staticProduct3);

			var uniqueProduct1 = new UniqueProduct("Titanium attack",
					"Give a chance for damage up to 400 health per attack.", null, 15,
					new UniqueProductInfo(10, null, null, ProductEvent.ATTACK));
			adminService.addUniqueProduct(uniqueProduct1);

			var moneyGiveaway = new MoneyGiveaway(
					"100 money", "You can collect 100 money per 24 hours.", 100, 24);
			var vipMoneyGiveaway = new VipMoneyGiveaway(
					"2 vip money", "You can collect 2 vip money per 24 hours.", 2, 24);
			adminService.addMoneyGiveaway(moneyGiveaway);
			adminService.addVipMoneyGiveaway(vipMoneyGiveaway);

			List<String> allEngMessages = Files.readAllLines(Paths.get(messagesPath + "messages_eng.txt"));
			botMessagesList.addAll(allEngMessages);

			List<String> botsListOne = Files.readAllLines(Paths.get(botsPath + "bots_1.txt"));
			List<String> botsListTwo = Files.readAllLines(Paths.get(botsPath + "bots_2.txt"));

			for(var bot : botsListOne) {
				String[] botData = bot.split(" ");
				String[] botAvatar = botData[1].split(":");
				adminService.addBot(botData[0], botAvatar[0],
						avatarsPath + botAvatar[1], 50000, 500,
						random.nextInt(2) == 0 ? character1 : character2);
			}
			for(var bot : botsListTwo) {
				String[] botData = bot.split(":");
				adminService.addBot(botData[0], null,
						avatarsPath + botData[1], 50000, 500,
						random.nextInt(2) == 0 ? character1 : character2);
			}
		};
	}

}
