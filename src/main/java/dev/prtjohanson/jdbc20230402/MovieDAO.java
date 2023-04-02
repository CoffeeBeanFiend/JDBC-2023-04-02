package dev.prtjohanson.jdbc20230402;

import java.util.Iterator;
import java.util.Optional;

public interface MovieDAO {
    void createTable();
    void deleteTable();

    void createMovie(final Movie movie);
    void deleteMovie(int id);
    void updateMoviesTitle(int id, String newTitle);
    Optional<Movie> findMovieById(int id);
    Iterator<Movie> findAll();
}
