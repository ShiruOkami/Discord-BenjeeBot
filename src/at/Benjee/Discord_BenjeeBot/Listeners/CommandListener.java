package at.Benjee.Discord_BenjeeBot.Listeners;

import at.Benjee.Discord_BenjeeBot.BenjeeBot;
import at.Benjee.Discord_BenjeeBot.Commands.CommandAssignRole;
import at.Benjee.Discord_BenjeeBot.Commands.CommandPing;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	private BenjeeBot _bot;
	
	public CommandListener(BenjeeBot bot) {
		_bot = bot;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		Message discordMsg = event.getMessage();
		String rawMsg = discordMsg.getContentRaw();

		if ((rawMsg.length() > 1) && (rawMsg.substring(0, _bot.getPrefix().length()).equals(_bot.getPrefix()))) {
			
			// Single argument commands
			if (!rawMsg.contains(" ")) {
				
				// Ping Command
				if (rawMsg.substring(_bot.getPrefix().length(), rawMsg.length()).equals("ping")) {
					new CommandPing(event);
					return;
				}
				
				// StreamPing Command
				if (rawMsg.substring(_bot.getPrefix().length(), rawMsg.length()).equals("streamping")) {
					new CommandAssignRole(event);
					return;
				}
				
			}
			
		}
		
	}
	
}
