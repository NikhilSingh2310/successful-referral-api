package com.simplifymoney.successfulreferral.repository;
import com.simplifymoney.successfulreferral.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByReferralCode(String referralCode);
    List<User> findByReferrer(User referrer);

}
