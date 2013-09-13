package com.global.dbtest.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

import com.global.dbtest.load.XmlMovieLoader;
import com.global.dbtest.model.Movie;
import com.global.dbtest.model.Movie.Format;
import com.global.dbtest.model.MovieStar;
import com.global.dbtest.service.MovieStorageService;


public class MoviesCommandProvider implements CommandProvider {

	private MovieStorageService movieStorageService;

	public MoviesCommandProvider(MovieStorageService movieStorageService) {
		this.movieStorageService = movieStorageService;
	}

	@Override
	public String getHelp() {
		return "Commands required for movie db test";
	}

	public void _listbytitle(CommandInterpreter ci) {
		List<Movie> allMovies = this.movieStorageService.getAllMovies();
		Collections.sort(allMovies, new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});

		printMovies(ci, allMovies);
	}

	public void _listbyyear(CommandInterpreter ci) {
		List<Movie> allMovies = this.movieStorageService.getAllMovies();
		Collections.sort(allMovies, new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				return o1.getReleaseYear() - o2.getReleaseYear();
			}
		});

		printMovies(ci, allMovies);
	}

	public void _deletemovie(CommandInterpreter ci) {
		ci.println("Enter movie id");
		Scanner scanner = new Scanner(System.in);
		int id = scanner.nextInt();
		this.movieStorageService.deleteMovie(id);

	}

	public void _addmovie(CommandInterpreter ci) {
		Movie movie = new Movie();
		Scanner scanner = new Scanner(System.in);

		ci.println("Enter movie title");
		movie.setTitle(scanner.nextLine());

		ci.println("Enter release year");
		movie.setReleaseYear(Integer.parseInt(scanner.nextLine()));

		ci.println("Enter format");
		movie.setFormat(resolveFormat(scanner.nextLine()));

		ci.println("Enter stars number");
		int number = Integer.parseInt(scanner.nextLine()) ;
		List<MovieStar> stars = new LinkedList<MovieStar>();
		while (number > 0) {
			ci.println("Enter next star name");
			MovieStar currentStar = new MovieStar();
			currentStar.setName(scanner.nextLine());
			number--;
		}
		movie.setStars(stars);

		this.movieStorageService.saveMovie(movie);
	}

	public void _displaymovie(CommandInterpreter ci) {
		ci.println("Enter movie id");
		Scanner scanner = new Scanner(System.in);

		Movie movie = this.movieStorageService.getMovie(scanner.nextInt());
		ci.println(movie);
	}

	public void _loadmovies(CommandInterpreter ci) {
		ci.println("Enter file path");
		Scanner scanner = new Scanner(System.in);
		String filePath = scanner.nextLine();

		XmlMovieLoader loader = new XmlMovieLoader(filePath);
		this.movieStorageService.saveMovies(loader.parseDocument());
	}

	public void _findbytitle(CommandInterpreter ci) {
		ci.println("Enter title");
		Scanner scanner = new Scanner(System.in);

		String title = scanner.nextLine();
		printMovies(ci, this.movieStorageService.findMoviesByTitle(title));
	}

	public void _findbystar(CommandInterpreter ci) {
		ci.println("Enter star name");
		Scanner scanner = new Scanner(System.in);

		String name = scanner.nextLine();
		printMovies(ci, this.movieStorageService.findMoviesByStar(name));
	}

	private void printMovies(CommandInterpreter ci, List<Movie> allMovies) {
		for (Movie movie : allMovies) {
			ci.println(String.format("Movie title [%s], identifier [%d]",
					movie.getTitle(), movie.getId()));
		}
	}

	private Movie.Format resolveFormat(String name) {
		if ("VHS".equals(name)) {
			return Format.VHS;
		} else if ("DVD".equals(name)) {
			return Format.DVD;
		} else {
			return Format.BLURAY;
		}
	}
}
