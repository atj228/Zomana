package store.test;

import store.business.*;
import junit.framework.*;
import java.util.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/ 

public class GameTest extends TestCase {
	
	private UUID identifier1 = UUID.randomUUID();
	private UUID identifier2 = UUID.randomUUID();

	Game game1 = new Game("gameTest1", 1.0, 1, "imageGame1", identifier1, GameGenre.ARCADE, Platform.NINTENDO);
	Game game2 = new Game("gameTest1", 4.0, 4, "imageGame2", identifier2, GameGenre.MJ, Platform.PLAYSTATION);
	
	public void testGame() {
		
		assertNotSame(game1, game2);
	}

	public void testGetGameGenre() {
		assertEquals("Game genre incorect", GameGenre.ARCADE, game1.getGameGenre());
	}

	public void testGetPlatform() {
		assertEquals("Platform incorect", Platform.NINTENDO, game1.getPlatform());
	}

}

