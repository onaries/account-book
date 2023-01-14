package com.example.accountbook.repository;

import com.example.accountbook.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long>, StatementCustomRepository {

    Long countBy();
}
