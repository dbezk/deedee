package ua.project.deedee.service.implemetation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.dto.rating.RatingDto;
import ua.project.deedee.entity.user.UserStatistic;
import ua.project.deedee.repository.UserStatisticRepository;
import ua.project.deedee.service.IUserService;
import ua.project.deedee.service.IUserStatisticService;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RequiredArgsConstructor
public class UserStatisticService implements IUserStatisticService {

    UserStatisticRepository userStatisticRepository;

    IUserService userService;

    @Override
    public List<RatingDto> getRatingList() {
        var user = userService.getUserById(userService.getUserId());
        List<UserStatistic> statisticList = userStatisticRepository.findAllByRatingDesc();
        List<UserStatistic> ratingsList = new ArrayList<>(statisticList.stream()
                .limit(3).toList());
        var userPosition = statisticList.indexOf(user.getUserStatistic());
        ratingsList.add(user.getUserStatistic());
        return ratingsList.stream()
                .map(us -> RatingDto.builder()
                        .firstName(us.getUser().getFirstName())
                        .lastName(us.getUser().getLastName())
                        .avatar(us.getUser().getAvatar())
                        .rating(us.getRating())
                        .ratingPosition(ratingsList.indexOf(us)+1 == ratingsList.size() ?
                            userPosition : ratingsList.indexOf(us)+1)
                        .isRequester((ratingsList.indexOf(us)+1) == ratingsList.size())
                        .build())
                .toList();
    }
}
