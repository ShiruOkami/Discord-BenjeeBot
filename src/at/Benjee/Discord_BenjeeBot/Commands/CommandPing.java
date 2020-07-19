package at.Benjee.Discord_BenjeeBot.Commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandPing {

	public CommandPing(MessageReceivedEvent event) {

		event.getChannel().sendMessage("Pong!").queue();
		
	}
	
}
