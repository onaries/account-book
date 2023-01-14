package com.example.accountbook.repository;

import com.example.accountbook.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByIdAsc(Pageable pageable);

    Optional<Category> findByName(String name);

    Long countBy();
}

