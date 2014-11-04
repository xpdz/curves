package com.curves.franchise.repository;

import com.curves.franchise.domain.Ca;
import org.springframework.data.repository.CrudRepository;

public interface CaRepository extends CrudRepository<Ca, Integer> {
    public Ca findByClubIdAndCaYearAndCaMonth(int clubId, int caYear, int caMonth);
}
