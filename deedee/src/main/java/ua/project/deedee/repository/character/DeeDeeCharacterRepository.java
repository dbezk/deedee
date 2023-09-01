package ua.project.deedee.repository.character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.character.DeeDeeCharacter;

@Repository
public interface DeeDeeCharacterRepository extends JpaRepository<DeeDeeCharacter, Long> {
}
