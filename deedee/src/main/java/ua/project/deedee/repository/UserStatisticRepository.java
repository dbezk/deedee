package ua.project.deedee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.user.UserStatistic;

import java.util.List;

@Repository
public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {

    @Query(value = "SELECT us FROM UserStatistic us ORDER BY us.rating DESC")
    List<UserStatistic> findAllByRatingDesc();

}
