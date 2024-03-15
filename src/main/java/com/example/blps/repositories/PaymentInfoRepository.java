package com.example.blps.repositories;

import com.example.blps.entities.PaymentInfo;
import com.example.blps.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
    Optional<PaymentInfo> findByUser(User user);
}
