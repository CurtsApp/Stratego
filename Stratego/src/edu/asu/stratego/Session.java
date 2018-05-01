package edu.asu.stratego;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Session {
	private static int ID = 0;
	private Socket player1;
	private Socket player2;
	private int id;
	public LinkedBlockingQueue<Socket> sessionCommunicaton;
	
	public Session(Socket player1, Socket player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.id = 1 + Session.ID;
		sessionCommunicaton = new LinkedBlockingQueue<Socket>();
	}
	
	public static int getNextID() {
		return Session.ID;
	}
	
	public int getId () {
		return id;
	}
	
	public Socket getPlayer1() {
		return player1;
	}
	
	public Socket getPlayer2() {
		return player2;
	}
	
	public void setPlayer1(Socket playerOne) {
		this.player1 = playerOne;
	}
	
	public void setPlayer2(Socket playerTwo) {
		this.player2 = playerTwo;
	}
	
}
