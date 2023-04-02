package dev.prtjohanson.jdbc20230402;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.stream.Stream;

public class Task2 {
    public enum Action {
        CREATE_TABLE,
        DELETE_TABLE,
        ADD_MOVIE,
        UPDATE_MOVIE,
        DELETE_MOVIE,
        LIST_ALL,
        GET,
        EXIT;
    }

    public static void main(String[] args) {
        final Map<String, Action> commandToAction = new TreeMap<>(Map.of(
                "create table", Action.CREATE_TABLE,
                "delete table", Action.DELETE_TABLE,
                "add movie", Action.ADD_MOVIE,
                "update movie", Action.UPDATE_MOVIE,
                "delete movie", Action.DELETE_MOVIE,
                "list all", Action.LIST_ALL,
                "get", Action.GET,
                "exit", Action.EXIT,
                "quit", Action.EXIT
        ));

        if (args.length < 5) {
            System.out.println(
                    "Please provide mysql server host, port, database name, database user and password."
            );
            System.exit(1);
        }

        final String host = args[0];
        final String port = args[1];
        final String db = args[2];
        final String dbUser = args[3];
        final String dbPass = args[4];

        final String JDBCUrl = "jdbc:mysql://" + host + ":" + port + "/" + db;

        try (
                Connection connection = DriverManager.getConnection(
                        JDBCUrl,
                        dbUser,
                        dbPass
                )
        ) {
            while (true) {
                final MovieDAO movieDAO = new MovieDAOImpl(connection);

                System.out.println("Available commands:");

                commandToAction.keySet().stream().forEach(command -> System.out.println(command));

                try {
                    System.out.println("Enter action: ");
                    final String command = (new Scanner(System.in)).nextLine().toLowerCase();

                    if (!commandToAction.containsKey(command)) {
                        throw new IllegalArgumentException();
                    }

                    final Action userAction = commandToAction.get(command);

                    switch (userAction) {
                        case CREATE_TABLE -> {
                            movieDAO.createTable();
                        }
                        case DELETE_TABLE -> {
                            movieDAO.deleteTable();
                        }
                        case ADD_MOVIE -> {
                            System.out.println("Enter movie title: ");
                            final String title = (new Scanner(System.in)).nextLine();
                            System.out.println("Enter movie genre: ");
                            final String genre = (new Scanner(System.in)).nextLine();
                            System.out.println("Enter movie year of release:");
                            final int yearOfRelease = (new Scanner(System.in)).nextInt();

                            final Movie movie = (new Movie())
                                    .setTitle(title)
                                    .setGenre(genre)
                                    .setYearOfRelease(yearOfRelease);

                            movieDAO.createMovie(movie);
                        }
                        case UPDATE_MOVIE -> {
                            System.out.println("Enter movie id");
                            final int id = (new Scanner(System.in)).nextInt();
                            System.out.println("Enter new movie title:");
                            final String title = (new Scanner(System.in)).nextLine();

                            movieDAO.updateMoviesTitle(id, title);
                        }
                        case LIST_ALL -> {
                            for (Iterator<Movie> it = movieDAO.findAll(); it.hasNext(); ) {
                                Movie movie = it.next();
                                System.out.println(movie);
                            }
                        }
                        case DELETE_MOVIE -> {
                            System.out.println("Enter id of movie to delete:");
                            final int id = (new Scanner(System.in)).nextInt();
                            movieDAO.deleteMovie(id);
                        }
                        case GET -> {
                            System.out.println("Enter id of movie to get:");
                            final int id = (new Scanner(System.in)).nextInt();
                            Optional<Movie> optMovie = movieDAO.findMovieById(id);
                            if (optMovie.isPresent()) {
                                System.out.println(optMovie.get());
                            } else {
                                System.out.println("Movie with id " + id + " not found.");
                            }
                        }
                        case EXIT -> {
                            System.out.println("Bye!");
                            System.exit(0);
                        }
                    }

                    System.out.println("Action completed!");
                    System.out.println();
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input, try again!");
                    System.out.println("");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
