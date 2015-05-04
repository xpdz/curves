package com.curves.franchise.repository;

import com.curves.franchise.domain.Club;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClubRepository extends PagingAndSortingRepository<Club, Integer> {
    public Club findByName(String name);
}
