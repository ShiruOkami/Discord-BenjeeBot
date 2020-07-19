package at.Benjee.Discord_BenjeeBot.Commands;

import at.Benjee.Discord_BenjeeBot.Util.GenericEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandStreamPingRole {

	public CommandStreamPingRole(GenericEvent genericEvent) {
		assignRole(genericEvent);
	}
	
	
	private boolean assignRole(GenericEvent genericEvent) {
		
		MessageReceivedEvent event = (MessageReceivedEvent) genericEvent.getEventObject();
		String streamPingRoleName = genericEvent.getBotInstance().getStreamPingRoleName();
		
		Guild guild = event.getGuild();
		MessageChannel channel = event.getChannel();
		Member senderMember = event.getMember();
		
		if (!genericEvent.getBotInstance().doesUserHaveVerifiedRole(senderMember)) {
			return false;
		}
		
		Role streamPingRole = guild.getRolesByName(streamPingRoleName, true).get(0);
		
		for (Role r : senderMember.getRoles()) {
			if (r.getName().equalsIgnoreCase(streamPingRoleName)) {
				guild.removeRoleFromMember(senderMember, streamPingRole).complete();
				channel.sendMessage(senderMember.getAsMention() + " , you don't have **StreamPing** role anymore.").queue();
				return true;
			}
		}
		
		guild.addRoleToMember(senderMember, streamPingRole).complete();
		channel.sendMessage(senderMember.getAsMention() + " , you now have the **StreamPing** role.").queue();
		return true;
		
	}
	
}
