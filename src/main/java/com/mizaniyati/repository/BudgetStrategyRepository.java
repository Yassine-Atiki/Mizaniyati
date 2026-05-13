package com.mizaniyati.repository;

import com.mizaniyati.entity.BudgetStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetStrategyRepository extends JpaRepository<BudgetStrategy, Long> {
    List<BudgetStrategy> findByUserId(Long userId);
    Optional<BudgetStrategy> findByIdAndUserId(Long id, Long userId);
}

