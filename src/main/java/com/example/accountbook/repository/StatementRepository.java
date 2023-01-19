package com.example.accountbook.repository;

import com.example.accountbook.model.Statement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long>, StatementCustomRepository {

    Long countBy();

    @Query(value = "SELECT s FROM Statement s WHERE (:type IS NULL OR s.category.id IN (SELECT c.id FROM Category c WHERE c.type = :type)) AND (:startDate IS NULL OR s.date >= :startDate) AND (:endDate IS NULL OR s.date <= :endDate)")
    Page<Statement> findByCategoryIdAndDateBetween(Pageable pageable, @Param("type") Integer type, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
