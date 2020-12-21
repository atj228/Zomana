package store.business;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public enum Genre {
	
	ACTION("Action"), ADVENTURE("Adventure"), ANIMATION("Animation"), COMEDY("Comedy"), 
	DRAMA("Drama"), HORROR("Horror"), SCIFI("Scifi"), THRILLER("Thriller"), ROMANCE("Romance");
	
	private String genre;

	Genre(String genre) {
		this.genre = genre;
	}

	public String getGenre() {
		return this.genre;
	}

}
