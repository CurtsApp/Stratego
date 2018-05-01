package edu.asu.stratego.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class ClientFileManager {
	private final static String GAME_INFO_PATH = "gameinfo.txt";

	public static String getLastGameIp() {
		File file = new File(GAME_INFO_PATH);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			String gameInfo = scanner.next();
			String[] gamePieces = gameInfo.split(",");
			return gamePieces[0];
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "localhost";
	}

	public static int getLastPort() {
		File file = new File(GAME_INFO_PATH);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			String gameInfo = scanner.next();
			String[] gamePieces = gameInfo.split(",");
			return Integer.parseInt(gamePieces[1]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 4242;
	}

	public static int getLastGameId() {
		File file = new File(GAME_INFO_PATH);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			String gameInfo = scanner.next();
			String[] gamePieces = gameInfo.split(",");
			return Integer.parseInt(gamePieces[2]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public static void writeSessionData(String ip, int port, int sessionId) {
		File file = new File("gameinfo.txt");
		if (ip.equals("localhost/127.0.0.1")) {
			ip = "127.0.0.1";
		}
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(ip + "," + port + "," + sessionId);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static boolean doesGameInfoExist() {
		File file = new File(GAME_INFO_PATH);
		try {
			return file.exists();
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

}
