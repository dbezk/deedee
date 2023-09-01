package ua.project.login.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.project.login.constants.AuthCodeStatus;
import ua.project.login.model.LoginCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CodeService {

    @Qualifier("authCodes")
    List<LoginCode> codeList;

    public boolean checkWaiting(Long chatId) {
        log.info("list = '{}'", codeList);
        return codeList.stream()
                .filter(c -> c.getChatId() != null)
                .filter(c -> Objects.equals(c.getChatId(), chatId))
                .anyMatch(c -> c.getCodeStatus() == AuthCodeStatus.WAITING);
    }

    public void changeCodeStep(Long chatId, LoginCode loginCode) {
        codeList.remove(loginCode);
        loginCode.setChatId(chatId);
        loginCode.setCodeStatus(AuthCodeStatus.WAITING);
        codeList.add(loginCode);
    }

    public boolean isCodeExpired(LoginCode loginCode) {
        if(LocalDateTime.now().isBefore(loginCode.getExpiresAt())) {
            return false;
        }
        removeCode(loginCode);
        return true;
    }

    public LoginCode findCode(Integer code) {
        return codeList.stream()
                .filter(c -> Objects.equals(c.getCode(), code))
                .findAny()
                .orElse(null);
    }

    public LoginCode findCodeByChatId(Long chatId) {
        return codeList.stream()
                .filter(c -> Objects.equals(c.getChatId(), chatId))
                .findAny()
                .orElse(null);
    }

    public void addCode(LoginCode codeDto) {
        codeList.add(codeDto);
    }

    public void removeCode(LoginCode code) {
        codeList.remove(code);
    }
}
