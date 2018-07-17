package com.aca.parser.service;

import com.aca.parser.repository.MovieSessionRepo;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private MovieSessionRepo movieSessionRepo;

    public MovieSessionRepo getMovieSessionRepo() {
        return movieSessionRepo;
    }

    public void setMovieSessionRepo(MovieSessionRepo movieSessionRepo) {
        this.movieSessionRepo = movieSessionRepo;
    }
}
