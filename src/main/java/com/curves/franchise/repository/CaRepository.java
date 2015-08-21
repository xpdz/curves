package com.curves.franchise.repository;

import com.curves.franchise.domain.Ca;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaRepository extends JpaRepository<Ca, Integer> {
    Ca findByClubIdAndCaYearAndCaMonth(int clubId, int caYear, int caMonth);
    List<Ca> findByClubIdAndCaYearBetweenAndCaMonthBetween(int clubId, int yStart, int yEnd, int mStart, int mEnd);
    List<Ca> findByCaYearAndCaMonth(int caYear, int caMonth);
    List<Ca> findByCaYearAndCaMonth(int caYear, int caMonth, Sort sort);
}
