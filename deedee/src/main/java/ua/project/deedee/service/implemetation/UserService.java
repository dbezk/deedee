package ua.project.deedee.service.implemetation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.dto.auth.AuthTelegramUserDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.entity.character.CharacterStatistic;
import ua.project.deedee.entity.character.DeeDeeCharacter;
import ua.project.deedee.entity.user.DeeDeeUser;
import ua.project.deedee.entity.user.UserStatistic;
import ua.project.deedee.exception.EntityNotFoundException;
import ua.project.deedee.repository.DeeDeeUserRepository;
import ua.project.deedee.service.IAuthService;
import ua.project.deedee.service.ICharacterService;
import ua.project.deedee.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService, IAuthService {

    ICharacterService characterService;

    DeeDeeUserRepository userRepository;

    ObjectMapper objectMapper;

    Map<Integer, DeeDeeUserPersonalInfoDto> mobileAuthedList;

    @Override
    public Long getUserId() {
        return Long.parseLong(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    @Override
    public List<DeeDeeUser> getUsersByRatingDesc() {
        Pageable pageable = PageRequest.of(0, 3);
        return userRepository.findAll(pageable).stream().toList();
    }

    @Override
    public DeeDeeUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(
                    String.format("User with id %d not found.", id));
        });
        // TODO: OR ELSE NULL ?
    }

    @Override
    public DeeDeeUser getUserByChatId(Long chatId) {
        return userRepository.findByChatId(chatId).orElse(null);
    }

    @Override
    public DeeDeeUserPersonalInfoDto getFullPersonalUserInfo() {
        var user = getUserById(getUserId());
        DeeDeeUserPersonalInfoDto userDataResponse =
                objectMapper.convertValue(user, DeeDeeUserPersonalInfoDto.class);
        userDataResponse.setAccessToken(null);
        return userDataResponse;
    }

    @Override
    public void setUserCharacter(Long characterId) {
        DeeDeeCharacter character =
                characterService.findCharacterById(characterId);
        DeeDeeUser user = getUserById(getUserId());
        user.setDeeDeeCharacter(character);
        user.setCharacterStatistic(
                new CharacterStatistic(character.getPower(),
                        character.getHealth(), character.getSpeed())
        );
        user.setUserStatistic(new UserStatistic());
        user.setChatMessages(new ArrayList<>());
        saveUser(user);
    }

    @Override
    public void setUniqueProduct(Long productId) {
        var user = getUserById(getUserId());
        var product = user.getUniqueProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst().orElse(null);
        if(product != null) {
            user.getCharacterStatistic().setUniqueProduct(product);
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("Item not found in your profile.");
        }
    }

    @Override
    public void unsetUniqueProduct() {
        var user = getUserById(getUserId());
        if(user.getCharacterStatistic().getUniqueProduct() != null) {
            user.getCharacterStatistic().setUniqueProduct(null);
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("You didn't set any attack to unset.");
        }
    }

    @Override
    public void saveUser(DeeDeeUser user) {
        userRepository.save(user);
    }

    @Override
    public DeeDeeUser registerNewUser(AuthTelegramUserDto successLoginDto) {
        var user = getUserByChatId(successLoginDto.getChatId());
        if(user == null) {
            var newUser = DeeDeeUser.builder()
                    .firstName(successLoginDto.getFirstName())
                    .lastName(successLoginDto.getLastName())
                    .chatId(successLoginDto.getChatId())
                    .avatar(successLoginDto.getAvatar())
                    .money(1500)
                    .vipMoney(10)
                    .isBot(false)
                    .build();
            saveUser(newUser);
            return newUser;
        }
        return user;
    }

    @Override
    public DeeDeeUserPersonalInfoDto getUserAuth(Integer authCode) {
        var authData = mobileAuthedList.get(authCode);
        if(authData != null) {
            mobileAuthedList.remove(authCode);
        }
        return authData;
    }
}
