package ua.project.deedee.service;


import ua.project.deedee.entity.character.CharacterStatistic;
import ua.project.deedee.entity.character.DeeDeeCharacter;

import java.util.List;

public interface ICharacterService {

    public DeeDeeCharacter findCharacterById(Long id);

    public List<DeeDeeCharacter> getAllCharacters();

    public void updateCharacterStatistic(CharacterStatistic statistic,
                                                    Integer power,
                                                    Integer health,
                                                    Integer speed);

}
