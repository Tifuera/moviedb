package com.global.dbtest.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.global.dbtest.Constants;
import com.global.dbtest.model.Movie;
import com.global.dbtest.service.MovieStorageService;

public class MovieStorageServiceImpl implements MovieStorageService {

	private EntityManager em;

	public MovieStorageServiceImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public void saveMovie(Movie movie) {
		EntityTransaction transaction = this.em.getTransaction();

		transaction.begin();
		this.em.merge(movie);
		transaction.commit();
	}

	@Override
	public void saveMovies(List<Movie> movies) {
		EntityTransaction transaction = this.em.getTransaction();

		transaction.begin();

		for (Movie movie : movies) {
			this.em.merge(movie);
		}

		transaction.commit();
	}

	@Override
	public void deleteMovie(int id) {
		Movie movie = this.em.find(Movie.class, id);

		EntityTransaction transaction = this.em.getTransaction();

		transaction.begin();
		this.em.remove(movie);
		transaction.commit();
	}

	@Override
	public Movie getMovie(int id) {
		return this.em.find(Movie.class, id);
	}

	@Override
	public List<Movie> getAllMovies() {
		return this.em.createNamedQuery(Constants.FIND_ALL_MOVIES_QUERY,
				Movie.class).getResultList();
	}

	@Override
	public List<Movie> findMoviesByTitle(String title) {
		return this.em
				.createNamedQuery(Constants.FIND_MOVIES_BY_TITLE_QUERY,
						Movie.class).setParameter("title", "%" + title + "%")
				.getResultList();
	}

	@Override
	public List<Movie> findMoviesByStar(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
