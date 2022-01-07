package Game;

import java.io.Serializable;

/***
 * This class handles all the clicks being send to between instanceof of same gui classes.
 *
 *
 */
public class Game implements Serializable {
	
	    static final long serialVersionUID = 555545756255555L;
	    private boolean check;
	    private boolean turn;
	    private boolean won;
	    private boolean loose;
	    private String play;
	    private String figure;
	    private int location;
	    private boolean again;
	    private int count;


	public Game() {
	    }
	    
	    /***
	     * This Method constructor takes in games play figure;
	     * @param play this takes in string and see where the other player have set their mark.
	     * @param figure this takes a String basically either X or O
	     * @param check this takes boolean and disables the button. 
	     * @param turn takes in a boolean check to see if the boolean. Who's turns is it.
	     * @param won Takes in boolean to see who won the game.
	     * @param again is to rest the board again.
	     * @param location This is a integer value tell you the location of the buttons 		 	 
	     */
	    public Game(String play,String figure, boolean check,boolean turn,boolean won,boolean again, int location, int count){
	        this.check = check;
	        this.figure = figure;
	        this.won = won;
	        this.again = again;
	        this.turn = turn;
	        this.play  = play;
	        this.location = location;
	        this.count =count;
	    }
	    /***
	     * This takes in boolean 
	     * @param check takes in boolean
	     */
	    public void setCheck(boolean check){
	        this.check = check;
	    }
	    
	    /***
	     * This Takes in a String for the play.
	     * @param play takes in a String 
	     */
	    public void setPlay(String play){
	        this.play = play;
	    }
	    
	    /***
	     * This returns a boolean
	     * @return this return a false boolean.
	     */
	    public boolean getCheck(){
	        return this.check;
	    }
	    
	    /***
	     * This return a String
	     * @return a string value for the enemy
	     */
	    public String getPlay(){
	        return this.play;
	    }
	    
	    /***
	     * This tells the location in the tictac panel
	     * @param location takes integer value to tell the other side of the location and vice versa
	     */
	    public void setLocation(int location){
	        this.location = location;
	    }
	    
	    /***
	     * This give the integer value 
	     * @return a integer value.
	     */
	    public int getLocation(){
	        return this.location;
	    }
	    
	    /***
	     * This takes in boolean 
	     * @param turn true or false depending on turn
	     */
	    public void setTurn(boolean turn){
	        this.turn = turn;
	    }
	    
	    
	    /***
	     * This returns a boolean
	     * @return this return a true or false depending on turn.
	     */
	    public boolean getTurn(){
	        return turn;
	    }
	    
	    /***
	     * This returns a boolean
	     * @return this return a true or false depending on win.
	     */
	    public boolean getWon(){
	        return  this.won;
	    }
	    
	    /***
	     * This returns a boolean
	     * @return this return a true  to reset the board.
	     */
	    public boolean getAgain(){
	        return this.again;
	    }
	    
	    /***
	     * This takes a boolean to resent the board 
	     * @param again
	     */
	    public void setAgain( boolean again){
	        this.again = again;
	    }
	    
	    /***
	     * This checks for see who won
	     * @param won taken in a boolean who won the game.
	     */
	    public void setWon(boolean won){
	        this.won = won;
	    }
	    /***
	     * This take in a String value as X or O
	     * @param figure is a string takes in X or O
	     */
	    public void setFigure(String figure){
	        this.figure = figure;
	    }
	    
	    /***
	     * This return a figure Either or a X or O
	     * @return
	     */
	    public String getFigure(){
	        return figure;
	    }

		public int getCount() {
		return count;
	}

		public void setCount(int count) {
		this.count = count;
	}


}


