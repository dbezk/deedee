package ua.project.login.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.project.login.enums.ImageType;
import ua.project.login.service.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class FileController {

    final FileService fileService;

    @Value(value = "${static.path.characters}")
    String charactersPath;

    @GetMapping(value = "/avatars/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getAvatarImage(@PathVariable String filename) {
        try {
            Resource file = fileService.load(ImageType.AVATAR, filename);
            InputStream inputStream = file.getInputStream();
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.info("error when trying to return file '{}'", filename);
        }
        return null;
    }

//    @GetMapping(value = "/characters/{filename:.+}",
//            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.valueOf("image/svg+xml")})
//    public @ResponseBody byte[] getCharacterImage(@PathVariable String filename) {
//        try {
//            Resource file = fileService.load(ImageType.CHARACTER, filename);
//            InputStream inputStream = file.getInputStream();
//            return IOUtils.toByteArray(inputStream);
//        } catch (IOException e) {
//            log.info("error when trying to return file '{}'", filename);
//        }
//        return null;
//    }

    @GetMapping(value = "/characters/{filename:.+}")
    public ResponseEntity<Resource> viewImg(@PathVariable String filename)
        throws IOException {
        Path root = Paths.get(charactersPath);
        Path file = root.resolve(filename);
        FileSystemResource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(file)))
                .body(resource);
    }

}
