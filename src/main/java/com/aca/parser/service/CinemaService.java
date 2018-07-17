package com.aca.parser.service;

import com.aca.parser.domain.Cinema;
import com.aca.parser.repository.CinemaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepo cinemaRepo;

    public CinemaRepo getCinemaRepo() {
        return cinemaRepo;
    }

    public void setCinemaRepo(CinemaRepo cinemaRepo) {
        this.cinemaRepo = cinemaRepo;
    }

    public Cinema getCinemaByName(String cinemaName) {
        return cinemaRepo.findByName(cinemaName);
    }

}
