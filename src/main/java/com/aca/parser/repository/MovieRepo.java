package com.aca.parser.repository;

import com.aca.parser.domain.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepo extends CrudRepository<Movie, Long> {

    Movie findByName(String name);

}
