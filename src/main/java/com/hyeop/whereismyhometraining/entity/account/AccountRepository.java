package com.hyeop.whereismyhometraining.entity.account;

import com.hyeop.whereismyhometraining.entity.enums.Sns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndSns(String username, Sns sns);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndSns(String username, Sns sns);

    boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndUsername(String email, String username);
}
