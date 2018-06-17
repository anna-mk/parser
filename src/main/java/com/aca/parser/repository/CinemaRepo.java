package com.aca.parser.repository;

import com.aca.parser.domain.Cinema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepo extends CrudRepository<Cinema, Long> {
    Cinema findByName(String name);
}
