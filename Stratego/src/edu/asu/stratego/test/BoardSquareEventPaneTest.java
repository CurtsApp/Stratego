package edu.asu.stratego.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.PieceColor;
import edu.asu.stratego.gui.board.BoardSquareEventPane;

class BoardSquareEventPaneTest {

	

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
	void boundsTest() {
		assert (!BoardSquareEventPane.isInBounds(-1, 4));
		assert (!BoardSquareEventPane.isInBounds(1, 4));
		assert (!BoardSquareEventPane.isInBounds(10, 4));
		assert (!BoardSquareEventPane.isInBounds(4, 10));
		assert (BoardSquareEventPane.isInBounds(5, 8));
	}

	@Test
	void lakeTest() {
		assert (BoardSquareEventPane.isLake(4, 2));
		assert (BoardSquareEventPane.isLake(4, 3));
		assert (BoardSquareEventPane.isLake(4, 6));
		assert (BoardSquareEventPane.isLake(4, 7));
		assert (BoardSquareEventPane.isLake(5, 2));
		assert (BoardSquareEventPane.isLake(5, 3));
		assert (BoardSquareEventPane.isLake(5, 6));
		assert (BoardSquareEventPane.isLake(5, 7));
		assert (!BoardSquareEventPane.isLake(4, 8));
		assert (!BoardSquareEventPane.isLake(5, 8));
		assert (!BoardSquareEventPane.isLake(3, 2));
		assert (!BoardSquareEventPane.isLake(3, 3));
		assert (!BoardSquareEventPane.isLake(3, 6));
		assert (!BoardSquareEventPane.isLake(3, 7));
	}

	@Test
	void setBoardTest() {

		BoardSquareEventPane.randomSetup();
		// Test 4 corners for null peice
		assert (!BoardSquareEventPane.isNullPiece(0, 0));
		assert (!BoardSquareEventPane.isNullPiece(0, 9));
		assert (!BoardSquareEventPane.isNullPiece(9, 0));
		assert (!BoardSquareEventPane.isNullPiece(9, 9));

		// Test where lake is
		if (Game.getOpponent().getColor() == PieceColor.BLUE) {
			assert (BoardSquareEventPane.isOpponentPiece(0, 9));
			assert (!BoardSquareEventPane.isOpponentPiece(9, 0));
			// Lake piece should be false for both colors
			assert (!BoardSquareEventPane.isOpponentPiece(5, 6));
		} else {
			assert (BoardSquareEventPane.isOpponentPiece(0, 9));
			assert (!BoardSquareEventPane.isOpponentPiece(9, 0));
			// Lake piece should be false for both colors
			assert (!BoardSquareEventPane.isOpponentPiece(5, 6));
		}

	}

}
