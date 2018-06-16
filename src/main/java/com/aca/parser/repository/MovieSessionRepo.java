package com.aca.parser.repository;

import com.aca.parser.domain.MovieSession;
import org.springframework.data.repository.CrudRepository;

public interface MovieSessionRepo extends CrudRepository<MovieSession, Long> {
}
