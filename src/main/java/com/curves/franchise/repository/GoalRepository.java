package com.curves.franchise.repository;

import com.curves.franchise.domain.Goal;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GoalRepository extends PagingAndSortingRepository<Goal, Integer> {
    public Goal findByGYearAndGMonth(int year, int month);
}
