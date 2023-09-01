package ua.project.deedee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.user.DeeDeeUser;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeeDeeUserRepository extends JpaRepository<DeeDeeUser, Long> {

    Optional<DeeDeeUser> findByChatId(Long chatId);

}
