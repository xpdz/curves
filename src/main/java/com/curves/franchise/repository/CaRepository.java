package com.curves.franchise.repository;

import com.curves.franchise.domain.Ca;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CaRepository extends CrudRepository<Ca, Integer> {
    public List<Ca> findByClubIdAndYearAndMonth(int clubId, int year, int month);
}
