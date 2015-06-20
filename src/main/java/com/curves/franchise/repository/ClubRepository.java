package com.curves.franchise.repository;

import com.curves.franchise.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    Club findByName(String name);
}
