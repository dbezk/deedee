package ua.project.deedee.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.project.deedee.entity.character.DeeDeeCharacter;
import ua.project.deedee.service.ICharacterService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/character")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CharacterApi {

    ICharacterService characterService;

    @GetMapping
    public ResponseEntity<List<DeeDeeCharacter>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getAllCharacters());
    }

}
