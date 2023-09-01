package ua.project.login.bot.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import ua.project.login.bot.AuthBot;
import ua.project.login.constants.BotConstants;
import ua.project.login.kafka.producer.AuthProducer;
import ua.project.login.service.CodeService;
import ua.project.login.service.FileService;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PhotoHandler {

    AuthProducer authProducer;

    CodeService codeService;
    FileService fileService;

    public void handlePhoto(AuthBot bot,
                            Message message) {
        if(message.hasPhoto()) {
            finishLogin(bot, message);
        } else {
            bot.sendMessage(message.getChatId(), BotConstants.SEND_PHOTO_ERROR_TEXT);
        }
    }

    public void finishLogin(AuthBot bot,
                            Message message) {
        var code = codeService.findCodeByChatId(message.getChatId());
        List<PhotoSize> photos = message.getPhoto();
        PhotoSize photo = photos.get(photos.size() - 1);

        String avatarPath = fileService.downloadTelegramFile(bot,
                photo.getFileId());
        if(avatarPath != null) {
            authProducer.sendSuccessAuth(message, code, avatarPath);
            codeService.removeCode(code);
            bot.sendMessage(message.getChatId(), BotConstants.SUCCESS_AUTH_TEXT);
        }
    }

}

