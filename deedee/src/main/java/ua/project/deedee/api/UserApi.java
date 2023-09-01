package ua.project.deedee.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserApi {

    IUserService userService;

    @GetMapping
    public ResponseEntity<DeeDeeUserPersonalInfoDto> getUserInfo() {
        return ResponseEntity.ok(userService.getFullPersonalUserInfo());
    }

    @PutMapping(value = "/setCharacter/{id}")
    public ResponseEntity<?> choosePlayer(@PathVariable(value = "id") Long id) {
        userService.setUserCharacter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/setUniqueProduct/{id}")
    public ResponseEntity<?> setUniqueAttack(@PathVariable(value = "id") Long id) {
        userService.setUniqueProduct(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/unsetUniqueProduct")
    public ResponseEntity<?> unsetUniqueAttack() {
        userService.unsetUniqueProduct();
        return ResponseEntity.ok().build();
    }

}
