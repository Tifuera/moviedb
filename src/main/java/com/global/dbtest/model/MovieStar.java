package com.global.dbtest.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "STARS")
public class MovieStar {


	@Id
	@Column
	private String name;

	@ManyToMany(mappedBy = "stars")
	private List<Movie> movies;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
