package dev.prtjohanson.jdbc20230402;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {
    private int id, yearOfRelease;
    private String title, genre;

    public Movie() {

    }
     
    public Movie(ResultSet rs) {
        try {
            this
                    .setId(rs.getInt("id"))
                    .setYearOfRelease(rs.getInt("yearOfRelease"))
                    .setTitle(rs.getString("title"))
                    .setGenre(rs.getString("genre"));
        } catch (SQLException e) {
            throw new DatabaseActionException(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public Movie setId(int id) {
        this.id = id;
        return this;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public Movie setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Movie setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", yearOfRelease=" + yearOfRelease +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
