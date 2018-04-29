package edu.asu.stratego.gui;
 
import java.net.UnknownHostException;
import java.io.*;
import java.util.*;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
 
import edu.asu.stratego.game.ClientSocket;
import edu.asu.stratego.game.Game;
 
/**
 * Wrapper class for a JavaFX scene. Contains a scene UI and its associated
 * event handlers for retrieving network connection information from the player
 * and connecting to the network.
 */
public class ConnectionScene {
   
    private static final Object playerLogin = new Object();
   
    private Button    submitFields  = new Button("Enter Battlefield");
    private Button    prevButton = new Button("Previous Servers");
    private TextField nicknameField = new TextField();
    private TextField serverIPField = new TextField();
    static  Label     statusLabel   = new Label();
   
    private static String serverIP, nickname;
   
    private final int WIDTH  = 340;
    private final int HEIGHT = 150;
    
    public static File file;
    private static BufferedReader stdin;
    private static ArrayList<String> list;
    
    private static int current;
   
    Scene scene;
   
    /**
     * Creates a new instance of ConnectionScene.
     * @throws IOException 
     */
    ConnectionScene() throws IOException {
    	
    	//reads from saved ips file and stores in ArrayList list
    	file = new File("Servers.sav");
    	file.createNewFile();
		stdin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		list = new ArrayList<String>();
		String input = stdin.readLine();
		current = 0;
		while(input != null)
		{
			list.add(input);
			input = stdin.readLine();
		}
		stdin.close();
		
		
        // Create UI.
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Nickname: "), 0, 0);
        gridPane.add(new Label("Server IP: "), 0, 1);
        gridPane.add(nicknameField, 1, 0);
        gridPane.add(serverIPField, 1, 1);
        
        gridPane.add(prevButton, 2, 1);
        
        gridPane.add(submitFields, 1, 2);
       
        BorderPane borderPane = new BorderPane();
        BorderPane.setMargin(statusLabel, new Insets(0, 0, 10, 0));
        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        borderPane.setBottom(statusLabel);
        borderPane.setCenter(gridPane);
       
        // UI Properties.
        GridPane.setHalignment(submitFields, HPos.RIGHT);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
       
        // Event Handlers.
        submitFields.setOnAction(e -> Platform.runLater(new ProcessFields()));
        prevButton.setOnAction(e -> Platform.runLater(new prevTexts()));
       
        scene = new Scene(borderPane, WIDTH, HEIGHT);
    }
    
    /**
     * When previous servers button is pressed, this handler will
     * cycle through the list of previous ips and display each one
     * in the server ip field
     */
    private class prevTexts implements Runnable {
        @Override
        public void run() {
            Platform.runLater(() -> {
            	if(!list.isEmpty())
            	{	
            		if(current >= list.size())
            		{
            			current = 0;
            		}
            		
            		if(!isValid(decrypt(list.get(current))))
            		{
            			current ++;
            			
                		if(current >= list.size())
                		{
                			current = 0;
                		}
	
            		}
            		
            		serverIPField.setText(decrypt(list.get(current)));
            		
                	current ++;
            	}
            });
        }
    }
    
     
    /**
     * Event handler task for submitFields button events. Notifies the
     * connectToServer thread that connection information has been received
     * from the user.
     *
     * <p>
     * The method call to wait() will cause the event to hang until it is woken
     * up by another thread signaling that a connection attempt has been made.
     * Until the thread running this task is notified, the form fields will
     * be disabled preventing the user from firing another event.
     * </p>
     *
     * @see edu.asu.stratego.gui.ConnectionScene.ConnectToServer
     */
    private class ProcessFields implements Runnable {
        @Override
        public void run() {
            Platform.runLater(() -> {
               statusLabel.setText("Connecting to the server...");
            });
 
            nickname = nicknameField.getText();
            serverIP = serverIPField.getText();

            
            // Default values.
            if (nickname.equals(""))
                nickname = "Player";
           
            Game.getPlayer().setNickname(nickname);
           
            nicknameField.setEditable(false);
            serverIPField.setEditable(false);
            submitFields.setDisable(true);
           
            synchronized (playerLogin) {
                try {
                    playerLogin.notify();  // Signal submitFields button event.
                    playerLogin.wait();    // Wait for connection attempt.
                }
                catch (InterruptedException e) {
                    // TODO Handle this exception somehow...
                    e.printStackTrace();
                }
            }
           
            nicknameField.setEditable(true);
            serverIPField.setEditable(true);
            submitFields.setDisable(false);
        }
    }
   
    /**
     * A Runnable task for establishing a connection to a Stratego server.
     * The task will continue running until a successful connection has
     * been made. The connection attempt loop is structured like so:
     *
     * <ol><li>
     * Wait for the player to invoke button event in the ConnectionScene.
     * </li><li>
     * Attempt to connect to a Stratego server using the information retrieved
     * from the UI and wake up the button event thread.
     * </li><li>
     * If connection succeeds, signal the isConnected condition to indicate to
     * other threads a successful connection attempt and then terminate the
     * task. Otherwise, output error message to GUI, and go to #1.
     * </li></ol>
     *
     * @see edu.asu.stratego.gui.ConnectionScene.ProcessFields
     */
    public static class ConnectToServer implements Runnable {
        @Override
        public void run() {
           
            while (ClientSocket.getInstance() == null) {
                synchronized (playerLogin) {
                    try {
                        // Wait for submitFields button event.
                        playerLogin.wait();
                       
                        if(!isValid(serverIP))
                        {
                        	Platform.runLater(() -> {
                                statusLabel.setText("Enter \"localhost\" or a valid ipv4 address");
                            });
                        }
                        else
                        {
                        	// Attempt connection to server.
                        	ClientSocket.connect(serverIP, 4212);
                        }
                    }
                    catch (UnknownHostException ex)
                    {
                        Platform.runLater(() -> {
                            statusLabel.setText("Cannot connect to Server");
                        });	
                    }
                    catch (IOException e) {
                        Platform.runLater(() -> {
                            statusLabel.setText("Cannot find Server at that address");
                        });
                    }
                    catch (InterruptedException e) {
                        Platform.runLater(() -> {
                            statusLabel.setText("Server connection interrupted");
                        });
                    }
                    finally {
                        // Wake up button event thread.
                        playerLogin.notify();
                    }
                }
            }
        }
    }
    
    /**
     * boolean method for determining whether user's ip address is
     * a valid ipv4 address, that is, it contains four numerical fields
     * separated by points, and each field is between 0 and 255 (inclusive)
     *
     * @return whether ip entered by used is a valid ipv4 address
     */
    public static boolean isValid(String ip)
    {
    	boolean valid = true;
    	
    	if(ip.length() < 7)
    	{
    		valid = false;
    	}
    	else if(!ip.equals("localhost"))
    	{
    		if(ip.charAt(0) == '.' || ip.charAt(ip.length() - 1) == '.')
    		{
    			valid = false;
    		}
    		else
    		{
    			int points = 0;
    			
    			int[] locs = new int[3];
    			
    			int currentLoc = 0;
    			
        		for(int i = 0; i < ip.length(); i++)
        		{
        			if(ip.charAt(i) == '.')
        			{
        				points ++;
        				
        				if(points < 4)
        				{
        					locs[currentLoc] = i;
        					
        					currentLoc ++;
        				}
        				else
        				{
        					valid = false;
        				}
        			}
        			else if(!isDigit(ip.charAt(i)))
        			{
        				valid = false;
        			}
        		}
        		
        		if(points != 3)
        		{
        			valid = false;
        		}
        		else
        		{
        			for(int i = 1; i < locs.length; i++)
        			{
        				if(locs[i] == locs[i-1] + 1)
        					valid = false;
        			}
        			
        			if(valid)
					{        			
        				String[] values = ip.split("\\.");
        			
        				for(int i = 0; i < values.length; i++)
        				{
        					if(Integer.valueOf(values[i]) > 255)
        						valid = false;
        				}
					}
        		}
    		}
    	}
    	
    	return valid;
    } 
    
    /**
     *
     * @return whether or not the entered character is an ascii digit
     */
    public static boolean isDigit(char test)
    {
    	boolean digit = false;
    	
    	if(((int) test) > 47 && ((int) test) < 58)
    	{
    		digit = true;
    	}
    	
    	return digit;
    }
    
    
    /**
    * reverses encryption of encrypted server ip
    *
    * @return decrypted value of a server ip
    */
	public static String decrypt(String entered)
	{
		String result = "";

		entered = reverse(entered);

		String[] values = entered.split("\\$");

		int[] decrypted = new int[values.length];

		for(int i = 0; i < values.length; i++)
		{
			decrypted[i] = (int)Math.sqrt(Integer.valueOf(values[i]));

			result += Character.toString((char)decrypted[i]);
		}

		return result;
	}
	
    /**
    *
    * @return the reverse of the string that was entered
    */
	public static String reverse(String enter)
	{
		if(enter.length() > 1)
		{
			return enter.substring(enter.length() - 1) + reverse(enter.substring(0, enter.length() - 1));
		}
		else
		{
			return enter;
		}
	}
}