package edu.asu.stratego.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.asu.stratego.game.Piece;
import edu.asu.stratego.game.PieceColor;
import edu.asu.stratego.game.PieceType;

class PieceTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void pieceTests() {
		Piece redScout = new Piece(PieceType.SCOUT, PieceColor.RED, false);
		assertEquals(redScout.getPieceSpriteKey(), "RED_02");
		
		Piece blueScout = new Piece(PieceType.SCOUT, PieceColor.BLUE, false);
		assertEquals(blueScout.getPieceSpriteKey(), "BLUE_02");
		
		Piece redScoutOpponent = new Piece(PieceType.SCOUT, PieceColor.RED, true);
		assertEquals(redScoutOpponent.getPieceSpriteKey(), "RED_BACK");
		
		Piece blueScoutOpponent = new Piece(PieceType.SCOUT, PieceColor.BLUE, true);
		assertEquals(blueScoutOpponent.getPieceSpriteKey(), "BLUE_BACK");
		
		Piece redBomb = new Piece(PieceType.BOMB, PieceColor.RED, false);
		assertEquals(redBomb.getPieceSpriteKey(), "RED_BOMB");
		
		Piece blueBomb = new Piece(PieceType.BOMB, PieceColor.BLUE, false);
		assertEquals(blueBomb.getPieceSpriteKey(), "BLUE_BOMB");
		
		Piece redFlag = new Piece(PieceType.FLAG, PieceColor.RED, false);
		assertEquals(redFlag.getPieceSpriteKey(), "RED_FLAG");
		
		Piece blueFlag = new Piece(PieceType.FLAG, PieceColor.BLUE, false);
		assertEquals(blueFlag.getPieceSpriteKey(), "BLUE_FLAG");
		
	}

}
