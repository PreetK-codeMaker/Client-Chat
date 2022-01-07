package AppDriver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

import Game.Game;
import Utility.InputListener;
import Utility.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class ClientGui extends Application implements PropertyChangeListener{
	private Socket socket;
	private String username;
	private Button connect,sendBtn,gameButton,disconnect;
    private TextArea textArea;
    private TextField input;
	private ObjectOutputStream oos= null;
	private InputListener listener;
	private Message msg;
	
	private Stage primeStage;
	private GridPane grip;
	private boolean turn = true;
	private boolean again = false;
	private boolean turnSwitch = true;
	private String status = "";
	private int count = 0;
	private int buttonLocation;
	private int player = 0;
	private Stage windows;
	private Button[] buttons = new Button[9];
    private int[][] wins = new int[][]{
        {0,1,2}, {3,4,5}, {6,7,8},
        {0,3,6}, {1,4,7}, {2,5,8},
        {0,4,8}, {2,4,6}
    };

	
    /***
     * This Method initiate stage and for the gui. Responsible for 
     * all the button send, diconnect and conncet.
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
        primeStage = primaryStage;
        
        windows = new Stage();
        //Text Area Where the Chat Will visible.
        textArea = new TextArea();
        textArea.setEditable(false);

        //Input field for writing Text.
        input = new TextField();

        //Button to send Messages.
        sendBtn = new Button("Send");
        sendBtn.setDisable(true);
        //Button to play Game.
        gameButton = new Button("Play TicTac");
        gameButton.setUserData(1);
        int ill = (Integer) gameButton.getUserData();
        System.out.println(ill+"*****************");
        gameButton.setDisable(true);
        //Button to Connect to other user.
         connect = new Button("Connect");
        //Button Disconnect from the User.
        disconnect = new Button("Disconnect");
        disconnect.setDisable(true);

        //vBox holds Disconnect and connect button that are being added to the vBox then gripPane.
        VBox vBox = new VBox();
        vBox.setSpacing(60);
        vBox.getChildren().add(connect);
        vBox.setSpacing(150);
        vBox.getChildren().add(disconnect);

        organizeButtons(buttons);
        GridPane griddy = new GridPane();
        griddy.setPadding(new Insets(20,20,20,20));

        for(int i=0; i<buttons.length; i++){
            griddy.add(buttons[i],i/3,i%3);

        }
        grip = griddy;
        grip.setDisable(true);

        GridPane gPane = new GridPane();
        gPane.setMinSize(400,400);
        gPane.setPadding(new Insets(10,10,10,10));
        gPane.setAlignment(Pos.CENTER_LEFT);
        gPane.add(griddy,2,1);
        gPane.setAlignment(Pos.CENTER);
        gPane.add(textArea,1,1);
        gPane.add(input,1,2);
        gPane.add(sendBtn,2,2);
        gPane.setAlignment(Pos.CENTER_LEFT);
        gPane.add(vBox,0,1);
        gPane.setGridLinesVisible(true);

        Scene scene = new Scene(gPane);
        
        /*----------------------------------------*/
        connect.setOnMouseClicked(event -> serverConnect());
        disconnect.setOnMouseClicked(event -> serverDisconnect());
        sendBtn.setOnMouseClicked(event -> sendMessages());
        windows.close();
        windows.setTitle("Client Chat");
        windows.setScene(scene);
        windows.show();
        windows.setOnCloseRequest(closeEvent -> {
            Platform.exit();
            System.exit(0);
       });


    }

    /***
     * This closes the GUI
     */
	private void shutDown(){
        Platform.exit();
    }

    /***
     * This connects the Client to the server and
     */
	private void Connect() {
		try {
			String host = addIP();
			socket = new Socket(host,3334);
			username = addUsername();
			oos = new ObjectOutputStream(socket.getOutputStream());
			listener = new InputListener(socket, this);
			new Thread(listener).start();

			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/***
	 * Disconnect the Client from the client and disable all the buttons.
	 * Except for th connect.
	 */
	private void Disconnect() {
		textArea.appendText("Disconnected.\n");
		disconnect.setDisable(true);
		sendBtn.setDisable(true);
		connect.setDisable(false);
		try {
			oos.close();
			socket.close();
			listener = null;
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Method prompt the user for the username to be added.
	 * @return a String for the username.
	 */
	private String addUsername() {
		String username =null;
		boolean status  =true;
		while(status) {
			username = JOptionPane.showInputDialog("Please enter Username and Cannot be null");
			if(!username.equals("")) {
				status = false;
			}
		}
		return username;
	}
	
	/***
	 * This ask for the user for a IP address to be added.
	 * @return this return the String. of Ip address
	 */
	private String addIP() {
		String ipAddress = null;
		boolean status = true;
		while(status) {
			ipAddress = JOptionPane.showInputDialog("Please Enter IP address");
			if(!ipAddress.equals("")){
				status = false;	
			}
			
		}
		return ipAddress;
	}
/***********************************GUI Disconnect,Connect and sendbtn************************************/

    private void sendMessages() {
        Message msg = new Message(username,input.getText(),new Date());
         try{
             oos.writeObject(msg);
             textArea.appendText(username+": "+msg.getMsg()+"("+msg.getTimeStamp()+")\n");
             input.setText("");

        }catch (IOException e){
             e.printStackTrace();
         }
    }
    
    private void serverConnect() {
        Connect();
        sendBtn.setDisable(false);
        disconnect.setDisable(false);
        gameButton.setDisable(false);
        connect.setDisable(true);
        grip.setDisable(false);

    }

    private void serverDisconnect() {
        Message msg = new Message(username,"Has Disconnected",new Date());
        try{
            oos.writeObject(msg);
        }catch(IOException e){
            e.printStackTrace();
        }
        Disconnect();
    }
    
/**********************************Game Logic ***********************************************/
	
	/***
	 * Assign buttons a Button listeners and and setUserData with i for the location tracking of the button
	 * 
	 * @param buttons
	 */
    private void organizeButtons(Button[] buttons){
        for (int i=0; i< buttons.length; i++ ){
            Button button = new Button();
            buttons[i] = button;
            button.setPrefWidth(50);
            button.setMaxHeight(100);
            button.setPrefHeight(100);
            button.setMaxWidth(100);
            button.setUserData(i);
            assignAction(button);
        }
    }
    /***
     * This takes in a button and assign a listener to each buttons
     * @param button takes in Button and and assign a event Action.
     */
    private void assignAction(Button button){
        again =false;
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(button.getText().isEmpty()){
                    System.out.println(button.getUserData());
                    turnSwitch= true;

                    if(turn){
                    	turnSwitch =true;
                        button.setText("X");
                        button.setDisable(true);
                        buttonLocation = (Integer) button.getUserData();
                        turn = false;
                        status = button.getText();
                        checkforWin();
                        count++;
                        grip.setDisable(turnSwitch);
                        turnSwitch = false;
                        Game game = new Game(button.getText(),button.getText(),true,false,again,turnSwitch,buttonLocation,count);
                        msg = new Message(null,null,null,game);
                        try {
                            oos.writeObject(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        System.out.println("I am in O");
                        button.setText("O");
                        button.setDisable(true);
                        buttonLocation = (Integer) button.getUserData();
                        turn = true;
                        status = button.getText();

                        checkforWin();
                        count++;
                        grip.setDisable(turnSwitch);
                        turnSwitch = false;
                        Game game = new Game(button.getText(),button.getText(),true,true,again,turnSwitch,buttonLocation,count);
                        msg = new Message(null,null,null,game);
                        try {
                            oos.writeObject(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    count ++;
                    System.out.println(count);

                }
                     if (count ==10){
                    JOptionPane.showMessageDialog(null,"It's a draw");
                    for(int i = 0; i<buttons.length; i++){
                        buttons[i].setText("");
                        buttons[i].setDisable(false);
                    }
                }if(again){
                    for(int i = 0; i<buttons.length; i++){
                        buttons[i].setText("");
                        buttons[i].setDisable(false);
                    }
                    count =0;
                    again =false;
                    turn = true;
                }
            }
        });
    }
    private void checkforWin(){

        for(int i =0; i<wins.length; i++){
            if(status.equals("X")){
                if (buttons[wins[i][0]].getText().equals("X") && buttons[wins[i][1]].getText().equals("X") && buttons[wins[i][2]].getText().equals("X")){
                    JOptionPane.showMessageDialog(null,status+" Has Won the");
                   again=true;

                }

            }else if(status.equals("O")){
                if(buttons[wins[i][0]].getText().equals("O") && buttons[wins[i][1]].getText().equals("O") && buttons[wins[i][2]].getText().equals("O")){
                    JOptionPane.showMessageDialog(null,status+" Has Won the");
                    again=true;

                }
            }

        }
    }
    
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
        Message msg = (Message) evt.getNewValue();
        if(msg.getObj() != null){
            Game game = (Game)msg.getObj();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    buttons[game.getLocation()].setText(game.getPlay());
                    System.out.println(game.getPlay());
                    buttons[game.getLocation()].setDisable(game.getCheck());
                    turn = game.getTurn();
                    grip.setDisable(game.getAgain());
                    status = game.getFigure();
                    again = game.getWon();
                    count = game.getCount();
                    System.out.println(count+"****************");
                    if(again){
                            for(int i = 0; i<buttons.length; i++){
                                buttons[i].setText("");
                                buttons[i].setDisable(false);
                            }
                            again =false;
                            count =0;
                    }if(count ==10){
                        JOptionPane.showMessageDialog(null,"It's a draw");
                        for(int i = 0; i<buttons.length; i++){
                            buttons[i].setText("");
                            buttons[i].setDisable(false);
                        }
                    }


                }
            });

        }else {
            String message = msg.getUser() + ": " + msg.getMsg() + " \n";
            textArea.appendText(message);
        }

    }
		
	
	
	/***
	 * Launch the GUI
	 * 
	 */
	public static void main(String[] args) {
		launch(args);
	}
	

}
