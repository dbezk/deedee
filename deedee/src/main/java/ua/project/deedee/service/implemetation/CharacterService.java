package ua.project.deedee.service.implemetation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.entity.character.CharacterStatistic;
import ua.project.deedee.entity.character.DeeDeeCharacter;
import ua.project.deedee.exception.EntityNotFoundException;
import ua.project.deedee.repository.character.CharacterStatisticRepository;
import ua.project.deedee.repository.character.DeeDeeCharacterRepository;
import ua.project.deedee.service.ICharacterService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CharacterService implements ICharacterService {

    DeeDeeCharacterRepository characterRepository;
    CharacterStatisticRepository statisticRepository;

    @Override
    public DeeDeeCharacter findCharacterById(Long id) {
        return characterRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(
                    String.format("Character with id %s not found", id));
        });
    }

    @Override
    public List<DeeDeeCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    @Override
    public void updateCharacterStatistic(CharacterStatistic statistic,
                                                    Integer power,
                                                    Integer health,
                                                    Integer speed) {
        statistic.setPower(statistic.getPower() +
                (power != null ? power : 0));
        statistic.setHealth(statistic.getHealth() +
                (health != null ? health : 0));
        statistic.setSpeed(statistic.getSpeed() +
                (speed != null ? speed : 0));
        statisticRepository.save(statistic);
    }
}
