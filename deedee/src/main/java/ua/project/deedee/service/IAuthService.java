package ua.project.deedee.service;

import ua.project.deedee.dto.auth.AuthTelegramUserDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.entity.user.DeeDeeUser;

public interface IAuthService {

    public DeeDeeUser registerNewUser(AuthTelegramUserDto successLoginDto);

    public DeeDeeUserPersonalInfoDto getUserAuth(Integer authCode);

}
