package com.curves.franchise.repository;

import com.curves.franchise.domain.Ca;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CaRepository extends CrudRepository<Ca, Integer> {
    public Ca findByClubIdAndCaYearAndCaMonth(int clubId, int caYear, int caMonth);
    public List<Ca> findByClubIdAndCaYearBetweenAndCaMonthBetween(int clubId, int yStart, int yEnd, int mStart, int mEnd);
    public List<Ca> findByCaYearAndCaMonth(int caYear, int caMonth);
}
