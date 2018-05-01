package edu.asu.stratego.game;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

import edu.asu.stratego.gui.ConnectionScene;


/**
 * Client socket that connects to the server. The client should only use one 
 * socket to connect to the server.
 */
public final class ClientSocket {
	
    private static BufferedReader stdin;
	private static FileWriter writer;
	private static ArrayList<String> list;
    
    private static Socket socket = null;
    
    /**
     * Prevents an instance of this class from being instantiated.
     */
    private ClientSocket() { /* Intentionally Empty */ }
    
    /**
     * Attempts a connection to the server and updates saved server ips file
     * 
     * @param serverIP server IP address
     * @param port server port number
     */
    public static void connect(String serverIP, int port) 
            throws UnknownHostException, IOException {
        socket = new Socket(serverIP, port); //attempt connection
    	
        //take inputs from saved server file
		stdin = new BufferedReader(new InputStreamReader(new FileInputStream(ConnectionScene.serverFile)));
		writer = new FileWriter(ConnectionScene.serverFile,true);		
		list = new ArrayList<String>();		
		String input = stdin.readLine();		
		while(input != null)
		{
			list.add(input);		
			input = stdin.readLine();
		}
		stdin.close();
		
		//if ip was valid and not already present in save file, write ip to save file
        if(ConnectionScene.isValid(serverIP) && !list.contains(encrypt(serverIP))) 
        {
        	try 
        	{
        		writer.write(encrypt(serverIP) + System.getProperty("line.separator"));
        		writer.flush();
        		writer.close();
			} 
        	catch (IOException e1) 
        	{
				e1.printStackTrace();
			}
        }  
    }    
    
    public static void reconnect() {
    	try {
			socket = new Socket(ClientFileManager.getLastGameIp(), ClientFileManager.getLastPort());
			
			
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
    }
    

    
    /**
    * encrypts server ip to prevent easy tampering
    *
    * @return encrypted value of a server ip
    */
	public static String encrypt(String entered)
	{
		String result = "";

		char[] values = entered.toCharArray();

		int[] encrypted = new int[values.length];

		for(int i = 0; i < values.length; i++)
		{
			encrypted[i] = (int)Math.pow((int)values[i],2);

			result += Integer.toString(encrypted[i]) + "$";
		}

		return ConnectionScene.reverse(result);
	}

    
    
    /**
     * Returns the one and only instance of the client socket.
     * 
     * @return the Socket used to establish a connection between the client 
     * and the server. The socket may be null.
     */
    public static Socket getInstance() {
        return socket;
    }
}