package com.aca.parser.repository;

import com.aca.parser.domain.Cinema;
import org.springframework.data.repository.CrudRepository;

public interface CinemaRepo extends CrudRepository<Cinema, Long> {

    Cinema findByName(String name);

}
