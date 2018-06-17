package com.aca.parser.repository;

import com.aca.parser.domain.MovieSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSessionRepo extends CrudRepository<MovieSession, Long> {
}
