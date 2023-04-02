package dev.prtjohanson.jdbc20230402;

import java.sql.*;
import java.util.Iterator;
import java.util.Optional;

public class MovieDAOImpl implements MovieDAO {
    private Connection connection;

    private final String createTableQuery =
            "CREATE TABLE IF NOT EXISTS movies (" +
            "id int NOT NULL AUTO_INCREMENT," +
            "title VARCHAR(255), genre VARCHAR(255)," +
            "yearOfRelease int," +
            "PRIMARY KEY (id)" +
            ");";

    private final String dropTableQuery = "DROP TABLE movies;";

    private final String createQuery = "INSERT INTO movies (title, genre, yearOfRelease) VALUES (?, ?, ?)";
    private final int createMovieTitleIdx = 1;
    private final int createMovieGenreIdx = 2;
    private final int createMovieYearOfReleaseIdx = 3;

    private final String deleteQuery = "DELETE FROM movies WHERE id = ?";
    private final int deleteQueryIdIdx = 1;

    private final String updateTitleQuery = "UPDATE movies SET title = ? WHERE id = ?";
    private final int updateTitleQueryTitleIdx = 1;
    private final int updateTitleQueryIdIdx = 2;

    private final String selectByIdQuery = "SELECT * FROM movies WHERE id = ?";
    private final int selectByIdQueryIdIdx = 1;

    private final String selectAllQuery = "SELECT * FROM movies";

    public MovieDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        this.runQuery(createTableQuery);
    }

    @Override
    public void deleteTable() {
        this.runQuery(dropTableQuery);
    }

    @Override
    public void createMovie(Movie movie) {
        try {
            PreparedStatement st = connection.prepareStatement(createQuery);
            st.setString(this.createMovieTitleIdx, movie.getTitle());
            st.setString(this.createMovieGenreIdx, movie.getGenre());
            st.setInt(this.createMovieYearOfReleaseIdx, movie.getYearOfRelease());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseActionException(e.getMessage());
        }
    }

    @Override
    public void deleteMovie(int id) {
        try {
            PreparedStatement st = connection.prepareStatement(deleteQuery);
            st.setInt(this.deleteQueryIdIdx, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseActionException(e.getMessage());
        }
    }

    @Override
    public void updateMoviesTitle(int id, String newTitle) {
        try {
            PreparedStatement st = connection.prepareStatement(updateTitleQuery);
            st.setInt(this.updateTitleQueryIdIdx, id);
            st.setString(this.updateTitleQueryTitleIdx, newTitle);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseActionException(e.getMessage());
        }
    }

    @Override
    public Optional<Movie> findMovieById(int id) {
        try {
            PreparedStatement st = connection.prepareStatement(selectByIdQuery);
            st.setInt(selectByIdQueryIdIdx, id);
            ResultSet r = st.executeQuery();

            if (r.next()) {
                return Optional.of(new Movie(r));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DatabaseActionException(e.getMessage());
        }
    }

    @Override
    public Iterator<Movie> findAll() {
        try {
            PreparedStatement st = connection.prepareStatement(selectAllQuery);
            return new MovieResultSetIterator(st.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseActionException(e.getMessage());
        }
    }

    private void runQuery(String query) {
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            throw new DatabaseActionException(e.getMessage());
        }
    }
}
