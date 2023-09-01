package ua.project.login.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.project.login.bot.AuthBot;
import ua.project.login.enums.ImageType;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Value(value = "${static.path.avatars}")
    private String avatarsPath;

    @Value(value = "${static.path.characters}")
    private String charactersPath;

    @Value(value = "${static.path.avatars.extension}")
    private String avatarsExtension;

    public String downloadTelegramFile(AuthBot authBot, String fileId) {
        try {
            GetFile getFile = new GetFile();
            getFile.setFileId(fileId);
            org.telegram.telegrambots.meta.api.objects.File file =
                    authBot.execute(getFile);
            String avatarPath = avatarsPath + UUID.randomUUID() + avatarsExtension;
            authBot.downloadFile(file, new File(avatarPath));
            return avatarPath;
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Error when trying to download photo with id '{}'", fileId);
        }
        return null;
    }

    public Resource load(ImageType type, String filename) {
        Path root = null;
        if(type == ImageType.AVATAR) {
            root = Paths.get(avatarsPath);
        } else {
            root = Paths.get(charactersPath);
        }
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.warn("file '{}' not found", filename);
            }
        } catch (MalformedURLException e) {
            log.error("error when converting file '{}'", filename);
        }
        return null;
    }

}
