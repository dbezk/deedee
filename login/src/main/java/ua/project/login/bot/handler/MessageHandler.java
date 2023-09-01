package ua.project.login.bot.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.project.login.bot.AuthBot;
import ua.project.login.bot.constants.BotCommand;
import ua.project.login.constants.AuthCodeStatus;
import ua.project.login.constants.BotConstants;
import ua.project.login.model.LoginCode;
import ua.project.login.service.CodeService;

import java.util.List;
import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MessageHandler {

    List<LoginCode> codeList;

    CodeService codeService;

    public void handleCode(AuthBot bot,
                           Message message) {
        try {
            var intCode = Integer.parseInt(message.getText());
            var code = codeService.findCode(intCode);
            if(code != null) {
                if(!codeService.isCodeExpired(code)) {
                    codeService.changeCodeStep(message.getChatId(), code);
                    bot.sendMessage(message.getChatId(), BotConstants.SEND_PHOTO_TEXT);
                } else {
                    bot.sendMessage(message.getChatId(), BotConstants.ERROR_CODE_WAS_EXPIRED);
                }
            } else {
                bot.sendMessage(message.getChatId(), BotConstants.ERROR_CODE_NOT_EXISTS_TEXT);
            }
        } catch (NumberFormatException e) {
            bot.sendMessage(message.getChatId(),
                    BotConstants.ERROR_MESSAGE_LENGTH_TEXT);
        }
    }

    public boolean isChatFrozen(Long chatId) {
        return codeList.stream()
                .filter(c -> c.getChatId() != null)
                .filter(c -> Objects.equals(c.getChatId(), chatId))
                .anyMatch(c -> c.getCodeStatus() == AuthCodeStatus.WAITING);
    }

}
