package com.global.dbtest.service;

import java.util.List;

import com.global.dbtest.model.Movie;


public interface MovieStorageService {

	void saveMovie(Movie movie) ;

	void saveMovies(List<Movie> movies) ;

	void deleteMovie(int id) ;

	Movie getMovie(int id);

	List<Movie> getAllMovies() ;

	List<Movie> findMoviesByTitle(String title);

	List<Movie> findMoviesByStar(String name) ;
}
