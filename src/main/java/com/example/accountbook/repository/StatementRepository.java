package com.example.accountbook.repository;

import com.example.accountbook.model.Statement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long>, StatementCustomRepository {

    Long countBy();

    @Query(value = "SELECT s FROM Statement s WHERE s.category.id IN (SELECT c.id FROM Category c WHERE c.type = ?1)")
    Page<Statement> findByCategoryId(Pageable pageable, int type);
}
