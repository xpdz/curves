package com.curves.franchise.repository;

import com.curves.franchise.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    public Goal findByGYearAndGMonth(int year, int month);
}
