package com.example.accountbook.repository;

import com.example.accountbook.model.Statement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long>, StatementCustomRepository {

    Long countBy();

    @Query(value = "SELECT s FROM Statement s WHERE s.category.id IN (SELECT c.id FROM Category c WHERE c.type = ?1)")
    Page<Statement> findByCategoryId(Pageable pageable, int type);


    @Query(value = "SELECT s FROM Statement s WHERE s.date BETWEEN ?1 AND ?2")
    Page<Statement> findByDate(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT s FROM Statement s WHERE s.category.id IN (SELECT c.id FROM Category c WHERE c.type = ?1) AND s.date BETWEEN ?2 AND ?3")
    Page<Statement> findByCategoryIdAndDateBetween(Pageable pageable, int type, LocalDateTime startDate, LocalDateTime endDate);
}
