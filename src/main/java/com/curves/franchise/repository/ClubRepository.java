package com.curves.franchise.repository;

import com.curves.franchise.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    Page<Club> findByNameContaining(String name, Pageable pageable);
}
