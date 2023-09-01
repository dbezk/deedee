package ua.project.deedee.ws.service.implementation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.data.OnlineUserData;
import ua.project.deedee.dto.battle.FullBattleDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.ws.service.IWSService;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WSService implements IWSService {

    SimpMessagingTemplate simpMessagingTemplate;

    List<OnlineUserData> onlineUserDataList;

    @Override
    public void notifyUser(String userId, String message) {
        int index = getUserSessionIndex(userId);
        if(index != -1) {
            simpMessagingTemplate.convertAndSendToUser(onlineUserDataList.get(index).getSessionId(),
                    "/topic/notifications", message);
        }
    }

    @Override
    public void connectBot(String userId) {
        onlineUserDataList.add(OnlineUserData.builder()
                .userId(userId)
                .sessionId(UUID.randomUUID().toString())
                .isBotSession(true).build());
    }

    @Override
    public void connectUser(String userId, String sessionId) {
        onlineUserDataList.add(OnlineUserData.builder()
                .userId(userId)
                .sessionId(sessionId).build());
    }

    @Override
    public void disconnectUser(String userId) {
        List<OnlineUserData> userSessions = onlineUserDataList.stream()
                .filter(d -> d.getUserId().equals(userId))
                .toList();
        onlineUserDataList.removeAll(userSessions);
    }

    @Override
    public int getUserSessionIndex(String userId) {
        return IntStream.range(0, onlineUserDataList.size())
                .filter(i -> onlineUserDataList.get(i).getUserId().equals(userId))
                .findFirst().orElse(-1);
    }

    @Override
    public void sendLoginData(String sessionId,
                              DeeDeeUserPersonalInfoDto userDataResponse) {
        simpMessagingTemplate.convertAndSendToUser(sessionId,
                "/topic/authSuccess", userDataResponse);
    }

    @Override
    public void sendBattleData(FullBattleDto battleDto) {
        simpMessagingTemplate.convertAndSend("/topic/battle/"+battleDto.getBattleId(),
                battleDto);
    }

}
