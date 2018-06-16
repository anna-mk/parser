package com.aca.parser.service;

import com.aca.parser.domain.Movie;
import com.aca.parser.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    public Movie getMovieByName(String movieName) {
        return movieRepo.findByName(movieName);
    }

}
