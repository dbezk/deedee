package ua.project.deedee.service;

import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.entity.user.DeeDeeUser;

import java.util.List;

public interface IUserService {

    public Long getUserId();

    public List<DeeDeeUser> getUsersByRatingDesc();

    public DeeDeeUser getUserById(Long id);

    public DeeDeeUser getUserByChatId(Long chatId);

    public DeeDeeUserPersonalInfoDto getFullPersonalUserInfo();

    public void setUserCharacter(Long characterId);

    public void setUniqueProduct(Long productId);

    public void unsetUniqueProduct();

    public void saveUser(DeeDeeUser user);

}
