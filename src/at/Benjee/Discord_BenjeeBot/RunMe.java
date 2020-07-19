package at.Benjee.Discord_BenjeeBot;

import javax.security.auth.login.LoginException;

public class RunMe {

	/*
	 * https://discordapp.com/oauth2/authorize?client_id=570598825002008578&scope=bot&permissions=8
	 */
	
	private BenjeeBot _bot; 
	
	public RunMe() throws LoginException {
		_bot = new BenjeeBot();
		_bot.connectBot();
	}
	
	public static void main(String[] args) {
		
		try {
			new RunMe();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
	}
	
}
