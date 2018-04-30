package edu.asu.stratego;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import edu.asu.stratego.game.ServerGameManager;

/**
 * The Stratego Server creates a socket and listens for connections from every
 * two players to form a game session. Each session is handled by a thread,
 * ServerGameManager, that communicates with the two players and determines the
 * status of the game.
 */
public class Server {
	public static void main(String[] args) throws IOException {
        
        String hostAddress    = InetAddress.getLocalHost().getHostAddress();
        ServerSocket listener = null;
        
        ArrayList<Session> activeSessions = new ArrayList<Session>();
        
        try {
            listener = new ServerSocket(4212);
            System.out.println("Server started @ " + hostAddress);
            System.out.println("Waiting for incoming connections...\n");
            
            while (true) {
                Socket playerOne = listener.accept();
                System.out.println("Session " + Session.getNextID() + 
                                   ": Player 1 has joined the session");
                ObjectOutputStream toPlayerOne = new ObjectOutputStream(playerOne.getOutputStream());
    			ObjectInputStream fromPlayerOne = new ObjectInputStream(playerOne.getInputStream());
                
                Socket playerTwo = listener.accept();
                System.out.println("Session " + Session.getNextID() + 
                                   ": Player 2 has joined the session");
               
                
                Session session = new Session(playerOne, playerTwo);
                activeSessions.add(session);
                
                Thread thread= new Thread(new ServerGameManager(session, false));
                
                thread.setDaemon(true);
                thread.start();
            }
        }
        
        finally 
        { 
        	if(listener != null)
        	{
        		listener.close(); 
        	}
        }
    }
}