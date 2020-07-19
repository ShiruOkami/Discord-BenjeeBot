package at.Benjee.Discord_BenjeeBot;

import javax.security.auth.login.LoginException;

import at.Benjee.Discord_BenjeeBot.Listeners.CommandListener;
import at.Benjee.Discord_BenjeeBot.Listeners.GuildJoinListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BenjeeBot {

	private static final String _botToken = "";
	private static final String _botPrefix = "!";
	
	private JDABuilder _builder;
	
	public BenjeeBot() {
		_builder = JDABuilder.createDefault(_botToken);
	}
	
	
	public boolean connectBot() throws LoginException {
		
		// Allow to track guild members
		_builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		
		// Activate listeners
		_builder.addEventListeners(new CommandListener(this));
		_builder.addEventListeners(new GuildJoinListener(this));
		
		// Set bot activity
		_builder.setActivity(Activity.playing("with Raichus -w-"));
		
		// Connect to Discord
		 _builder.build();
		 
		return true;
		
	}
	
	public String getPrefix() {
		return _botPrefix;
	}
	
}
