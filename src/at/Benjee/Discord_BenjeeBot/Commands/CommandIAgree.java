package at.Benjee.Discord_BenjeeBot.Commands;

import at.Benjee.Discord_BenjeeBot.Util.GenericEvent;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandIAgree {
	
	public CommandIAgree(GenericEvent genericEvent) {
		assignRole(genericEvent);
	}
	
	private boolean assignRole(GenericEvent genericEvent) {
		
		MessageReceivedEvent event = (MessageReceivedEvent) genericEvent.getEventObject();
		
		if (!event.getChannel().getId().contentEquals(genericEvent.getBotInstance().getVerifyChannelID())) {
			return false;
		}
		
		Role verifyRole = event.getGuild().getRolesByName(genericEvent.getBotInstance().getVerifiedRoleName(), true).get(0);
		event.getGuild().addRoleToMember(event.getMember(), verifyRole).complete();
		event.getMessage().delete().complete();
		
		return true;
		
	}
	
}
