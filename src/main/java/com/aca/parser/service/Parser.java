package com.aca.parser.service;

import com.aca.parser.domain.Cinema;
import com.aca.parser.domain.Movie;
import com.aca.parser.domain.MovieSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Parser {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MovieService movieService;

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void parseTomsarkghData() throws IOException, ParseException {
        sessionsCinemaStarDalma();
        sessionsKinoPark();
    }

    private List<MovieSession> sessionsCinemaStarDalma() throws IOException, ParseException {
        List<MovieSession> movieSessions = new ArrayList<MovieSession>();

        String url = "https://www.tomsarkgh.am/en/venue/393/Cinema-Star.html";
        Document document = Jsoup.connect(url).timeout(20 * 1000).get();
        for (Element row : document.select("table.table-bordered tr")) {
            if (row.getElementsByClass("ocItem").attr("data-attrs").equals(""))
                continue;

            //date
            String dateTime = row.getElementsByClass("ocTime").attr("content");
            if (dateTime.equals(" "))
                continue;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = formatter.parse(dateTime);

            //movieName
            String movieName = row.getElementsByTag("strong").text();

            String dataAttrs = row.getElementsByClass("ocItem").attr("data-attrs");

            //dimension
            String dimension = "";
            if (dataAttrs.endsWith("os")) {
                for (int i = dataAttrs.length() - 9; i < dataAttrs.length(); i++)
                    dimension += dataAttrs.charAt(i);
            } else {
                for (int i = dataAttrs.length() - 2; i < dataAttrs.length(); i++)
                    dimension += dataAttrs.charAt(i);
            }

            //ticket price
            String ticketPriceString = "";
            if (dataAttrs.endsWith("os")) {
                for (int i = dataAttrs.length() - 16; i < dataAttrs.length() - 12; i++)
                    ticketPriceString += dataAttrs.charAt(i);
            } else {
                for (int i = dataAttrs.length() - 9; i < dataAttrs.length() - 5; i++)
                    ticketPriceString += dataAttrs.charAt(i);
            }
            Integer ticketPrice = Integer.parseInt(ticketPriceString);

            //cinema name, hall
            String cinemaNameHall = row.getElementsByTag("i").text();
            String[] split = cinemaNameHall.split("\\(");
            String cinemaName = split[0];
            if (cinemaName.endsWith(" ")) {
                cinemaName = cinemaName.substring(0, cinemaName.length() - 1);
            }
            String cinemaHall = split[1].substring(0, split[1].length() - 1);

            String languageData = dataAttrs.split(",")[1];

            //language
            String language = "";
            if (languageData.equals(" english"))
                language = "ENG";
            else
                language = "RUS";

            Cinema cinema = cinemaService.getCinemaByName(cinemaName);
            Movie movie = movieService.getMovieByName(movieName);

            MovieSession movieSession = new MovieSession();
            movieSession.setCinema(cinema);
            movieSession.setHall(cinemaHall);
            movieSession.setDate(date);
            movieSession.setMovie(movie);
            movieSession.setLanguage(language);
            movieSession.setTicketPrice(ticketPrice);
            movieSession.setDimension(dimension);

            movieSessions.add(movieSession);
        }
        return movieSessions;
    }

    private List<MovieSession> sessionsKinoPark() throws ParseException, IOException {
        List<MovieSession> movieSessions = new ArrayList<MovieSession>();

        String url = "https://www.tomsarkgh.am//en/venue/997/KinoPark.html";
        Document document = Jsoup.connect(url).timeout(20 * 1000).get();
        for (Element row : document.select("table.table-bordered tr")) {
            if (row.getElementsByClass("ocItem").attr("data-attrs").equals(""))
                continue;

            //date
            String dateTime = row.getElementsByClass("ocTime").attr("content");
            if (dateTime.equals(" "))
                continue;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = formatter.parse(dateTime);

            //movieName
            String movieName = row.getElementsByTag("strong").text();

            String dataAttrs = row.getElementsByClass("ocItem").attr("data-attrs");

            //dimension
            String dimension = "";
            if (dataAttrs.split(",")[1].endsWith("atmos"))
                dimension = dataAttrs.substring(0, 9);
            else
                dimension = dataAttrs.substring(0, 2);

            //language
            String language = "";
            if (dataAttrs.endsWith("english, "))
                language = "ENG";
            else
                language = "RUS";

            //ticketPrice
            String ticketPriceString = "";
            if (dataAttrs.endsWith("d, "))
                ticketPriceString = dataAttrs.substring(dataAttrs.length() - 7, dataAttrs.length() - 3);
            else
                ticketPriceString = dataAttrs.substring(dataAttrs.length() - 16, dataAttrs.length() - 12);
            Integer ticketPrice = Integer.parseInt(ticketPriceString);

            //cinema name, hall
            String cinemaNameHall = row.getElementsByTag("i").text();
            String[] split = cinemaNameHall.split("\\(");
            String cinemaName = split[0];
            if (cinemaName.endsWith(" ")) {
                cinemaName = cinemaName.substring(0, cinemaName.length() - 1);
            }
            String cinemaHall = split[1].substring(0, split[1].length() - 1);

            //System.out.println(cinemaName + ", " + cinemaHall);

            Cinema cinema = cinemaService.getCinemaByName(cinemaName);
            Movie movie = movieService.getMovieByName(movieName);

            MovieSession movieSession = new MovieSession();
            movieSession.setCinema(cinema);
            movieSession.setHall(cinemaHall);
            movieSession.setDate(date);
            movieSession.setMovie(movie);
            movieSession.setLanguage(language);
            movieSession.setTicketPrice(ticketPrice);
            movieSession.setDimension(dimension);

            movieSessions.add(movieSession);
        }
        return  movieSessions;
    }

}