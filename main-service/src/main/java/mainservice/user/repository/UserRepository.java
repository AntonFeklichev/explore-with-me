package mainservice.user.repository;

import mainservice.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u" +
           "FROM users AS u" +
           "WHERE u.user_id IN ?1" +
           "ORDER BY u.user_id DESC"
    )

    Page<User> findAllByIdIn(List<Long> ids, Pageable pageRequest);//TODO Уточнить по нахванию метода

}
