package dev.prtjohanson.jdbc20230402;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class MovieResultSetIterator implements Iterator<Movie> {
    private ResultSet resultSet;

    MovieResultSetIterator(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public boolean hasNext() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseActionException(e.getMessage());
        }
    }

    @Override
    public Movie next() {
        return new Movie(this.resultSet);
    }
}
