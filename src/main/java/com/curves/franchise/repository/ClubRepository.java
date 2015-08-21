package com.curves.franchise.repository;

import com.curves.franchise.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    Page<Club> findByNameContaining(String name, Pageable pageable);

    @Query("select c from Club c where c.name like %?1% or " +
            "mentor like %?1% or cooperation like %?1%")
    List<Club> findBySearchText(String searchText);

    @Query("select c from Club c where c.name like %?1% or " +
            "mentor like %?1% or cooperation like %?1%")
    List<Club> findBySearchText(String searchText, Sort sort);

    @Query("select c from Club c where c.name like %?1% or " +
            "mentor like %?1% or cooperation like %?1%")
    Page<Club> findBySearchText(String searchText, Pageable pageable);
}
