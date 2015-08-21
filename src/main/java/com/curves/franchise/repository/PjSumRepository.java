package com.curves.franchise.repository;

import com.curves.franchise.domain.PjSum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PjSumRepository extends JpaRepository<PjSum, Integer> {
    PjSum findByClubIdAndYearAndMonth(int clubId, int year, int month);
    List<PjSum> findByClubIdAndYearBetweenAndMonthBetween(int clubId, int yStart, int yEnd, int mStart, int mEnd);
    List<PjSum> findByYearAndMonth(int year, int month);
    List<PjSum> findByYearAndMonth(int year, int month, Sort sort);
}
