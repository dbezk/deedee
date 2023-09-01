package ua.project.deedee.service.implemetation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.service.IRatingService;
import ua.project.deedee.service.IUserService;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RatingService implements IRatingService {

    IUserService userService;

    @Override
    public void getRatingsList() {
//        var requester = userService.getUserById(userService.getUserId());

    }

}
