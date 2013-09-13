package com.global.dbtest.load;

import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.global.dbtest.model.Movie;
import com.global.dbtest.model.Movie.Format;
import com.global.dbtest.model.MovieStar;


public class XmlMovieLoader extends DefaultHandler {

	private String fileName;
	private String tmpValue;
	private Movie currentMovie;
	private List<MovieStar> stars;
	private List<Movie> movies;

	public XmlMovieLoader(String fileName) {
		this.fileName = fileName;
		this.movies = new LinkedList<Movie>();
	}

	public List<Movie> parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(this.fileName, this);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.movies;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("movie".equalsIgnoreCase(qName)) {
			this.currentMovie = new Movie();
			// this.currentMovie.setId(this.counter++);
		}

		if ("stars".equalsIgnoreCase(qName)) {
			this.stars = new LinkedList<MovieStar>();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		switch (qName) {
		case "title":
			this.currentMovie.setTitle(this.tmpValue);
			break;
		case "release_year":
			this.currentMovie.setReleaseYear(Integer.parseInt(this.tmpValue));
			break;
		case "format":
			try {
				this.currentMovie.setFormat(resolveFormat(this.tmpValue));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "star":
			MovieStar tmpStar = new MovieStar();
			tmpStar.setName(this.tmpValue);
			this.stars.add(tmpStar);
			break;
		case "stars":
			this.currentMovie.setStars(this.stars);
			break;
		case "movie":
			this.movies.add(this.currentMovie);
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.tmpValue = new String(ch, start, length);

	}

	private Movie.Format resolveFormat(String name) throws Exception {
		if ("VHS".equals(name)) {
			return Format.VHS;
		} else if ("DVD".equals(name)) {
			return Format.DVD;
		} else if ("Blu-Ray".equals(name)) {
			return Format.BLURAY;
		} else {
			throw new Exception("Unknow Format");
		}
	}
}
