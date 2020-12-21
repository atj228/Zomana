package store.business;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public enum Platform {
	
	NINTENDO("Nintendo"), PC("PC"), PLAYSTATION("Playstation"), XBOX("Xbox"); 

	private String platform;

	private Platform(String platform) {
		this.platform = platform;
	}

	public String getPlatform() {
		return this.platform;
	}

}
