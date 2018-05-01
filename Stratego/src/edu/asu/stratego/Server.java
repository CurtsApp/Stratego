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

	private static ArrayList<Session> activeSessions = new ArrayList<Session>();

	public static void main(String[] args) throws IOException {

		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		ServerSocket listener = null;

		try {
			listener = new ServerSocket(4212);
			System.out.println("Server started @ " + hostAddress);
			System.out.println("Waiting for incoming connections...\n");

			while (true) {
				boolean isPlayerOneReconnecting = true;
				Socket playerOne = null;
				while (isPlayerOneReconnecting) {
					playerOne = listener.accept();
					System.out.println("Session " + Session.getNextID() + ": Player 1 has joined the session");

					ObjectOutputStream toPlayerOne = new ObjectOutputStream(playerOne.getOutputStream());
					ObjectInputStream fromPlayerOne = new ObjectInputStream(playerOne.getInputStream());

					try {
						isPlayerOneReconnecting = (Boolean) fromPlayerOne.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					if (isPlayerOneReconnecting) {
						// Get game id from client
						int gameId = fromPlayerOne.readInt();

					}
				}

				boolean isPlayerTwoReconnecting = true;
				Socket playerTwo = null;
				while (isPlayerTwoReconnecting) {
					playerTwo = listener.accept();
					System.out.println("Session " + Session.getNextID() + ": Player 2 has joined the session");

					ObjectOutputStream toPlayerTwo = new ObjectOutputStream(playerTwo.getOutputStream());
					ObjectInputStream fromPlayerTwo = new ObjectInputStream(playerTwo.getInputStream());

					try {
						isPlayerTwoReconnecting = (Boolean) fromPlayerTwo.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					if (isPlayerOneReconnecting) {
						// Get game id from client
						int gameId = 0;
						try {
							gameId = (Integer) fromPlayerTwo.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}

						boolean reconnectSucess = reconnectPlayerToSession(gameId, playerTwo);

						// If reconnect was unsucessful discard their information anyways
						// The client will make a new non reconnect request

					}
				}

				System.out.println("Session built");
				
				Session session = new Session(playerOne, playerTwo);
				activeSessions.add(session);

				Thread thread = new Thread(new ServerGameManager(session, false));

				thread.setDaemon(true);
				thread.start();
			}

		}

		finally

		{
			if (listener != null) {
				listener.close();
			}
		}
	}

	private static boolean reconnectPlayerToSession(int gameId, Socket socket) {
		for (Session session : activeSessions) {
			if(session.getId() == gameId) {
				session.sessionCommunicaton.add(socket);
				return true;
			}
		}
		return false;
	}
}