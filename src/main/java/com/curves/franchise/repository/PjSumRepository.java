package com.curves.franchise.repository;

import com.curves.franchise.domain.PjSum;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PjSumRepository extends CrudRepository<PjSum, Integer> {
    public PjSum findByClubIdAndYearAndMonth(int clubId, int year, int month);
    public List<PjSum> findByClubIdAndYearBetweenAndMonthBetween(int clubId, int yStart, int yEnd, int mStart, int mEnd);
    public List<PjSum> findByYearAndMonth(int year, int month, Sort sort);
}
