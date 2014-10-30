package com.curves.franchise.repository;

import com.curves.franchise.domain.PjSum;
import org.springframework.data.repository.CrudRepository;

public interface PjSumRepository extends CrudRepository<PjSum, Integer> {
    public PjSum findByClubIdAndYearAndMonth(int clubId, int year, int month);
    public PjSum findById(Long id);
}
