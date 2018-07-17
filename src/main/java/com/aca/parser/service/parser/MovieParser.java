package com.aca.parser.service.parser;

import com.aca.parser.domain.Movie;
import com.aca.parser.service.MovieService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieParser {

    @Autowired
    private MovieService movieService;

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void parseKinopoisk() throws IOException {
        movies("https://www.tomsarkgh.am/en/venue/393/Cinema-Star.html");
        movies("https://www.tomsarkgh.am/en/venue/997/KinoPark.html");
    }

    private List<Movie> movies(String cinemaUrl) throws IOException {
        List<Movie> movieList = new ArrayList<Movie>();
        List<String> movies = new ArrayList<String>();

        Document doc = Jsoup.connect(cinemaUrl).timeout(20 * 1000).get();
        for (Element row : doc.select("table.table-bordered tr")) {
            if (row.getElementsByClass("ocItem").attr("data-attrs").equals(""))
                continue;

            //movieName
            String movieName = row.getElementsByTag("strong").text();
            if (movies.contains(movieName) || movieService.getMovieRepo().findByName(movieName) != null)
                continue;
            movies.add(movieName);
        }

        for (String movie : movies) {
            Document search = Jsoup.connect("https://www.kinopoisk.ru/index.php?kp_query=" + movie).get();
            String movieUrl = search.getElementsByClass("search_results").first().getElementsByClass("info").first().getElementsByTag("a").attr("href");

            Document document = Jsoup.connect("https://www.kinopoisk.ru" + movieUrl).get();
            String movieName = document.getElementById("headerFilm").select("h1").text();
            String director = document.select("table.info tbody tr td[itemprop=director]").text();
            String writers = document.select("table.info tbody tr").get(4).select("td").get(1).text().replace(", ...", "");
            String stars = document.select("div#actorList ul").get(0).text().replace(" ...", "");
            String released = document.select("table.info tbody tr").get(0).select("td").get(1).text();
            String description = document.select("span[class=_reachbanner_] div[class=brand_words film-synopsys]").text();
            String genre = document.select("span[itemprop=genre]").text().replace(", ...", "");
            String duration = document.select("td#runtime").text();
            if (duration.contains("/"))
                duration = duration.substring(0, 7);
            String posterUrl = document.getElementById("photoBlock").select("a").get(1).select("img").attr("src");
            String trailerUrl = document.select("meta[property=og:video:url]").attr("content");

            Movie film = new Movie(movieName, director, writers, stars, released, description, genre, duration, posterUrl, trailerUrl);
            movieService.getMovieRepo().save(film);
            movieList.add(film);
        }
        return  movieList;
    }

}
