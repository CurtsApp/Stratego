package edu.asu.stratego.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Point;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.asu.stratego.game.Move;
import edu.asu.stratego.game.PieceColor;

class MoveTest {

	static Move move;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		move = new Move();
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
	void test() {
		// Default start point is (-1, -1)
		Point start = move.getStart();
		assertEquals(-1, start.getX());
		assertEquals(-1, start.getY());
		
		// Default end point is (-1, -1)
		Point end = move.getEnd();
		assertEquals(-1, end.getX());
		assertEquals(-1, end.getY());
		
		move.setAttackMove(true);
		assert(move.isAttackMove());
		move.setAttackMove(false);
		assert(!move.isAttackMove());
		
		// No piece has been selected yet
		assert(!move.isPieceSelected());
		
		move.setStart(2,3);
		start = move.getStart();
		
		assertEquals(2, start.getX());
		assertEquals(3, start.getY());
		
		assert(move.isPieceSelected());
		
		assertEquals(2, move.getRowStart());
		assertEquals(3, move.getColStart());
		
		assertEquals(null, move.getMoveColor());
		
		move.setMoveColor(PieceColor.BLUE);
		assertEquals(PieceColor.BLUE, move.getMoveColor());
	}

}
