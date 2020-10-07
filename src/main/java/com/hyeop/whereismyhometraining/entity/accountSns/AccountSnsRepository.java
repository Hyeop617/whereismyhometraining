package com.hyeop.whereismyhometraining.entity.accountSns;

import com.hyeop.whereismyhometraining.entity.enums.Sns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountSnsRepository extends JpaRepository<AccountSns, Long> {

    boolean existsBySnsAndUsername(Sns sns, String username);

    Optional<AccountSns> findBySnsAndUsername(Sns sns, String username);

    Optional<AccountSns> findByUsername(String username);
}
