package ua.project.deedee.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MobileAuthApi {

    Map<Integer, DeeDeeUserPersonalInfoDto> mobileAuthedList;

    @GetMapping(value = "/{authCode}")
    public ResponseEntity<DeeDeeUserPersonalInfoDto> getUserAuth(
            @PathVariable(value = "authCode") Integer authCode
    ) {
        log.info("request with code = {}, map = {}", authCode, mobileAuthedList);
        return ResponseEntity.ok(mobileAuthedList.get(authCode));
    }
}
