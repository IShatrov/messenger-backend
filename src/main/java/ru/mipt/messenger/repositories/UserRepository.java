package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import ru.mipt.messenger.models.User;
import ru.mipt.messenger.dto.UserDto;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUsersByFirstname(String firstname);

    Optional<User> findUserByNickname(String nickname);

    Optional<User> findUserByUserId(Integer id);

    @Query("""
        SELECT new ru.mipt.messenger.dto.UserDto(u)
        FROM User u
        WHERE LOWER(u.nickname) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(u.firstname) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(u.secondname) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY
          CASE
            WHEN LOWER(u.nickname) LIKE LOWER(CONCAT('%', :query, '%')) THEN 1
            WHEN LOWER(u.firstname) LIKE LOWER(CONCAT('%', :query, '%')) THEN 2
            WHEN LOWER(u.secondname) LIKE LOWER(CONCAT('%', :query, '%')) THEN 3
            ELSE 4
          END,
          u.nickname, u.firstname, u.secondname
    """)
    List<UserDto> searchUsersByQuery(@Param("query") String query);
}
