package com.aca.parser.repository;

import com.aca.parser.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends CrudRepository<Movie, Long> {
    Movie findByName(String name);
}
