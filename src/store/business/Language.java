package store.business;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public enum Language {
	
	FRANCAIS("French"), ANGLAIS("English"), ESPAGNOL("Spanish"), ALLEMAND("German"), CHINOIS("Chinese");

	String language; 

	private Language(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return this.language;
	}

}
