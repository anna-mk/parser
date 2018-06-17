package com.aca.parser.service;

import com.aca.parser.domain.Movie;
import com.aca.parser.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    public Movie getMovieByName(String movieName) {
        return movieRepo.findByName(movieName);
    }

}
