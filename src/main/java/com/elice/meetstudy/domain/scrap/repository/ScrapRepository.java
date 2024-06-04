package com.elice.meetstudy.domain.scrap.repository;

import com.elice.meetstudy.domain.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    List<Scrap> findScrapsByUserId(Long userId);
}
