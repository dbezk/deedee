package ua.project.login.bot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.project.login.bot.AuthBot;
import ua.project.login.bot.constants.BotCommand;
import ua.project.login.constants.BotConstants;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandHandler {

    public void handleCommand(AuthBot bot,
                              Message message) {
        var messageText = message.getText();
        if(messageText.equals(BotCommand.START_COMMAND)) {
            bot.sendMessage(message.getChatId(),
                    String.format(BotConstants.START_GREETING_TEXT,
                            message.getChat().getFirstName()));
        }
    }

}
