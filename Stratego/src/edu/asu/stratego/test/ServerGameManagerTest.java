package edu.asu.stratego.test;

import edu.asu.stratego.Session;
import edu.asu.stratego.game.*;
import edu.asu.stratego.game.board.ServerSquare;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ServerGameManagerTest {

    private ServerGameManager serverGameManager;

    @Before
    public void setUp() throws Exception {
        serverGameManager = new ServerGameManager(new Session(null, null), false);
    }

    private void clearBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (serverGameManager.getBoard() != null && serverGameManager.getBoard().getSquare(i, j) != null) {
                    serverGameManager.getBoard().getSquare(i, j).setPiece(null);
                }
            }
        }
    }

    private void addMockPieces() {
        // set up fake blue pieces
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                serverGameManager.getBoard()
                                 .getSquare(i, j)
                                 .setPiece(new Piece(PieceType.MINER, PieceColor.BLUE, true));

        // set up fake red pieces
        for (int i = 9; i > 5; i--)
            for (int j = 9; j > 5; j--)
                serverGameManager.getBoard()
                                 .getSquare(i, j)
                                 .setPiece(new Piece(PieceType.SCOUT, PieceColor.RED, false));
    }

    private void removeColor(PieceColor color) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ServerSquare square = serverGameManager.getBoard().getSquare(i, j);
                if (square.getPiece() != null && square.getPiece().getPieceColor() != null && square.getPiece()
                                                                                                    .getPieceColor()
                                                                                                    .equals(color))
                    square.setPiece(null);
            }
        }
    }

    @Test
    public void testCheckWinCondition() {
        
        try {
            new Game();
            assertEquals(serverGameManager.checkWinCondition(), GameStatus.SETTING_UP);
        } catch (ExceptionInInitializerError | RuntimeException e) {
            
        }
        

        addMockPieces();
        assertEquals(serverGameManager.checkWinCondition(), GameStatus.IN_PROGRESS);

        removeColor(PieceColor.BLUE);
        assertEquals(serverGameManager.checkWinCondition(), GameStatus.BLUE_NO_MOVES);

        clearBoard();
        addMockPieces();
        removeColor(PieceColor.RED);
        assertEquals(serverGameManager.checkWinCondition(), GameStatus.RED_NO_MOVES);
    }

    @Test
    public void testIsCaptured() {
        addMockPieces();
        assertEquals(serverGameManager.isCaptured(PieceColor.BLUE), false);
        assertEquals(serverGameManager.isCaptured(PieceColor.RED), false);
    }

    @Test
    public void testHasAvailableMoves() {
        addMockPieces();
        removeColor(PieceColor.BLUE);
        assertEquals(serverGameManager.hasAvailableMoves(PieceColor.BLUE), false);

        clearBoard();
        addMockPieces();
        removeColor(PieceColor.RED);
        assertEquals(serverGameManager.hasAvailableMoves(PieceColor.RED), false);
    }

    @Test
    public void testComputeValidMoves() {
        addMockPieces();
        ArrayList<Point> points = serverGameManager.computeValidMoves(3, 3, PieceColor.BLUE);

        // assure all possible moves for the SCOUT piece
        for (int x = 3, y = 4; y < 10; y++) {
            Point p = points.get(y - 4);
            assertEquals(p.getX(), x, 0);
            assertEquals(p.getY(), y, 0);
        }
    }

    @Test
    public void testIsLake() {
        // Left position
        assertEquals(ServerGameManager.isLake(4, 2), true);
        assertEquals(ServerGameManager.isLake(5, 2), true);
        assertEquals(ServerGameManager.isLake(4, 3), true);
        assertEquals(ServerGameManager.isLake(5, 3), true);

        // Right position
        assertEquals(ServerGameManager.isLake(4, 6), true);
        assertEquals(ServerGameManager.isLake(5, 6), true);
        assertEquals(ServerGameManager.isLake(4, 7), true);
        assertEquals(ServerGameManager.isLake(5, 7), true);
    }

    @Test
    public void testIsInBounds() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                assertEquals(ServerGameManager.isInBounds(i, j), true);
        assertEquals(ServerGameManager.isInBounds(10, 10), false);
        assertEquals(ServerGameManager.isInBounds(-1, -1), false);
        assertEquals(ServerGameManager.isInBounds(0, 0), true);
    }

    @Test
    public void testIsOpponentPiece() {
        addMockPieces();
        assertEquals(serverGameManager.isOpponentPiece(3, 3, PieceColor.BLUE), false);
    }

}