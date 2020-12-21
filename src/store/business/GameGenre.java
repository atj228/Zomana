package store.business;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public enum GameGenre {
	
	ARCADE("Arcade"),ROLE("Role"), MJ("MJ"), EDUCATIONAL("Educational"), STRATEGY("Strategy");

	private String gameGenre;

	private GameGenre(String gameGenre) {
		this.gameGenre = gameGenre;
	}

	public String getGameGenre() {
		return this.gameGenre;
	}
}