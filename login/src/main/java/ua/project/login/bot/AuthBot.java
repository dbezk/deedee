package ua.project.login.bot;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.project.login.bot.handler.CommandHandler;
import ua.project.login.bot.handler.MessageHandler;
import ua.project.login.bot.handler.PhotoHandler;
import ua.project.login.constants.BotConstants;
import ua.project.login.service.CodeService;
import ua.project.login.service.FileService;
import ua.project.login.kafka.producer.AuthProducer;

import java.util.List;

@Component
@Slf4j
public class AuthBot extends TelegramLongPollingBot {

    private final CommandHandler commandHandler;
    private final MessageHandler messageHandler;
    private final PhotoHandler photoHandler;

    @Value(value = "${static.path.avatars}")
    private String avatarsPath;

    public AuthBot(@Value(value = "${bot.token}") String botToken,
                   CommandHandler commandHandler,
                   MessageHandler messageHandler,
                   PhotoHandler photoHandler) {
        super(botToken);
        this.commandHandler = commandHandler;
        this.messageHandler = messageHandler;
        this.photoHandler = photoHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            var message = update.getMessage();
            if(!messageHandler.isChatFrozen(message.getChatId())) {
                if(update.getMessage().isCommand()) {
                    commandHandler.handleCommand(this, message);
                }
                if(update.getMessage().hasText() &&
                    !update.getMessage().isCommand()) {
                    messageHandler.handleCode(this, message);
                }
            } else {
                photoHandler.handlePhoto(this, message);
            }
        }
    }

    public void sendMessage(Long chatId, String message) {
        var newMessage = new SendMessage();
        newMessage.setChatId(chatId);
        newMessage.setText(message);
        newMessage.enableHtml(true);
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            log.error("Error when send message, chat id = {}", chatId);
        }
    }

    @Override
    public String getBotUsername() {
        return "deedeegamebot";
    }


}
