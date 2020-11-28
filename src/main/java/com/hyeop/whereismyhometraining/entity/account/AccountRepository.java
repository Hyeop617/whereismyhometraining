package com.hyeop.whereismyhometraining.entity.account;

import com.hyeop.whereismyhometraining.entity.enums.Sns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    @Query("select a from Account a left join fetch a.userCourse")
    List<Account> findAllJoinFetch();

    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndSns(String username, Sns sns);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndSns(String username, Sns sns);

    boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndUsername(String email, String username);
}
