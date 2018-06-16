package com.aca.parser.service;

import com.aca.parser.domain.Cinema;
import com.aca.parser.repository.CinemaRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class CinemaService {

    @Autowired
    private CinemaRepo cinemaRepo;

    public Cinema getCinemaByName(String cinemaName) {
        return cinemaRepo.findByName(cinemaName);
    }

}