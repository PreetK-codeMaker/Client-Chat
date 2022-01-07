package Utility;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable
	{
		//Constants
		//This is the finger Print.
		static final long serialVersionUID = 5488945625178844229L;
		//Attributes
		private String 			user;
		private String			msg;
		private Date			timeStamp;
		private String			token;
		private Object			game;
		
		//Constructors
		public Message()
		{
			
		}
		
		public Message(String user, String msg, Date timeStamp)
		{
			this.user = user;
			this.msg = msg;
			this.timeStamp = timeStamp;
		}
		
		public Message(String user, String msg, Date timeStamp,Object obj)
		{
			this.user = user;
			this.msg = msg;
			this.timeStamp = timeStamp;
			this.game = obj;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		/**
		 * @return the user
		 */
		public String getUser()
		{
			return user;
		}

		/**
		 * @param user the user to set
		 */
		public void setUser(String user)
		{
			this.user = user;
		}

		/**
		 * @return the msg
		 */
		public String getMsg()
		{
			return msg;
		}

		/**
		 * @param msg the msg to set
		 */
		public void setMsg(String msg)
		{
			this.msg = msg;
		}

		/**
		 * @return the timeStamp
		 */
		public Date getTimeStamp()
		{
			return timeStamp;
		}
		public Object getObj(){
			return game;
		}

		/**
		 * @param timeStamp the timeStamp to set
		 */
		public void setTimeStamp(Date timeStamp)
		{
			this.timeStamp = timeStamp;
		}
		
		//Operational Methods
		public String toString()
		{
			return "User Name: "+user+
					"\nMessage: "+msg;
		}
	}
