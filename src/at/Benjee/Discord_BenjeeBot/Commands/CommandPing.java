package at.Benjee.Discord_BenjeeBot.Commands;

import at.Benjee.Discord_BenjeeBot.Util.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandPing {

	public CommandPing(GenericEvent genericEvent) {
		ping(genericEvent);
	}
	
	private boolean ping(GenericEvent genericEvent) {
		
		MessageReceivedEvent event = (MessageReceivedEvent) genericEvent.getEventObject();
		if (!genericEvent.getBotInstance().doesUserHaveVerifiedRole(event.getMember())) {
			return false;
		}
		
		event.getChannel().sendMessage("Pong!").queue();
		return true;
		
	}
	
}
