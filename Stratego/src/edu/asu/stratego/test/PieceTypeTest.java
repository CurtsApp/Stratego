package edu.asu.stratego.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.asu.stratego.game.BattleOutcome;
import edu.asu.stratego.game.Piece;
import edu.asu.stratego.game.PieceColor;
import edu.asu.stratego.game.PieceType;

public class PieceTypeTest {

	Piece redSpy, blueSpy, bScout, redScout, blueSarge, redSarge, blueLiuet, redLieut, blueCapt, blueMajor, redMajor,
			blueCol, redCol, blueGeneral, redGeneral, blueMarsh, redMarsh, blueMiner, redMiner, blueBomb, redBomb;

	BattleOutcome result;

	@Before
	public void setUp() throws Exception {
		redSpy = new Piece(PieceType.SPY, PieceColor.RED, false);
		blueSpy = new Piece(PieceType.SPY, PieceColor.BLUE, true);

		bScout = new Piece(PieceType.SCOUT, PieceColor.BLUE, true);
		blueMiner = new Piece(PieceType.MINER, PieceColor.BLUE, true);
		blueSarge = new Piece(PieceType.SERGEANT, PieceColor.BLUE, true);
		blueLiuet = new Piece(PieceType.LIEUTENANT, PieceColor.BLUE, true);
		blueCapt = new Piece(PieceType.CAPTAIN, PieceColor.BLUE, true);
		blueMajor = new Piece(PieceType.MAJOR, PieceColor.BLUE, true);
		blueCol = new Piece(PieceType.COLONEL, PieceColor.BLUE, true);
		blueGeneral = new Piece(PieceType.GENERAL, PieceColor.BLUE, true);
		blueMarsh = new Piece(PieceType.MARSHAL, PieceColor.BLUE, true);
		blueBomb = new Piece(PieceType.BOMB, PieceColor.BLUE, true);

		redScout = new Piece(PieceType.SCOUT, PieceColor.RED, true);
		redMiner = new Piece(PieceType.MINER, PieceColor.RED, true);
		redSarge = new Piece(PieceType.SERGEANT, PieceColor.RED, true);
		redLieut = new Piece(PieceType.LIEUTENANT, PieceColor.RED, true);
		blueCapt = new Piece(PieceType.CAPTAIN, PieceColor.RED, true);
		redMajor = new Piece(PieceType.MAJOR, PieceColor.RED, true);
		redCol = new Piece(PieceType.COLONEL, PieceColor.RED, true);
		redGeneral = new Piece(PieceType.GENERAL, PieceColor.RED, true);
		redMarsh = new Piece(PieceType.MARSHAL, PieceColor.RED, true);
		redBomb = new Piece(PieceType.BOMB, PieceColor.RED, true);

		// Create Bomb piece
		// bomb = new Piece(PieceType.BOMB, PieceColor.BLUE, true);
	}

	@After
	public void ValNull() throws Exception {
		redSpy = null;
		blueSpy = null;
		blueMiner = null;
		blueSarge = null;
		blueLiuet = null;
		blueCapt = null;
		blueMajor = null;
		blueCol = null;
		blueGeneral = null;
		blueMarsh = null;
		blueBomb = null;
		redScout = null;
		redMiner = null;
		redSarge = null;
		redLieut = null;
		blueCapt = null;
		redMajor = null;
		redCol = null;
		redGeneral = null;
		redMarsh = null;
		redBomb = null;

	}

	@Test
	public void test1() {
		// Bomb
		result = redMiner.getPieceType().attack(redBomb.getPieceType());
		assertTrue(result == BattleOutcome.WIN);

		// Testing for Blue Spy attacking a red piece. Spy should lose

		result = redSpy.getPieceType().attack(blueSpy.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = redSpy.getPieceType().attack(bScout.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = redSpy.getPieceType().attack(blueMiner.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = redSpy.getPieceType().attack(blueSarge.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = redSpy.getPieceType().attack(blueLiuet.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = redSpy.getPieceType().attack(blueCapt.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = redSpy.getPieceType().attack(blueMajor.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		// Testing for Red Spy to attack a blue piece. Spy should lose.

		result = blueSpy.getPieceType().attack(redSpy.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = blueSpy.getPieceType().attack(redScout.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = blueSpy.getPieceType().attack(redMiner.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = blueSpy.getPieceType().attack(redSarge.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = blueSpy.getPieceType().attack(redLieut.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = blueSpy.getPieceType().attack(blueCapt.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = blueSpy.getPieceType().attack(redMajor.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = blueSpy.getPieceType().attack(redCol.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		result = blueSpy.getPieceType().attack(redBomb.getPieceType());
		assertFalse(result == BattleOutcome.WIN);

		// Spy attacking the Marshal. Spy should win.

		result = blueSpy.getPieceType().attack(redMarsh.getPieceType());
		assertTrue(result == BattleOutcome.WIN);

		result = redSpy.getPieceType().attack(blueMarsh.getPieceType());
		assertTrue(result == BattleOutcome.WIN);

	}

}