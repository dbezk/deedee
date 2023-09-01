package ua.project.deedee.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.project.deedee.data.DoneBattleData;
import ua.project.deedee.data.OnlineUserData;
import ua.project.deedee.dto.battle.CreateBattleDto;
import ua.project.deedee.dto.chat.ChatMessageDto;
import ua.project.deedee.dto.user.OnlineUserDto;
import ua.project.deedee.entity.user.DeeDeeUser;
import ua.project.deedee.enums.BalanceUpdateType;
import ua.project.deedee.enums.BattleWinner;
import ua.project.deedee.service.*;
import ua.project.deedee.ws.service.IWSService;

import java.time.LocalDateTime;
import java.util.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ScheduledWSActions {

    SimpMessagingTemplate simpMessagingTemplate;

    IUserService userService;
    IChatService chatService;
    IBattleService battleService;
    IMarketService marketService;
    IUserBalanceService userBalanceService;
    IWSService iwsService;

    List<OnlineUserData> onlineUserDataList;
    List<String> botMessagesList;
    List<DoneBattleData> finishedBattlesList;

    ObjectMapper objectMapper;

    Random random;

    @Scheduled(fixedDelay = 7000)
    public void sendPlayersOnlineList() {
        List<OnlineUserDto> onlineUserDtos =  new ArrayList<>();
        for (var user : onlineUserDataList) {
            var thisUser = userService.getUserById(Long.parseLong(user.getUserId()));
            onlineUserDtos.add(objectMapper.convertValue(thisUser, OnlineUserDto.class));
        }
        Collections.shuffle(onlineUserDataList);
        List<OnlineUserDto> randomOnlineUsers;
        randomOnlineUsers = onlineUserDtos.stream().limit(10)
                .toList();
        for (var session : onlineUserDataList) {
            simpMessagingTemplate.convertAndSendToUser(session.getSessionId(),
                    "/topic/onlineUsers", randomOnlineUsers);
        }
    }

    @Scheduled(fixedDelay = 2000)
    public void sendLastChatMessages() {
        var onlineList = onlineUserDataList;
        for (var session : onlineList) {
            simpMessagingTemplate.convertAndSendToUser(session.getSessionId(),
                    "/topic/chatMessages", chatService.getLastChatMessages());
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void sendBotMessage() {
        var bot = getRandomOnlineBot();
        if(bot != null) {
            String randomMessage = botMessagesList.get(random.nextInt(botMessagesList.size()));
            chatService.saveNewMessage(ChatMessageDto.builder()
                    .senderId(String.valueOf(bot.getId()))
                    .avatar(bot.getAvatar())
                    .messageText(randomMessage).build());
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void createBotGame() {
        if(battleService.getAllActiveBattles().size() < 40) {
            var bot = getRandomOnlineBot();
            if (bot != null) {
                var currencyType = random.nextInt(2);
                int gameAmount = currencyType == 0 ?
                        (bot.getMoney() * (random.nextInt(5) + 1)) / 100 :
                        (bot.getVipMoney() * (random.nextInt(3) + 1)) / 100;
                if (currencyType == 0 && (gameAmount < 100 || gameAmount > 50000)) {
                    gameAmount = random.nextInt(200) + 100;
                } else if (currencyType == 1 && (gameAmount < 2 || gameAmount > 100)) {
                    gameAmount = random.nextInt(50) + 4;
                }
                battleService.createNewBattle(
                        CreateBattleDto.builder()
                                .money(currencyType == 0 ? gameAmount : null)
                                .vipMoney(currencyType == 1 ? gameAmount : null)
                                .build(), bot.getId()
                );
            }
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void updateBotsBalance() {
        var bots = getOnlineBots();
        for(var botSession : bots) {
            var bot = userService.getUserById(Long.parseLong(botSession.getUserId()));
            if(bot.getMoney() < 5000) {
                bot.setMoney(random.nextInt(5000) + 10000);
            }
            if(bot.getVipMoney() < 300) {
                bot.setVipMoney(random.nextInt(200) + 100);
            }
            userService.saveUser(bot);
        }
    }

    @Scheduled(fixedRate = 1000)
    public void sendActiveBattles() {
        var onlineList = onlineUserDataList;
        for (var session : onlineList) {
            simpMessagingTemplate.convertAndSendToUser(session.getSessionId(),
                    "/topic/activeBattles", battleService.getAllActiveBattles());
        }
    }

    @Scheduled(fixedDelay = 7000)
    public void joinBotToActiveBattles() {
        var randomBot = getRandomOnlineBot();
        var activeBattles = battleService.getAllActiveBattles();
        var randomBattle = activeBattles.stream()
                        .filter(battle -> !battle.getBattleCreator().getId().equals(randomBot.getId()))
                        .filter(battle ->
                                battle.getMoney() != null ? battle.getMoney() <= randomBot.getMoney() : battle.getVipMoney() <= randomBot.getVipMoney())
                .findFirst().orElse(null);
        if(randomBattle != null) {
            battleService.joinBattle(randomBattle.getBattleId(), randomBot.getId());
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void boostRandomBot() {
        var randomBot = getRandomOnlineBot();
        if(randomBot.getUniqueProducts().size() > 2) {
            var products = marketService.getAllUniqueProducts();
            var product = products.stream()
                    .filter(p -> !randomBot.getUniqueProducts().contains(p))
                    .findFirst().orElse(null);
            if(product != null) {
                marketService.buyUniqueProduct(product.getId(), randomBot.getId());
            }
        } else {
            var products = marketService.getAllStaticProducts();
            var product = products.get(random.nextInt(products.size()));
            marketService.buyStaticProduct(product.getId(), randomBot.getId());
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void changeBotAttacks() {
        var randomBot = getOnlineBots();
        for(var botSession : randomBot) {
            var bot = userService.getUserById(Long.valueOf(botSession.getUserId()));
            if(bot.getCharacterStatistic().getUniqueProduct() != null) {
                bot.getCharacterStatistic().setUniqueProduct(null);
                userService.saveUser(bot);
            } else {
                if(!bot.getUniqueProducts().isEmpty()) {
                    var products = bot.getUniqueProducts();
                    Collections.shuffle(products);
                    bot.getCharacterStatistic().setUniqueProduct(products.get(0));
                }
            }
        }
    }

    // TODO: refactor scheduled (replace methods to WSService)

    @Scheduled(fixedDelay = 1000)
    public void finishBattles() {
        Iterator<DoneBattleData> battleDataIterator = finishedBattlesList.iterator();
        while (battleDataIterator.hasNext()) {
            var battleData = battleDataIterator.next();
            if (battleData.getEndsAt().isEqual(LocalDateTime.now()) ||
                    battleData.getEndsAt().isBefore(LocalDateTime.now())) {
                var battle = battleService.getBattleById(battleData.getBattleId());
                var winner = battle.getBattleInfo().getWinner() == BattleWinner.CREATOR ?
                        battle.getBattleCreator() : battle.getBattleOpponent();
                userBalanceService.updateBalance(winner, battle.getBattleInfo().getMoney(),
                        battle.getBattleInfo().getVipMoney(), BalanceUpdateType.MULTIPLY);
                winner.getUserStatistic().setRating(winner.getUserStatistic().getRating() + 3);
                userService.saveUser(winner);
                iwsService.notifyUser(String.valueOf(battle.getBattleCreator().getId()),
                        String.format("Battle #%d is over. Winner is %s", battle.getId(), battle.getBattleInfo().getWinner().toString()));
                iwsService.notifyUser(String.valueOf(battle.getBattleOpponent().getId()),
                        String.format("Battle #%d is over. Winner is %s", battle.getId(), battle.getBattleInfo().getWinner().toString()));
                battleDataIterator.remove();
            }
        }
    }

    public List<OnlineUserData> getOnlineBots() {
        return onlineUserDataList.stream()
                .filter(OnlineUserData::isBotSession)
                .toList();
    }

    public DeeDeeUser getRandomOnlineBot() {
        List<OnlineUserData> onlineBotsList = getOnlineBots();
        if(!onlineBotsList.isEmpty()) {
            String randomBotId = onlineBotsList.get(random.nextInt(onlineBotsList.size())).getUserId();
            return userService.getUserById(Long.parseLong(randomBotId));
        }
        return null;
    }

}
