package com.example.accountbook.repository;

import com.example.accountbook.model.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {

    List<MainCategory> findAllByOrderByIdAsc();

    Optional<MainCategory> findByName(String name);

    Long countBy();
}
