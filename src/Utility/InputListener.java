package Utility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/***
 * This class is the Observer and Monitor it's subject help route them to proper. oos(ObjectOutputStream.)
 *
 *
 */
public class InputListener implements Runnable {
    private ObjectInputStream ois = null;
    private List<PropertyChangeListener> listeners = new ArrayList<>();
    private int listnerNum;
    private Socket socket;


    /***
     * this set the int value for each instance of the Client class and takes listener from the CinetHandler Class
     * @param i  is  integer value for the ID 
     * @param socket is help's you connect the server
     * @param listener is property change listener.
     */
    public InputListener(int i, Socket socket, PropertyChangeListener listener) {
        this.socket = socket;
        this.listnerNum = i;
        listeners.add(listener);

    }

    /***
     * This set by the Client to pass in the socket and listener 
     * @param socket This is the socket. Takes in int and String 
     * @param listener This listener been passed from Client GUI.
     */
    public InputListener(Socket socket, PropertyChangeListener listener){
        listeners.add(listener);
        this.socket = socket;
    }

    /***
     * This set the Id for the Listener
     * @param i Takes in i
     */
    public void setListeners(int i){
        this.listnerNum = i;

    }

    /***
     * This return the ID 
     * @return it return the listnerNum
     */
    public int getListnerNum(){
        return listnerNum;

    }   
    /***
     * This run method inherited from  propertyChangeListener. 
     */
    @Override
    public void run() {
        try{
        	//Declaring ObjectInputStream with socket getInputStream.
            ois = new ObjectInputStream(socket.getInputStream());
            boolean status =true;
            

        while(status) {
            try {


            //Declaring Message so ReaderObject can cast to Message.
            Message message = (Message) ois.readObject();

            //differentiate if person not sending a message. Rather game data.
            if (message.getObj() != null) {
                notifyListeners(message);
            } else {
                //If the client sending messages.
                notifyListeners(message);
            }
        }catch(ClassNotFoundException e){
               status = false;
        }
        }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
   /***
    *This notifyListeneres method it will notify Client Handler for any changes that will be made. 
    *It takes source, property name, oldValue, newValue. For this We only needed to pass newValue.
    * @param o This take in an Object as newValue .  
    */
    private void notifyListeners(Object o){
        for(PropertyChangeListener listener : listeners){
            listener.propertyChange(new PropertyChangeEvent(this,null,null,o));
        }
    }

}
