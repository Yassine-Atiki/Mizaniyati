package com.mizaniyati.repository;

import com.mizaniyati.entity.Category;
import com.mizaniyati.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCreatedById(Long userId);
}

