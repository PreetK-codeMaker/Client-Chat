package Serves;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Utility.InputListener;
import Utility.Message;

/***
 * This is Client Handler class it. Handles all the client that have been accepted by the server and passed down to this class.
 * This class is responsible for redirecting the the messages and client chat. This Class is notified whenever there is a need to send messages 
 * or game data is being send 
 *
 *
 */

public class ClientHandler  implements Runnable, PropertyChangeListener {
	private Socket cl1 =null;
	private Socket cl2 =null;
	private ObjectOutputStream oos1 = null;
	private ObjectOutputStream oos2 = null;

	private Thread inputListner1 = null;
	private Thread inputLister2 = null;
	
	/***
	 * This constructor takes in two different sockets from the server.
	 * @param cl1 This is a socket which takes in String and integer for client one.
	 * @param cl2 This is a socket which takes in String and integer for client one.
	 */
	public ClientHandler(Socket cl1, Socket cl2) {
		this.cl1 = cl1;
		this.cl2 = cl2;
	}

	/***
	 * This abstract method being overrided That inherited from the Runnable. This method runs Threads. In this Method create 
	 * Threads, make instantiate Inputlistener for Client socket #1, give it a unique id, and listener for ClientHandler class. 
	 */
	@Override
	public void run() {
		InputListener listener1 = new InputListener(1,cl1,this);
		InputListener listener2 = new InputListener(2,cl2,this);
		
		//Making thread 
		Thread t1 = new Thread(listener1);
		
		//Starting Thread
		t1.start();
		Thread t2 = new Thread(listener2);
		t2.start();

		try {
			
			oos1 = new ObjectOutputStream(cl1.getOutputStream());
			oos1.flush();

			
			oos2 = new ObjectOutputStream(cl2.getOutputStream());
			oos2.flush();


			while(cl1.isConnected() && cl2.isConnected());

			oos1.close();

			oos2.close();
			cl1.close();
			cl2.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

	
	/***
	 * This method is invoke from the Inputlistener to notify that have changes made. This 
	 */
	@Override
	synchronized public void propertyChange(PropertyChangeEvent event) {
		Message message = (Message) event.getNewValue();
		InputListener i = (InputListener) event.getSource();
		System.out.println(i);
		try {
			//This tell which way to direct the client messages.
			if (i.getListnerNum() == 1) {

				if(message.getObj() !=null) {

					oos2.writeObject(event.getNewValue());
				}else {
					oos2.writeObject(event.getNewValue());
				}
			}else{


				oos1.writeObject(event.getNewValue());
			}
		}catch(IOException e){
			e.printStackTrace();
		}


		
	}	

}
