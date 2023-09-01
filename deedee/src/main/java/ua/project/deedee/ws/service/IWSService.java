package ua.project.deedee.ws.service;

import ua.project.deedee.data.OnlineUserData;
import ua.project.deedee.dto.battle.FullBattleDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;

import java.util.List;

public interface IWSService {

    public void notifyUser(String userId, String message);

    public void connectBot(String userId);
    public void connectUser(String userId, String sessionId);

    public void disconnectUser(String userId);

    public int getUserSessionIndex(String userId);

    public void sendLoginData(String sessionId,
                              DeeDeeUserPersonalInfoDto userDataResponse);

    public void sendBattleData(FullBattleDto battleDto);

}
