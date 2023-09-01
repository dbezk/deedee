package ua.project.deedee.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.project.deedee.dto.rating.RatingDto;
import ua.project.deedee.service.IUserStatisticService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/rating")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RatingApi {

    IUserStatisticService userStatisticService;

    @GetMapping
    public ResponseEntity<List<RatingDto>> getRatingList() {
        return ResponseEntity.ok(userStatisticService.getRatingList());
    }

}
