package com.example.accountbook.repository;

import com.example.accountbook.model.AccountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountCardRepository extends JpaRepository<AccountCard, Long> {

    Optional<AccountCard> findByName(String name);

    Long countBy();
}
