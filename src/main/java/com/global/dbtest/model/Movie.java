package com.global.dbtest.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.global.dbtest.Constants;

@Entity
@Table(name = "MOVIES")
@NamedQueries({
		@NamedQuery(name = Constants.FIND_ALL_MOVIES_QUERY, query = "SELECT m FROM Movie m"),
		@NamedQuery(name = Constants.FIND_MOVIES_BY_TITLE_QUERY, query = "SELECT m FROM Movie m WHERE m.title LIKE :title") })
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "RELEASEYEAR")
	private int releaseYear;
	@Enumerated
	private Format format;
	@Transient
	private List<String> stars;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReleaseYear() {
		return this.releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Format getFormat() {
		return this.format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public List<String> getStars() {
		return this.stars;
	}

	public void setStars(List<String> stars) {
		this.stars = stars;
	}

	@Override
	public String toString() {
		return String.format(
				"Id [%s], titile [%s], release year [%d], format [%s]",
				this.id, this.title, this.releaseYear, this.format);
	}

	public enum Format {
		VHS, DVD, BLURAY;

	}

}
