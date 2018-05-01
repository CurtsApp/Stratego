package edu.asu.stratego.game;

public class MoveSet {
	public Move playerOneMove;
	public Move playerTwoMove;
	
	MoveSet() {
		playerOneMove = new Move();
		playerTwoMove = new Move();
	}
	
	MoveSet(Move p1Move, Move p2Move) {
		this.playerOneMove = p1Move;
		this.playerTwoMove = p2Move;
	}
}
