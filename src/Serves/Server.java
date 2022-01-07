package Serves;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Server {
	private static List<Socket> sco = new ArrayList<Socket>();
	
	/***
	 * This method run server and Initiate the Server Socket the serveSocket 
	 * Also socket on. It also the user to add in a port number.
	 */
	public void runServer() {
	
	ServerSocket server = null;
	Socket c1  = null;
	
	try {
		server = new ServerSocket(3334);
		JOptionPane.showMessageDialog(null,"port# 3334");
	}catch(IOException e) {
		e.printStackTrace();
	}
	
		while(true) {
			try {
				c1 = server.accept();
				sco.add(c1);
				System.out.println("Accepted a Client");
				if(sco.size() == 2) {
					ClientHandler cli = new ClientHandler(sco.get(0),sco.get(1));
					Thread td = new Thread(cli);
					td.start();
					sco.clear();
				}
			}catch(IOException e) {
				e.printStackTrace();
				
			}
		}
	}

}
